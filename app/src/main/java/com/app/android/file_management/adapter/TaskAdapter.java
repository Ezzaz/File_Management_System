package com.app.android.file_management.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.android.file_management.animation.AnimationUtil;
import com.app.android.file_management.fragment.DetailsFragment;
import com.app.android.file_management.activity.MainActivity;
import com.app.android.file_management.R;
import com.app.android.file_management.database.TodoTask;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.DataViewHolder>  {


    private Context context;
    private List<TodoTask> taskList;
    private int previousposition=0;

    public TaskAdapter(Context context, List<TodoTask> taskList) {
        this.context = context;
        this.taskList = taskList;
    }


    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.row_task,parent,false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        holder.taskTV.setText(taskList.get(position).getTitle().toString());
        //holder.timeTV.setText("Time : "+taskList.get(position).getTime().toString());
        holder.dateTV.setText("Date : "+taskList.get(position).getDate().toString());

        final  int id = taskList.get(position).getId();

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsFragment fragment = new DetailsFragment();
                fragment.Constructor(id);
                FragmentTransaction fragmentTransaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainFragmentContainer,fragment);
                fragmentTransaction.commit();
            }
        });

        if (position>previousposition){

            //animation view started
            AnimationUtil.animate (holder,true);
        }
        else {

            AnimationUtil.animate (holder,false);
        }

        previousposition=position;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        public TextView taskTV;
        //TextView timeTV;
        public TextView dateTV;
        public RelativeLayout layout;
        public ImageView imgFile;

        public DataViewHolder(View itemView) {
            super(itemView);
            taskTV = itemView.findViewById(R.id.task_row_name);
            //timeTV = itemView.findViewById(R.id.task_row_time);
            dateTV = itemView.findViewById(R.id.task_row_date);
            layout = itemView.findViewById(R.id.taskLayout);
            imgFile=itemView.findViewById (R.id.img_file);
        }
    }
}
