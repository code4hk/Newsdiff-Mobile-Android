package hk.code4.newsdiffhk;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import hk.code4.newsdiffhk.Adapter.NewsAdapter;
import hk.code4.newsdiffhk.DAO.NetworkController;
import hk.code4.newsdiffhk.Interface.API;
import hk.code4.newsdiffhk.Model.News;
import hk.code4.newsdiffhk.Model.NewsItem;
import hk.code4.newsdiffhk.Model.Publisher;
import hk.code4.newsdiffhk.Util.NetworkUtils;
import hk.code4.newsdiffhk.Util.RxUtils;
import hk.code4.newsdiffhk.Widget.EmptyRecyclerView;
import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    NetworkController mNetworkController;
    List<Publisher> mPublishers;
    TabLayout mTabLayout;
    NewsAdapter mAdapter;
    EmptyRecyclerView mRecyclerView;
    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private API mApi;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupTabLayout();

        mNetworkController = NetworkController.getInstance();

        mAdapter = new NewsAdapter();
        mAdapter.setOnItemClickListener((view, position) -> {
            final NewsItem item = mAdapter.getItem(position);
            if (item != null)
                NewsDetailActivity.start(this, item.getId(), item.getTitle(), item.getCount());
        });

        setupRecyclerView();


        mApi = createApi();

        if (NetworkUtils.isOnline(MainActivity.this))
            getAllPublisher();

    }

    @Override
    public void onResume() {
        super.onResume();
        _subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(_subscriptions);
    }

    @Override
    public void onPause() {
        super.onPause();

        RxUtils.unsubscribeIfNotNull(_subscriptions);
    }

    private void getAllPublisher() {

        _subscriptions.add(mApi.getPublisher()
                .flatMap(list -> {
                    mPublishers = list;
                    mAdapter.setPublisher(list);
                    return Observable.from(list);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Publisher>() {
                               @Override
                               public void onNext(Publisher publishers) {
                                   mTabLayout.addTab(mTabLayout.newTab().setText(publishers.getName()));
                               }

                               @Override
                               public void onCompleted() {
                                   System.out.println("Completed!");
                                   getAllNews(ALL_NEWS, "", page);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                               }
                           }

                ));
    }

    final static int ALL_NEWS = 1001;
    final static int PUBLISHER_NEWS = 1002;

    private void getAllNews(int type, String publisher_code, int page) {

        Toast.makeText(this, "載入第 " + page + " 頁中", Toast.LENGTH_SHORT).show();
        Observable<News> newsObservable;
        String url = "";
        switch (type) {
            case PUBLISHER_NEWS:
                url = NetworkController.PUBLISHER_NEWS_URL + publisher_code + "/news?page=" + page + "&sort_by=time&order=desc";
                newsObservable = mApi.getPublisherNews(publisher_code, page);
                break;
            default:
            case ALL_NEWS:
                url = NetworkController.ALL_NEWS_URL + "?page=" + page + "&sort_by=time&order=desc";
                newsObservable = mApi.getAllNews(page);
                break;
        }

        if (url.length() == 0) return;

        final String ourl = url;

        newsObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<News>() {
                @Override
                public void onNext(News news) {
                    mAdapter.addData(news.getNews());
                    System.out.println(news.getMeta().getCount());
                    loading = true;
                }

                @Override
                public void onCompleted() {
                    System.out.println("Completed!");
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "載入成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error....", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    int mCurrentTabPos = 0;

    private void setupTabLayout() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText("ALL"));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mCurrentTabPos = tab.getPosition();
                mAdapter.clearData();
                page = 1;
                if (mCurrentTabPos == 0)
                    getAllNews(ALL_NEWS, "", page);
                else if (mPublishers.get(tab.getPosition() - 1) != null)
                    getAllNews(PUBLISHER_NEWS, mPublishers.get(tab.getPosition() - 1).getCode(), page);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupRecyclerView() {

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (EmptyRecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        Log.v("...", "Last Item Wow !");
                        page++;
                        if (mCurrentTabPos == 0)
                            getAllNews(ALL_NEWS, "", page);
                        else
                            getAllNews(PUBLISHER_NEWS, mPublishers.get(mCurrentTabPos - 1).getCode(), page);
                    }
                }
            }
        });
        mRecyclerView.setEmptyView(findViewById(R.id.emptyText));
    }

    private API createApi() {

        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(NetworkController.BASE_URL);

        return builder.build().create(API.class);
    }
}
