package hk.code4.newsdiffhk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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


/**
 * Layout for the photo list view header.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
//    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

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

        setUpToolbar();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText("ALL"));
        mTabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

        mNetworkController = NetworkController.getInstance();

        mRecyclerView = (EmptyRecyclerView) findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the activity_main size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear activity_main manager
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new NewsAdapter();
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
        mAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("oid", mNews.getNews().get(position).getId());
            startActivity(intent);
        });

        if (NetworkUtils.isOnline(MainActivity.this))
            getAllPublisher();
    }

    private void getAllPublisher() {

        Observable.defer(() -> Observable.just(mNetworkController.getJson(NetworkController.ALL_PUBLISHER_URL)))
                .map(mNetworkController::getPublisher)
                .flatMap(list-> {
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

//    <T> Observable.Transformer<T, T> applySchedulers() {
//        return observable -> observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
