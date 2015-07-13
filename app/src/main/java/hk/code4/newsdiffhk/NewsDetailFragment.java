package hk.code4.newsdiffhk;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.MessageFormat;

import hk.code4.newsdiffhk.DAO.NetworkController;
import hk.code4.newsdiffhk.Interface.API;
import hk.code4.newsdiffhk.Model.NewsDiff;
import hk.code4.newsdiffhk.Util.RxUtils;
import retrofit.RestAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsDetailFragment extends Fragment {

    NetworkController mNetworkController;
    TextView emptyText, title, fromTitle, fromContent, toTitle, toContent, percentage, fromDate, toDate, fromPublishedAt, toPublishedAt;
//    ProgressBar progress;
    TabLayout mTabLayout;
    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private API mApi;

    public NewsDetailFragment() {
    }

    public static NewsDetailFragment newInstance(String oid, int total_revision) {
        Bundle bundle = new Bundle();
        bundle.putString(NewsDetailActivity.EXTRA_ITEM_ID, oid);
        bundle.putInt(NewsDetailActivity.EXTRA_ITEM_REVISION, total_revision);

        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final Bundle arguments = getArguments();

        percentage = (TextView) view.findViewById(R.id.percentage);
        fromTitle = (TextView) view.findViewById(R.id.fromTitle);
        fromContent = (TextView) view.findViewById(R.id.fromContent);
        toTitle = (TextView) view.findViewById(R.id.toTitle);
        toContent = (TextView) view.findViewById(R.id.toContent);
        fromDate = (TextView) view.findViewById(R.id.fromDate);
        toDate = (TextView) view.findViewById(R.id.toDate);
        fromPublishedAt = (TextView) view.findViewById(R.id.fromPublishedAt);
        toPublishedAt = (TextView) view.findViewById(R.id.toPublishedAt);

        mApi = createApi();

        if (null != arguments)
            getDetail(arguments.getString(NewsDetailActivity.EXTRA_ITEM_ID), arguments.getInt(NewsDetailActivity.EXTRA_ITEM_REVISION));

//        emptyText = (TextView) view.findViewById(R.id.emptyText);
        setupTabLayout(view, arguments.getInt(NewsDetailActivity.EXTRA_ITEM_REVISION));

        super.onViewCreated(view, savedInstanceState);
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

    SparseArray<NewsDiff> revision = new SparseArray<NewsDiff>();
    private void getDetail(String oid, int total_count) {

        mNetworkController = NetworkController.getInstance();

        for (int x = total_count; x > 1 ; x--) {
            int y = x-1;
            int z = y-1;

            _subscriptions.add(mApi.getNewsDetail(oid, z, y)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsDiff>() {
                    @Override
                    public void onNext(NewsDiff newsDiff) {
                        revision.put(y, newsDiff);
                        if (y == total_count - 1) setUI(newsDiff);
                        setProgressBarVisibility(View.GONE);
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Completed!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }));
        }
    }

    private void setProgressBarVisibility(int visibility) {
//        emptyText.setVisibility(visibility);
    }

    private void setupTabLayout(View view, int total_count) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        int tabCount = total_count - 1;
        for (int x = tabCount; x > 0 ; x--){
            mTabLayout.addTab(mTabLayout.newTab().setText("第 "+x+" 次修改"));
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int y = tabCount - tab.getPosition();
                NewsDiff newsDiff = revision.get(y);
                if (newsDiff != null) setUI(newsDiff);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUI(NewsDiff newsDiff) {
        percentage.setText("修改比例： "+MessageFormat.format("{0,number,#.##%}", newsDiff.getChanges()));
        final NewsDiff.Revision revisions = newsDiff.getRevisions();
        fromTitle.setText(revisions.getFrom().getTitle());
        fromContent.setText(Html.fromHtml(revisions.getFrom().getContent()));
        toTitle.setText(revisions.getTo().getTitle());
        toContent.setText(Html.fromHtml(revisions.getTo().getContent()));
        fromPublishedAt.setText(revisions.getFrom().getPublishedAt());
        toPublishedAt.setText(revisions.getTo().getPublishedAt());
        fromDate.setText(revisions.getFrom().getArchiveTime().toString());
        toDate.setText(revisions.getTo().getArchiveTime().toString());
    }

    private API createApi() {

        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(NetworkController.BASE_URL);

        return builder.build().create(API.class);
    }
}
