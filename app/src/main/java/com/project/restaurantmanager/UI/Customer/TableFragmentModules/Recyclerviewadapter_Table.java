package com.project.restaurantmanager.UI.Customer.TableFragmentModules;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.restaurantmanager.R;

import java.util.List;

public class Recyclerviewadapter_Table extends RecyclerView.Adapter<Recyclerviewadapter_Table.viewholder> {
    private Context context;
    private List<String> timeList;
    int lastPos;
    int i =0;
    public static String startTimeDB="11:00",endTimeDB="12:00";

    public Recyclerviewadapter_Table (Context context, List<String> timeList) {
        this.context = context;
        this.timeList = timeList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_time_holder_layout,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {
        if(position!=getItemCount()) {
            holder.time.setTextOff(timeList.get(position));
            holder.time.setTextOn(timeList.get(position));
            holder.time.setChecked(lastPos == position);
            startTimeDB = timeList.get(lastPos);
            endTimeDB=timeList.get(lastPos+1);
        }

        if(holder.time.isChecked()){
            holder.time.setTextColor(Color.WHITE);
        }
        else
        {
            holder.time.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return timeList.size()-1;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ToggleButton time;

        public viewholder(@NonNull final View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.cust_reservation_timebutton);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastPos = getAdapterPosition();

                    startTimeDB=timeList.get(getAdapterPosition());
                    endTimeDB=timeList.get(getAdapterPosition()+1);

                    notifyItemRangeChanged(0, timeList.size());
                }
            };
            itemView.setOnClickListener(clickListener);
            time.setOnClickListener(clickListener);
        }
    }
}


