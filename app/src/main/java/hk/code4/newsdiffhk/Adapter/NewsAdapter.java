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
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
//    private News mDataset;
    private List<NewsItem> mDataset = new ArrayList<>();
    List<Publisher> mPublishers;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView section_create_date, section_update_date, section_label, section_changes, section_content;

//        public static ViewHolder newInstance(ViewGroup viewGroup) {
//            return new ViewHolder(
//                    LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_list_row, viewGroup, false)
//                    );
//        }

        public ViewHolder(View v) {
            super(v);
            section_create_date = (TextView) v.findViewById(R.id.section_create_date);
            section_update_date = (TextView) v.findViewById(R.id.section_update_date);
            section_label = (TextView) v.findViewById(R.id.section_label);
            section_changes = (TextView) v.findViewById(R.id.section_changes);
            section_content = (TextView) v.findViewById(R.id.section_content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdapter() {
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(final OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (visibleObjects == null) return;

        NewsItem item = visibleObjects.get(position);
        if (item == null) return;

        holder.section_label.setText(item.getTitle());
        holder.section_create_date.setText("" + item.getCreatedAt());
        holder.section_update_date.setText("" + item.getUpdatedAt());
        StringBuilder sb = new StringBuilder(findPublisherName(item.getPublisher()));
        sb.append(" / ");
        sb.append("第");
        sb.append(item.getCount()-1);
        sb.append("次修改");
        sb.append(" / ");
        sb.append(MessageFormat.format("{0,number,#.##%}", item.getChanges()));
        sb.append("改動");
        holder.section_changes.setText(sb.toString());
//        holder.section_content.setText(item.getTitle());
    }

    public NewsItem getItem(int pos){
        try {
            return mDataset.get(pos);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private List<NewsItem> visibleObjects = new ArrayList<>();
    @Override
    public int getItemCount() {
        if (visibleObjects == null) return 0;
        return visibleObjects.size();
    }

    public void clearData() {
        mDataset.clear();
        notifyDataSetChanged();
    }

    public void addData(List<NewsItem> news){
        for (NewsItem item: news) {
            mDataset.add(item);
        }
        visibleObjects.clear();
        visibleObjects.addAll(mDataset);
        notifyDataSetChanged();
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

//    public void flushFilter(){
////        visibleObjects.addAll(mDataset.getAllNews());
//        visibleObjects = news;
//        notifyDataSetChanged();
//    }
//
//    public void setFilter(String publisher) {
//
//        visibleObjects.clear();
//        for (NewsItem item: mDataset.getAllNews()) {
//            if (item.getPublisher().equals(publisher))
//                visibleObjects.add(item);
//        }
//        notifyDataSetChanged();
//    }
}
