package com.telran.borislav.masterhairsalonproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.telran.borislav.masterhairsalonproject.Models.Services;
import com.telran.borislav.masterhairsalonproject.R;

import java.util.ArrayList;

/**
 * Created by Boris on 21.06.2017.
 */

public class MyChoseServiceAdapter extends RecyclerView.Adapter<MyChoseServiceAdapter.MyViewHolder> {
    private final Context context;
    private ArrayList<Services> items = new ArrayList<>();

    public MyChoseServiceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chose_service_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Services item = items.get(position);
        holder.servicesTxt.setText(item.getName());
        holder.priceTxt.setText(String.valueOf(item.getPrice()));
        holder.timeTxt.setText(String.valueOf(item.getDuration()));
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public Services getItem(int position) {
        return items.get(position);
    }

    public void addItem(Services item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addItemAtFront(Services item) {
        items.add(item);
        notifyItemInserted(0);
    }

    public void addItem(Services item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(Services item) {
        int position = items.indexOf(items);
        if (position > 0) {
            items.remove(item);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(int position) {
        if (position > 0 && position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(int startPosition, int count) {
        if (startPosition > 0 && startPosition < items.size() && startPosition + count < items.size()) {
            for (int i = startPosition; i < startPosition + count; i++) {
                items.remove(i);
            }
            notifyItemRangeRemoved(startPosition, count);
        }
    }

    public void updateItem(Services item, int position) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        Services item = items.remove(fromPosition);
        items.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void clear() {

        notifyItemRangeRemoved(0, items.size());
        items.clear();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView servicesTxt, priceTxt, timeTxt;
        private CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            servicesTxt = (TextView) itemView.findViewById(R.id.chose_service_name_txt);
            priceTxt = (TextView) itemView.findViewById(R.id.chose_service_price_txt);
            timeTxt = (TextView) itemView.findViewById(R.id.chose_service_time_txt);
            checkBox = (CheckBox) itemView.findViewById(R.id.chose_service_check);
        }
    }
}
