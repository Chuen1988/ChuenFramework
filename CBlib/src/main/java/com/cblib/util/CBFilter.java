package com.cblib.util;

import android.widget.Filter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class CBFilter<T> extends Filter {

    public abstract FilterResults filtering(CharSequence constraint, FilterResults results, List fullList);

    private List oldList;
    private List fullList;
    private RecyclerView.Adapter adapter;
    private boolean isSearching;

    public CBFilter(List oldList, List fullList, RecyclerView.Adapter adapter) {
        this.oldList = oldList;
        this.fullList = fullList;
        this.adapter = adapter;
        isSearching = false;
    }

    public void setSearching(boolean searching) {
        isSearching = searching;
        filter("");
    }

    public void setSearching(boolean searching, FilterListener filterListener) {
        isSearching = searching;
        filter("", filterListener);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0) {
            filtering(constraint, results, fullList);
        } else {
            if (!isSearching) {
                results.count = fullList.size();
                results.values = fullList;
            } else {
                results.count = 0;
                results.values = new ArrayList<>();
            }
        }

        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        oldList.clear();
        List<T> filterList = (List<T>) results.values;
        for (int i = 0; i < results.count; i++) {
            oldList.add(filterList.get(i));
        }
        adapter.notifyDataSetChanged();
    }
}