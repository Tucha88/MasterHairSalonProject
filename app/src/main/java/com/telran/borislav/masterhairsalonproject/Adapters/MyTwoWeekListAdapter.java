package com.telran.borislav.masterhairsalonproject.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telran.borislav.masterhairsalonproject.Models.CalendarDayCustom;
import com.telran.borislav.masterhairsalonproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Boris on 11.06.2017.
 */

public class MyTwoWeekListAdapter extends RecyclerView.Adapter<MyTwoWeekListAdapter.TwoWeekScheduleViewHolder> {
    private static ScheduleClickListener mClickListener;
    private final Context context;
    private ArrayList<CalendarDayCustom> items = new ArrayList();

    public MyTwoWeekListAdapter(Context context) {
        this.context = context;
    }

    public void setmClickListener(ScheduleClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    @Override
    public TwoWeekScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.two_week_schedule_row, parent, false);
        return new TwoWeekScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TwoWeekScheduleViewHolder holder, int position) {
        CalendarDayCustom item = items.get(position);
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd");

        try {
            cal.setTime(format.parse(item.getMyCalendar()));
            holder.scheduleDay.setText(cal.get(Calendar.DAY_OF_MONTH)/*+" " + cal.get(Calendar.MONTH)*/
                    + " " + android.text.format.DateFormat.format("mm", cal.getTime())
                    + " " + android.text.format.DateFormat.format("EE", cal.getTime()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!item.isWorking()) {
            holder.scheduleEndHour.setText(" ");
            holder.scheduleStarHour.setText(" ");
            holder.isWordDay.setText("Day off");
        } else {
            holder.scheduleEndHour.setText(item.getEndWork().getHourLight() + ":" + item.getEndWork().getMinuteLight());
            holder.scheduleStarHour.setText(item.getStartWork().getHourLight() + ":" + item.getStartWork().getMinuteLight());
            holder.isWordDay.setText("Work day");
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }


    public CalendarDayCustom getItem(int position) {

        return items.get(position);
    }

    public void addItem(CalendarDayCustom item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addItemAtFront(CalendarDayCustom item) {
        items.add(item);
        notifyItemInserted(0);
    }

    public void addItem(CalendarDayCustom item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(CalendarDayCustom item) {
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

    public void updateItem(CalendarDayCustom item, int position) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        CalendarDayCustom item = items.remove(fromPosition);
        items.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void clear() {
        notifyItemRangeRemoved(0, items.size());
        items.clear();

    }

    public interface ScheduleClickListener {
        void onItemClick(int position);
    }

    class TwoWeekScheduleViewHolder extends RecyclerView.ViewHolder {
        private TextView scheduleStarHour, scheduleEndHour, scheduleDay, isWordDay;


        public TwoWeekScheduleViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(getAdapterPosition());
                }
            });

            scheduleStarHour = (TextView) itemView.findViewById(R.id.two_weeks_schedule_start_hour);
            scheduleEndHour = (TextView) itemView.findViewById(R.id.two_weeks_schedule_end_hour);
            scheduleDay = (TextView) itemView.findViewById(R.id.day_of_schedule_text_view);
            isWordDay = (TextView) itemView.findViewById(R.id.two_weeks_schedule_work_day);

        }
    }
}
