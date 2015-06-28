package hk.code4.newsdiffhk;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import hk.code4.newsdiffhk.Adapter.NewsAdapter;
import hk.code4.newsdiffhk.DAO.NetworkController;
import hk.code4.newsdiffhk.Model.News;
import hk.code4.newsdiffhk.Model.Publisher;
import hk.code4.newsdiffhk.Util.NetworkUtils;
import hk.code4.newsdiffhk.Widget.EmptyRecyclerView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    NetworkController mNetworkController;

    List<Publisher> mPublishers;
    News mNews;
    TabLayout mTabLayout;
    NewsAdapter mAdapter;
    EmptyRecyclerView mRecyclerView;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupTabLayout();

        mNetworkController = NetworkController.getInstance();

        mAdapter = new NewsAdapter();
        mAdapter.setOnItemClickListener((view, position) -> {
            NewsDetailActivity.start(this, mNews.getNews().get(position).getId());
        });

        setupRecyclerView();

        if (NetworkUtils.isOnline(MainActivity.this))
            getAllPublisher();
    }

    private void getAllPublisher() {

        Observable.defer(() -> Observable.just(mNetworkController.getJson(NetworkController.ALL_PUBLISHER_URL)))
                .map(mNetworkController::getPublisher)
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
                        getAllNews();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private void getAllNews() {

        Observable.defer(() -> Observable.just(mNetworkController.getJson(NetworkController.ALL_NEWS_URL)))
                .map(mNetworkController::getNews)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onNext(News news) {
                        mNews = news;
                        mAdapter.setData(news);
                        mAdapter.notifyDataSetChanged();
                        System.out.println(news.getMeta().getCount());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Completed!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupTabLayout() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText("ALL"));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    mAdapter.flushFilter();
                else
                    mAdapter.setFilter(mPublishers.get(tab.getPosition()-1).getCode());
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
                    }
                }
            }
        });
        mRecyclerView.setEmptyView(findViewById(R.id.emptyText));
    }
}
