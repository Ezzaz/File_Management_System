package com.app.android.file_management.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.file_management.activity.EditActivity;
import com.app.android.file_management.R;
import com.app.android.file_management.database.DatabaseSource;
import com.app.android.file_management.database.TodoTask;

import java.io.File;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private int taskId ;
    private List<TodoTask> tasksList;

    private TextView titleET;
    private TextView despET;
    private TextView dateET;
    //private TextView timeET;
    private TextView categoryET;
    private CheckBox isCompleteCB;
    private Button document_btn;
    private Button edit_btn;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public void Constructor(int id) {
        this.taskId = id;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        titleET = view.findViewById(R.id.detailsName);
        despET = view.findViewById(R.id.detailsDesp);
        dateET = view.findViewById(R.id.detailsDate);
        //timeET = view.findViewById(R.id.detailsTime);
        categoryET = view.findViewById(R.id.detailsCategory);
        isCompleteCB = view.findViewById(R.id.detailsComplete);
        document_btn = view.findViewById(R.id.detailsDocument);
        edit_btn = view.findViewById(R.id.detailsEdit);

        final DatabaseSource source = new DatabaseSource(getContext());
        tasksList = source.getTaskById( taskId);

        if (tasksList.size()!=0){
            titleET.setText(tasksList.get(0).getTitle().toString());
            despET.setText(tasksList.get(0).getDesp().toString());
            dateET.setText(tasksList.get(0).getDate().toString());
            //timeET.setText(tasksList.get(0).getTime().toString());
            categoryET.setText(tasksList.get(0).getCategory().toString());

            if (tasksList.get(0).getIsComplete() == 1){
                isCompleteCB.setChecked(true);
            }
            else {
                isCompleteCB.setChecked(false);
            }

        }

        isCompleteCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isCompleteCB.isChecked()){
                    boolean complete = source.setTodoTaskCompleteById(taskId);
                    if (complete){
                        Toast.makeText(getContext(), "File Complete", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    boolean complete = source.setTodoTaskPendingById(taskId);
                    if (complete){
                        Toast.makeText(getContext(), "File Pending", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        document_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tasksList.get(0).getPath().equals("null")){
                    Toast.makeText(getContext(), "No Document Found", Toast.LENGTH_SHORT).show();
                }
                else {
                    String filePath = tasksList.get(0).getPath();
                    Intent myIntent = new Intent(Intent.ACTION_VIEW);

                    String word = ".pdf";
                    String text = tasksList.get(0).getPath();
                    Boolean found = text.contains(word);

                    if (found){
                        myIntent.setDataAndType(Uri.fromFile( new File(filePath)),"application/pdf");
                    }
                    else {
                        myIntent.setDataAndType(Uri.fromFile( new File(filePath)),"image/*");
                    }
                    Intent intent = Intent.createChooser(myIntent, "Choose an application to open with:");
                    startActivity(intent);
                }
            }
        });


        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), EditActivity.class);

                intent.putExtra("id",tasksList.get(0).getId());
                intent.putExtra("title",tasksList.get(0).getTitle());
                intent.putExtra("desp",tasksList.get(0).getDesp());
                intent.putExtra("time",tasksList.get(0).getTime());
                intent.putExtra("date",tasksList.get(0).getDate());
                intent.putExtra("path",tasksList.get(0).getPath());

                startActivity(intent);
            }
        });

        return view;
    }

}