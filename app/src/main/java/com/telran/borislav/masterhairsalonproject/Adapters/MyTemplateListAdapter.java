package com.telran.borislav.masterhairsalonproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.telran.borislav.masterhairsalonproject.Models.WeekDay;
import com.telran.borislav.masterhairsalonproject.R;

import java.util.ArrayList;

/**
 * Created by Boris on 10.06.2017.
 */

public class MyTemplateListAdapter extends RecyclerView.Adapter<MyTemplateListAdapter.MyViewHolderTemplate> {
    private final Context context;
    private ArrayList<WeekDay> items = new ArrayList<>();


    public MyTemplateListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolderTemplate onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schadule_row, parent, false);
        return new MyViewHolderTemplate(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolderTemplate holder, int position) {
        WeekDay item = items.get(position);
        holder.templateEndHour.setText(item.getEndWork().getHourLight());
        holder.templateEndMin.setText(item.getEndWork().getMinuteLight());
        holder.templateStarHour.setText(item.getStartWork().getHourLight());
        holder.templateStarMin.setText(item.getStartWork().getMinuteLight());
        holder.templateCheckBox.setChecked(item.isActiveDay());
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public WeekDay getItem(int position) {
        return items.get(position);
    }

    public void addItem(WeekDay item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addItemAtFront(WeekDay item) {
        items.add(item);
        notifyItemInserted(0);
    }

    public void addItem(WeekDay item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(WeekDay item) {
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

    public void updateItem(WeekDay item, int position) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        WeekDay item = items.remove(fromPosition);
        items.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void clear() {

        notifyItemRangeRemoved(0, items.size());
        items.clear();

    }

    class MyViewHolderTemplate extends RecyclerView.ViewHolder {
        TextView templateStarHour, templateStarMin, templateEndHour, templateEndMin;
        CheckBox templateCheckBox;

        public MyViewHolderTemplate(View itemView) {
            super(itemView);
            templateStarHour = (TextView) itemView.findViewById(R.id.start_hour);
            templateStarMin = (TextView) itemView.findViewById(R.id.start_min);
            templateEndHour = (TextView) itemView.findViewById(R.id.end_hour);
            templateEndMin = (TextView) itemView.findViewById(R.id.end_min);
            templateCheckBox = (CheckBox) itemView.findViewById(R.id.template_check_box);
        }
    }

}
