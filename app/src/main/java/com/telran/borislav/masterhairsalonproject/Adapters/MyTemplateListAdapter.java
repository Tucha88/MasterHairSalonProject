package com.telran.borislav.masterhairsalonproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.telran.borislav.masterhairsalonproject.Models.WeekDay;
import com.telran.borislav.masterhairsalonproject.R;

import java.util.ArrayList;

/**
 * Created by Boris on 10.06.2017.
 */

public class MyTemplateListAdapter extends RecyclerView.Adapter<MyTemplateListAdapter.MyViewHolderTemplate> {
    private static ClickListener mClickListener;
    private final Context context;
    private ArrayList<WeekDay> items = new ArrayList<>();
    private String[] strings;
    private boolean onBind;



    public MyTemplateListAdapter(Context context) {
        this.context = context;
        strings = this.context.getResources().getStringArray(R.array.day_of_week_array);
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
        holder.dayOfWeek.setText(strings[position]);
        holder.templateStarHour.setText(item.getStartWork());
        holder.templateEndHour.setText(item.getEndWork());
        onBind = true;
        holder.templateCheckBox.setChecked(item.isActiveDay());
        onBind = false;
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

    public void setOnItemClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onChecked(int position, boolean isChecked);
    }

    class MyViewHolderTemplate extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        TextView templateStarHour, templateEndHour, dayOfWeek;
        CheckBox templateCheckBox;

        public MyViewHolderTemplate(View itemView) {
            super(itemView);
            dayOfWeek = (TextView) itemView.findViewById(R.id.day_of_week_text_view);
            templateStarHour = (TextView) itemView.findViewById(R.id.start_hour);
            templateEndHour = (TextView) itemView.findViewById(R.id.end_hour);
            templateCheckBox = (CheckBox) itemView.findViewById(R.id.template_check_box);
            templateStarHour.setOnClickListener(this);
            templateEndHour.setOnClickListener(this);
            templateCheckBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!onBind) {
                WeekDay weekDay = getItem(getAdapterPosition());
                weekDay.setActiveDay(isChecked);
                updateItem(weekDay, getAdapterPosition());
                notifyDataSetChanged();
            }

        }
    }

}
