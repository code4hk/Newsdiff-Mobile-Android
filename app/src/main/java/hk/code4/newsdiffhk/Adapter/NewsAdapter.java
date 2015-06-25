package hk.code4.newsdiffhk.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import hk.code4.newsdiffhk.Model.News;
import hk.code4.newsdiffhk.Model.NewsItem;
import hk.code4.newsdiffhk.Model.Publisher;
import hk.code4.newsdiffhk.R;

/**
 * Created by allen517 on 3/6/15.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private News mDataset;
    List<Publisher> mPublishers;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView section_info, section_label, section_changes, section_content;
        public ViewHolder(View v) {
            super(v);
            section_info = (TextView) v.findViewById(R.id.section_info);
            section_label = (TextView) v.findViewById(R.id.section_label);
            section_changes = (TextView) v.findViewById(R.id.section_changes);
            section_content = (TextView) v.findViewById(R.id.section_content);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdapter() {
    }

    public void setData(News news){
        mDataset = news;
        visibleObjects = mDataset.getNews();
    }

    public void setPublisher(List<Publisher> publishers) {
        mPublishers = publishers;
    }

    private String findPublisherName(String code) {
        for (Publisher publisher:mPublishers) {
            if (publisher.getCode().equals(code))
                return publisher.getName();
        }
        return code;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder holder = new ViewHolder(v);
//        holder.section_changes
//        holder.section_label = (TextView) v.findViewById(R.id.section_label);
//        android:id="@+id/section_info"
//        android:id="@+id/section_changes"
//        android:id="@+id/section_content"
//        switch (type){
//            case HEADER:
//                return HeaderHolder.newInstance(viewGroup);
//            case DIVIDER:
//                return DividerHolder.newInstance(viewGroup);
//            default:
//                return FirstViewHolder.newInstance(viewGroup);
//        }
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (visibleObjects == null) return;
//        if (mDataset.getNews() == null) return;
        NewsItem item = visibleObjects.get(position);
        if (item == null) return;
        holder.section_label.setText(item.getTitle());
        holder.section_info.setText(""+item.getUpdatedAt());
        holder.section_changes.setText(findPublisherName(item.getPublisher()) + " / " + MessageFormat.format("{0,number,#.##%}", item.getChanges()));
//        holder.section_content.setText(item.getTitle());
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.getNews().size();
    }
    private List<NewsItem> visibleObjects = new ArrayList<>();

    public void flushFilter(){
        visibleObjects.addAll(mDataset.getNews());
        notifyDataSetChanged();
    }

    public void setFilter(String publisher) {

        visibleObjects.clear();
        for (NewsItem item: mDataset.getNews()) {
            if (item.getPublisher().equals(publisher))
                visibleObjects.add(item);
        }
        notifyDataSetChanged();
    }
}
