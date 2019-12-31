package com.cblib.util;

import android.content.Context;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public abstract class CBRecyclerArrayAdapter<M, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements Filterable {

    private ArrayList<M> items = new ArrayList<>();
    public Context mContext;

    public CBRecyclerArrayAdapter(Context context) {
        mContext = context;
        setHasStableIds(true);
    }

    public CBRecyclerArrayAdapter(Context context, ArrayList<M> items) {
        this.items = items;
        mContext = context;
        setHasStableIds(true);
    }

    public void add(M object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, M object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends M> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    @SafeVarargs
    public final void addAll(M... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public synchronized void notifyItemRemoved(int position, M object) {
        if (position == 0) {
            items.remove(object);
            notifyDataSetChanged();
        } else {
            super.notifyItemRemoved(position);
            items.remove(object);
        }
    }

    public void remove(M object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public ArrayList<M> getList() {
        return items;
    }

    public M getItem(int position) {
        return items.get(position);
    }

    public M getItem(RecyclerView.ViewHolder viewHolder) {
        return items.get(viewHolder.getAdapterPosition());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else return 0;
    }

    private long lastClickTime;

    /**
     * prevent double click
     */
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (Math.abs(time - lastClickTime) < 400) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /* for filter */
    @Override
    public CBFilter getFilter() {
        return null;
    }
}
