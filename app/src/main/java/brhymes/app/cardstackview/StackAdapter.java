package brhymes.app.cardstackview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class StackAdapter<T> extends HorizontalCardStackView.Adapter<StackAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<T> mData;
    public static final int INVALID_TYPE = -1;

    public StackAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = new ArrayList();
    }

    public void updateData(List<T> data) {
        this.setData(data);
        this.notifyDataSetChanged();
    }

    public void setData(List<T> data) {
        this.mData.clear();
        if (data != null) {
            this.mData.addAll(data);
        }
    }

    public LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override
    public void onBindViewHolder(StackAdapter.ViewHolder holder, int position) {
        T data = this.getItem(position);
        this.bindView(data, position, holder);
    }

    public abstract void bindView(T data, int position, StackAdapter.ViewHolder holder);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(int position) {
        return this.mData.get(position);
    }

    public static abstract class ViewHolder {

        public View itemView;
        int mItemViewType = INVALID_TYPE;
        int position;

        public ViewHolder(View view) {
            itemView = view;
        }

    }

}
