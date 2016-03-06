package com.sachingupta.android_smarttodolist.ToDo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sachingupta.android_smarttodolist.R;
import com.sachingupta.android_smarttodolist.ToDoDetailActivity;

import java.util.ArrayList;

public class ToDoCustomAdapter extends BaseAdapter {
    ArrayList<ToDo> toDoList;
    Context context;
    private static LayoutInflater inflater=null;

    public ToDoCustomAdapter(Activity activity, ArrayList<ToDo> myToDoList){
        this.context = activity;
        this.toDoList = myToDoList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   }

    @Override
    public int getCount() {
        return toDoList.size();
    }

    @Override
    public ToDo getItem(int position) {
        return toDoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder
    {
        TextView titleView;
        TextView descriptionView;
        TextView categoryView;
        TextView startTimeView;
        TextView endTimeView;
        TextView statusView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        final ToDo currentToDo = getItem(position);
        View rowView = inflater.inflate(R.layout.list_item_to_do, null);
        holder.titleView = (TextView) rowView.findViewById(R.id.toDoTitle);
        holder.descriptionView = (TextView) rowView.findViewById(R.id.toDoDescription);
        holder.startTimeView = (TextView) rowView.findViewById(R.id.toDoStartTime);
        holder.endTimeView = (TextView) rowView.findViewById(R.id.toDoEndTime);
        holder.titleView.setText(currentToDo.Title);
        holder.descriptionView.setText(currentToDo.Description);
        holder.startTimeView.setText(currentToDo.StartTime.toString());
        holder.endTimeView.setText(currentToDo.EndTime.toString());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ToDoDetailActivity.class);
                intent.putExtra("toDoObject", currentToDo);
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
