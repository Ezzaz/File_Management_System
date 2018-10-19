package com.app.android.file_management.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.app.android.file_management.activity.MainActivity;
import com.app.android.file_management.R;
import com.app.android.file_management.adapter.TaskAdapter;
import com.app.android.file_management.database.DatabaseSource;
import com.app.android.file_management.database.TodoTask;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment{


    private FrameLayout mainLayout;
    private RecyclerView dataView;
    private List<TodoTask> tasksList;
    private TaskAdapter adapter;

    private String  category_name;

    public TaskFragment() {
        // Required empty public constructor
    }

    public void Constructor(String name) {
        this.category_name = name;
    }

    public void searchVaule(ArrayList<TodoTask> tasks) {
        this.tasksList = tasks;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_task, container, false);

        DatabaseSource source = new DatabaseSource(getContext());

        if(category_name.equals("search")){
            tasksList = tasksList;
        }
        else {
            tasksList = source.getTaskByCategory(category_name);
        }

        dataView = view.findViewById(R.id.taskRV);
        mainLayout=view.findViewById (R.id.rootLayout);


        if (tasksList.size()!=0) {

            adapter = new TaskAdapter (getActivity ( ), tasksList);
            GridLayoutManager llm = new GridLayoutManager (getContext ( ), 1);
            llm.setOrientation (LinearLayoutManager.VERTICAL);
            dataView.setLayoutManager (llm);
            dataView.setAdapter (adapter);

        }

        else {
            AlertDialog.Builder dialog;
            dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("There are no "+category_name+ " file");
            dialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(getContext(),MainActivity.class));
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        }


        return view;

    }
}
