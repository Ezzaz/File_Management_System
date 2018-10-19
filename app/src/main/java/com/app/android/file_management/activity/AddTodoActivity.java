package com.app.android.file_management.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.android.file_management.R;
import com.app.android.file_management.database.Category;
import com.app.android.file_management.database.DatabaseSource;
import com.app.android.file_management.database.TodoTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTodoActivity extends AppCompatActivity {

    //-- Layout Component From XML --//
    private EditText taskET;
    private EditText detailsET;
    private Spinner categorySP;
    private Button date_btn;
    //private Button time_btn;
    private Button upload_btn;
    private Button submit_btn;

    //-- declare variable for time and date --//
    private Calendar calendar;
    private int day, month ,year;
    private int hour,min;

    //-- declare variable for components --//
    private String taskName;
    private String taskDetails;
    private String categoryName;
    private String finalDate;
    private String finalTime;
    private String selectedFilePath ="null";

    //--declare category list variable--//
    private static List<Category> categoryList = new ArrayList<>();

    // this is the action code we use in our intent,
    // this way we know we're looking at the response from our own action
    private static final int SELECT_FILE = 1;

    //-- this variable for for database access--//
    private static DatabaseSource source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        //-- set action bar title and back button --//
        getSupportActionBar().setTitle("Adding file");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //--initialize database source --//
        source = new DatabaseSource(this);

        //-- find view by id  --//
        taskET = findViewById(R.id.taskName);
        detailsET = findViewById(R.id.taskDetails);
        categorySP = findViewById(R.id.taskCategory);
        date_btn = findViewById(R.id.taskDate);
        //time_btn = findViewById(R.id.taskTime);
        upload_btn = findViewById(R.id.upload);
        submit_btn = findViewById(R.id.taskSubmit);

        //--initialize calender value for time and date --//
        calendar = Calendar.getInstance();
        day   = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year  = calendar.get(Calendar.YEAR);
        hour  = calendar.get(Calendar.HOUR_OF_DAY);
        min   = calendar.get(Calendar.MINUTE);

        //-- adapter for load category item in spinner --//
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,getCategoryList());
        categorySP.setAdapter(adapter);

        //--action for spiner item select --//
        categorySP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryName= adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                categoryName ="Select Category";
            }
        });

        //--when click date button open calender dialog --//
        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(AddTodoActivity.this,dateListner,year,month,day);
                dialog.show();
            }
        });


        //-- upload media file --//
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // in onCreate or any event where your want the user to
            // select a file
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                    "Select File"), SELECT_FILE);
            }
         });


        //-- create new task --//
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //--call create new method --//
                createNewTask();
            }
        });


    }

    public  void editTodoTask(List<TodoTask> tasksList){
        taskET.setText(tasksList.get(0).getTitle().toString());
        detailsET.setText(tasksList.get(0).getDesp().toString());
        date_btn.setText(tasksList.get(0).getDate().toString());
        //time_btn.setText(tasksList.get(0).getTime().toString());

    }

    private void createNewTask() {
        taskName = taskET.getText().toString();
        taskDetails = detailsET.getText().toString();

        if (TextUtils.isEmpty(taskName)) {
            taskET.setError("User name cannot be empty");
        }
        else if (TextUtils.isEmpty(taskDetails)) {
            detailsET.setError("Password cannot be empty");
        }
        else if (categoryName.equals("Select Category")) {
            Toast.makeText(this, "Please Select Category", Toast.LENGTH_SHORT).show();
        }
        else if (date_btn.getText().equals("Date")) {
            Toast.makeText(this, "Please Select Date", Toast.LENGTH_SHORT).show();
        }

        else{
            taskET.setText("");
            detailsET.setText("");
            date_btn.setText(("Date"));
            //time_btn.setText("Time");
            upload_btn.setText("upload file");

            TodoTask task = new TodoTask(0,taskName,taskDetails,"null",finalDate,categoryName,0,selectedFilePath);
            Boolean isInsert = source.setTodoTask(task);

            if (isInsert){
                AlertDialog  alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setMessage("Successfully Create a new file");
                alertDialog.show();
            }
            else {
                Toast.makeText(this, "Failed to create a new file, please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static ArrayList<String> getCategoryList(){

        categoryList = source.getAllCategory();
        int size = categoryList.size();

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Select Category");
        for (int i=0;i<size;i++){
         categories.add(categoryList.get(i).getName().toString());
        }
        return categories;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                startActivity(new Intent(AddTodoActivity.this,MainActivity.class));
                finish(); //close the activty
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    TimePickerDialog.OnTimeSetListener timeListner = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int min) {
            calendar.set(0,0,0,hour,min);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            finalTime = sdf.format(calendar.getTime());
            //time_btn.setText(finalTime.toString());
        }
    };


    DatePickerDialog.OnDateSetListener dateListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            calendar.set(year,month,day);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            finalDate = sdf.format(calendar.getTime());
            date_btn.setText(finalDate.toString());
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Uri selectedFileUri = data.getData();
                selectedFilePath = getPath(selectedFileUri);

                //Toast.makeText(this, ""+selectedFilePath, Toast.LENGTH_SHORT).show();
                upload_btn.setText(selectedFilePath);
            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
//        String[] projection = { MediaStore.Images.Media.DATA };
        String[] projection = { MediaStore.Files.FileColumns.DATA};


        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }
}
