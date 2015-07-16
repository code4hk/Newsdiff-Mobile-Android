package hk.code4.newsdiffhk;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.LinkedList;

import hk.code4.newsdiffhk.Interface.Api;
import hk.code4.newsdiffhk.Model.NewsDiff;
import hk.code4.newsdiffhk.Util.DiffMatchPatch;
import hk.code4.newsdiffhk.Util.NetworkUtils;
import hk.code4.newsdiffhk.Util.RxUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsDetailFragment extends Fragment {

    TextView toTitle, toContent, percentage, toDate, toPublishedAt;
    TabLayout mTabLayout;
    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private Api mApi;
    private DiffMatchPatch diff = new DiffMatchPatch();
    private boolean secret_mode;

    public NewsDetailFragment() {
    }

    public static NewsDetailFragment newInstance(String oid, String title, int total_revision, boolean secret_mode) {
        Bundle bundle = new Bundle();
        bundle.putString(NewsDetailActivity.EXTRA_ITEM_ID, oid);
        bundle.putString(NewsDetailActivity.EXTRA_ITEM_TITLE, title);
        bundle.putInt(NewsDetailActivity.EXTRA_ITEM_REVISION, total_revision);
        bundle.putBoolean(NewsDetailActivity.EXTRA_ITEM_IS_SECRET_MODE, secret_mode);

        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_detail_single, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final Bundle arguments = getArguments();

        setupToolbar(view);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(arguments.getString(NewsDetailActivity.EXTRA_ITEM_TITLE));

        percentage = (TextView) view.findViewById(R.id.percentage);
        toTitle = (TextView) view.findViewById(R.id.toTitle);
        toContent = (TextView) view.findViewById(R.id.toContent);
        toDate = (TextView) view.findViewById(R.id.toDate);
        toPublishedAt = (TextView) view.findViewById(R.id.toPublishedAt);

        mApi = NetworkUtils.createApi();

        getDetail(arguments.getString(NewsDetailActivity.EXTRA_ITEM_ID)
                , arguments.getInt(NewsDetailActivity.EXTRA_ITEM_REVISION));

        setupTabLayout(view, arguments.getInt(NewsDetailActivity.EXTRA_ITEM_REVISION));

        secret_mode = arguments.getBoolean(NewsDetailActivity.EXTRA_ITEM_IS_SECRET_MODE);

        super.onViewCreated(view, savedInstanceState);
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setNavigationOnClickListener((v)->getActivity().finish());

            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            upArrow.setColorFilter(getResources().getColor(R.color.blackText), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            mActionBar.setNavigationIcon(upArrow);

            mActionBar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));

            // Inflate a menu to be displayed in the toolbar
//            toolbar.inflateMenu(R.menu.settings);
        }
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
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
        final NewsDiff.Revision revisions = newsDiff.getRevisions();
        LinkedList<DiffMatchPatch.Diff> diffs_title = diff.diff_main(revisions.getFrom().getTitle(), revisions.getTo().getTitle());
        LinkedList<DiffMatchPatch.Diff> diffs_content = diff.diff_main(revisions.getFrom().getBody(), revisions.getTo().getBody());

        percentage.setText("修改比例： "+MessageFormat.format("{0,number,#.##%}", newsDiff.getChanges()));
        toTitle.setText(Html.fromHtml(prettyHtml(diffs_title, true)));
        toContent.setText(Html.fromHtml(prettyHtmlContent(diffs_content, false)));
        toPublishedAt.setText(revisions.getTo().getPublishedAt());
        toDate.setText(revisions.getTo().getArchiveTime().toString());
    }

    /**
     * Convert a Diff list into a pretty HTML report.
     * @param diffs LinkedList of Diff objects.
     * @return HTML representation.
     */
    public String prettyHtml(LinkedList<DiffMatchPatch.Diff> diffs, boolean isNoMask) {

        if (secret_mode) isNoMask = true;

        StringBuilder html = new StringBuilder();
        for (DiffMatchPatch.Diff aDiff : diffs) {
            String text = aDiff.text.replace("&", "&amp;").replace("<", "&lt;")
                    .replace(">", "&gt;").replace("\n", "&para;<br>");
            switch (aDiff.operation) {
                case INSERT:
                    html.append("<font color=\"#4169e1\">").append(text)
                            .append("</font>");
                    break;
                case DELETE:
                    html.append("<font color=\"#dc143c\">").append(text)
                            .append("</font>");
                    break;
                case EQUAL:
//                    html.append("<div>").append(text).append("</div>");
                    String[] stringArray = text.split("");
                    for (CharSequence character : stringArray) {
                        if ((character.equals("，")
                                || character.equals("。")
                                || character.equals("「")
                                || character.equals("」")))
                            html.append(character);
                        else {
                            if (isNoMask)
                                html.append(character);
                            else
                                html.append('〇');
                        }
                    }
                    break;
            }
        }
        return html.toString();
    }

    /**
     * Convert a Diff list into a pretty HTML report.
     * @param diffs LinkedList of Diff objects.
     * @return HTML representation.
     */
    public String prettyHtmlContent(LinkedList<DiffMatchPatch.Diff> diffs, boolean isNoMask) {

        if (secret_mode) isNoMask = true;

        StringBuilder html = new StringBuilder();
        for (DiffMatchPatch.Diff aDiff : diffs) {
            String text = aDiff.text.replace("&", "&amp;").replace("<", "&lt;")
                    .replace(">", "&gt;").replace("\n", "&para;<br>");
            switch (aDiff.operation) {
                case INSERT:
                    html.append("<font color=\"#4169e1\">").append(text)
                            .append("</font>");
                    break;
                case DELETE:
                    html.append("<font color=\"#dc143c\">").append(text)
                            .append("</font>");
                    break;
                case EQUAL:
//                    html.append("<div>").append(text).append("</div>");
                    html.append(text);
//                    String[] stringArray = text.split("");
//                    for (CharSequence character : stringArray) {
//                        if ((character.equals("，")
//                                || character.equals("。")
//                                || character.equals("「")
//                                || character.equals("」")))
//                            html.append(character);
//                        else {
//                            if (isNoMask)
//                                html.append(character);
//                            else
//                                html.append('〇');
//                        }
//                    }
                    break;
            }
        }
        String[] stringArray = html.toString().split("，");

        StringBuilder maskHtml = new StringBuilder();

        int x = 0;
        boolean thisNoMask;
        for (String characters : stringArray) {
            if (characters.contains("font")) {
                thisNoMask = true;
            } else {
                thisNoMask = isNoMask;
            }
            String[] sentence = characters.split("");
            for (CharSequence singleChar : sentence) {
                if (thisNoMask
                        || (singleChar.equals("，")
                        || singleChar.equals("。")
                        || singleChar.equals("「")
                        || singleChar.equals("」")
                        || singleChar.equals("！")
                        || singleChar.equals(".")
                        || singleChar.equals("】")
                        || singleChar.equals("【")
                        || singleChar.equals("：")
                        || singleChar.equals("「")
                        || singleChar.equals("」")
                        || singleChar.equals("？")
                        || singleChar.equals("　")))
                    maskHtml.append(singleChar);
                else
                    maskHtml.append("〇");
            }
            if (x != stringArray.length-1)
                maskHtml.append("，");
            x++;
        }
        return maskHtml.toString().replaceAll("\\n", "");
    }

}
