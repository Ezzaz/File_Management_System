package com.app.android.file_management.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.android.file_management.activity.MainActivity;
import com.app.android.file_management.R;
import com.app.android.file_management.animation.AnimationUtil;
import com.app.android.file_management.database.Category;
import com.app.android.file_management.database.DatabaseSource;
import com.app.android.file_management.database.TodoTask;
import com.app.android.file_management.fragment.TaskFragment;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.DataViewHolder> {


    private Context context;
    private List<Category> categoryList;
    private int previousposition=0;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.row_card,parent,false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        holder.categoryTV.setText(categoryList.get(position).getName());

        DatabaseSource source = new DatabaseSource(context);
        List<TodoTask> tasksList = source.getTaskByCategory(categoryList.get(position).getName());
        holder.taskTV.setText("File : "+String.valueOf(tasksList.size()));

        final String category = categoryList.get(position).getName();
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskFragment fragment = new TaskFragment();
                fragment.Constructor(category);
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
        return categoryList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTV;
        TextView taskTV;
        LinearLayout layout;

        public DataViewHolder(View itemView) {
            super(itemView);
            categoryTV = itemView.findViewById(R.id.cardCategory);
            taskTV = itemView.findViewById(R.id.cardTask);
            layout = itemView.findViewById(R.id.cardLayout);
        }
    }


}
