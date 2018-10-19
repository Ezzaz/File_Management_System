package com.app.android.file_management.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSource {
    public DatabaseHelper helper ;
    public SQLiteDatabase database ;

    //---constructor ---//
    public DatabaseSource(Context context ) {
        helper = new DatabaseHelper(context) ;
    }

    //---database opening method---//
    public void open(){
        database = helper.getWritableDatabase();
    }

    //---database closing method---//
    public void close(){
        database.close();
    }

    //---update user name and password ---//
    public boolean updateLogin(String userName, String pass){
        this.open();
        ContentValues values = new ContentValues();
        values.put(helper.COL_USER_NAME,userName);
        values.put(helper.COL_PASS,pass);
        int updated = database.update(helper.TABLE_LOGIN,values,null,null);
        this.close();

        if(updated >0){
            return  true;
        }
        else {
            return false;
        }
    }

    //--- get user info data ---//
    public ArrayList<UserInfo> getUserInfo(){
        this.open();
        ArrayList<UserInfo> list  = new ArrayList<>();
        Cursor cursor = database.query(helper.TABLE_LOGIN,null,null,null,null,null,null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String userName = cursor.getString(cursor.getColumnIndex(helper.COL_USER_NAME));
                String pass = cursor.getString(cursor.getColumnIndex(helper.COL_PASS));

                UserInfo response = new UserInfo(userName,pass);
                list.add(response);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.close();

        return list;

    }


    //--- get user info data ---//
    public ArrayList<Category> getAllCategory(){
        this.open();
        ArrayList<Category> list  = new ArrayList<>();
        Cursor cursor = database.query(helper.TABLE_CATEGORY,null,null,null,null,null,null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(helper.COL_CAT_ID));
                String category = cursor.getString(cursor.getColumnIndex(helper.COL_CAT_NAME));

                Category response = new Category(id,category);
                list.add(response);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.close();

        return list;

    }


    public Boolean addCategory( Category category){
        this.open();
        ContentValues values = new ContentValues();
        values.put(helper.COL_CAT_NAME,category.getName());
        long inserted = database.insert(helper.TABLE_CATEGORY,null,values);
        this.close();

        if(inserted >0){
            return true;
        }
        else{
            return false;
        }

    }

    public Boolean deteteCategory( Category category){
        this.open();
        String name = category.getName();
        long inserted = database.delete(helper.TABLE_CATEGORY,helper.COL_CAT_NAME+"=?",new String[] { name });
        this.close();

        if(inserted >0){
            return true;
        }
        else{
            return false;
        }

    }


    public Boolean setTodoTask(TodoTask task){
        this.open();
        ContentValues values = new ContentValues();
        values.put(helper.COL_TITLE,task.getTitle());
        values.put(helper.COL_DESP,task.getDesp());
        values.put(helper.COL_TIME,task.getTime());
        values.put(helper.COL_DATE,task.getDate());
        values.put(helper.COL_CATEGORY,task.getCategory());
        values.put(helper.COL_IS_COMPLETE,task.getIsComplete());
        values.put(helper.COL_PATH,task.getPath());
        long inserted = database.insert(helper.TABLE_TASK,null,values);
        this.close();
        if(inserted >0){
            return true;
        }
        else{
            return false;
        }


    }


    public ArrayList<TodoTask> getTaskByCategory(String task_category){
        this.open();
        ArrayList<TodoTask> list  = new ArrayList<>();
        Cursor cursor = database.query(helper.TABLE_TASK,null,helper.COL_CATEGORY+"=?",new String[] { task_category },null,null,null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                String title = cursor.getString(cursor.getColumnIndex(helper.COL_TITLE));
                String desp = cursor.getString(cursor.getColumnIndex(helper.COL_DESP));
                String time = cursor.getString(cursor.getColumnIndex(helper.COL_TIME));
                String date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
                String category = cursor.getString(cursor.getColumnIndex(helper.COL_CATEGORY));
                int isComplete = cursor.getInt(cursor.getColumnIndex(helper.COL_IS_COMPLETE));
                String path = cursor.getString(cursor.getColumnIndex(helper.COL_PATH));

                TodoTask response = new TodoTask(id,title,desp,time,date,category,isComplete,path);
                list.add(response);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.close();

        return list;

    }

    public ArrayList<TodoTask> getTaskByTitle(String task_title){
        this.open();
        ArrayList<TodoTask> list  = new ArrayList<>();
        Cursor cursor = database.query(helper.TABLE_TASK,null,helper.COL_TITLE+"=?",new String[] { task_title },null,null,null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                String title = cursor.getString(cursor.getColumnIndex(helper.COL_TITLE));
                String desp = cursor.getString(cursor.getColumnIndex(helper.COL_DESP));
                String time = cursor.getString(cursor.getColumnIndex(helper.COL_TIME));
                String date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
                String category = cursor.getString(cursor.getColumnIndex(helper.COL_CATEGORY));
                int isComplete = cursor.getInt(cursor.getColumnIndex(helper.COL_IS_COMPLETE));
                String path = cursor.getString(cursor.getColumnIndex(helper.COL_PATH));

                TodoTask response = new TodoTask(id,title,desp,time,date,category,isComplete,path);
                list.add(response);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.close();

        return list;

    }

    public ArrayList<TodoTask> getTodayTask(String todaysDate){
        this.open();
        ArrayList<TodoTask> list  = new ArrayList<>();
        Cursor cursor = database.query(helper.TABLE_TASK,null,null,null,null,null,null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                String title = cursor.getString(cursor.getColumnIndex(helper.COL_TITLE));
                String desp = cursor.getString(cursor.getColumnIndex(helper.COL_DESP));
                String time = cursor.getString(cursor.getColumnIndex(helper.COL_TIME));
                String date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
                String category = cursor.getString(cursor.getColumnIndex(helper.COL_CATEGORY));
                int isComplete = cursor.getInt(cursor.getColumnIndex(helper.COL_IS_COMPLETE));
                String path = cursor.getString(cursor.getColumnIndex(helper.COL_PATH));

                if(date.equals(todaysDate)){

                    TodoTask response = new TodoTask(id,title,desp,time,date,category,isComplete,path);
                    list.add(response);

                }

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.close();

        return list;

    }

    public ArrayList<TodoTask> getPendingTask(){
        this.open();
        ArrayList<TodoTask> list  = new ArrayList<>();
        Cursor cursor = database.query(helper.TABLE_TASK,null,helper.COL_IS_COMPLETE+"=?",new String[] { "0" },null,null,null);
        Log.e("test",cursor.getCount()+"");
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                String title = cursor.getString(cursor.getColumnIndex(helper.COL_TITLE));
                String desp = cursor.getString(cursor.getColumnIndex(helper.COL_DESP));
                String time = cursor.getString(cursor.getColumnIndex(helper.COL_TIME));
                String date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
                String category = cursor.getString(cursor.getColumnIndex(helper.COL_CATEGORY));
                int isComplete = cursor.getInt(cursor.getColumnIndex(helper.COL_IS_COMPLETE));
                String path = cursor.getString(cursor.getColumnIndex(helper.COL_PATH));

                TodoTask response = new TodoTask(id,title,desp,time,date,category,isComplete,path);
                list.add(response);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.close();

        return list;

    }

    public ArrayList<TodoTask> getCompleteTask(){
        this.open();
        ArrayList<TodoTask> list  = new ArrayList<>();
        Cursor cursor = database.query(helper.TABLE_TASK,null,helper.COL_IS_COMPLETE+"=?",new String[] { "1" },null,null,null);

        Log.e("test",cursor.getCount()+"");
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                String title = cursor.getString(cursor.getColumnIndex(helper.COL_TITLE));
                String desp = cursor.getString(cursor.getColumnIndex(helper.COL_DESP));
                String time = cursor.getString(cursor.getColumnIndex(helper.COL_TIME));
                String date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
                String category = cursor.getString(cursor.getColumnIndex(helper.COL_CATEGORY));
                int isComplete = cursor.getInt(cursor.getColumnIndex(helper.COL_IS_COMPLETE));
                String path = cursor.getString(cursor.getColumnIndex(helper.COL_PATH));

                TodoTask response = new TodoTask(id,title,desp,time,date,category,isComplete,path);
                list.add(response);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.close();

        return list;

    }

    public ArrayList<TodoTask> getTaskById(int task_id){
        this.open();
        ArrayList<TodoTask> list  = new ArrayList<>();
        Cursor cursor = database.query(helper.TABLE_TASK,null,helper.COL_ID+"=?",new String[] { String.valueOf(task_id)  },null,null,null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                String title = cursor.getString(cursor.getColumnIndex(helper.COL_TITLE));
                String desp = cursor.getString(cursor.getColumnIndex(helper.COL_DESP));
                String time = cursor.getString(cursor.getColumnIndex(helper.COL_TIME));
                String date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
                String category = cursor.getString(cursor.getColumnIndex(helper.COL_CATEGORY));
                int isComplete = cursor.getInt(cursor.getColumnIndex(helper.COL_IS_COMPLETE));
                String path = cursor.getString(cursor.getColumnIndex(helper.COL_PATH));

                TodoTask response = new TodoTask(id,title,desp,time,date,category,isComplete,path);
                list.add(response);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.close();

        return list;

    }

    public Boolean setTodoTaskCompleteById(int task_id){
        this.open();
        ContentValues values = new ContentValues();
        values.put(helper.COL_IS_COMPLETE,1);
        int updated = database.update(helper.TABLE_TASK,values,helper.COL_ID+"=?",new String[]{String.valueOf(task_id)});
        this.close();
        if(updated >0){
            return true;
        }
        else{
            return false;
        }

    }

    public Boolean setTodoTaskPendingById(int task_id){
        this.open();
        ContentValues values = new ContentValues();
        values.put(helper.COL_IS_COMPLETE,0);
        int updated = database.update(helper.TABLE_TASK,values,helper.COL_ID+"=?",new String[]{String.valueOf(task_id)});
        this.close();
        if(updated >0){
            return true;
        }
        else{
            return false;
        }


    }
    //update from database....
    public Boolean updateTodoTask(TodoTask task , int id){
        this.open();
        ContentValues values = new ContentValues();
        values.put(helper.COL_TITLE,task.getTitle());
        values.put(helper.COL_DESP,task.getDesp());
        values.put(helper.COL_TIME,task.getTime());
        values.put(helper.COL_DATE,task.getDate());
        values.put(helper.COL_CATEGORY,task.getCategory());
        values.put(helper.COL_PATH,task.getPath());
        long inserted = database.update(helper.TABLE_TASK,values,helper.COL_ID+"=?",new String[]{String.valueOf(id)});
        this.close();

        if(inserted >0){
            return true;
        }
        else{
            return false;
        }


    }

    public Boolean deleteTodoTask( int id){
        this.open();
        ContentValues values = new ContentValues();
        long inserted= database.delete ("task",helper.COL_ID+"=?",new String[]{String.valueOf (id)});
        this.close ();

        if(inserted >0){
            return true;
        }
        else{
            return false;
        }

    }

    // Read records related to the search term
    public List<TodoTask> read(String searchTerm) {
        this.open();
        List<TodoTask> recordsList = new ArrayList<TodoTask>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + helper.TABLE_TASK;
        sql += " WHERE " + helper.COL_TITLE + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + helper.COL_ID + " DESC";
        sql += " LIMIT 0,5";

        // execute the query
        Cursor cursor = database.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                String title = cursor.getString(cursor.getColumnIndex(helper.COL_TITLE));
                String desp = cursor.getString(cursor.getColumnIndex(helper.COL_DESP));
                String time = cursor.getString(cursor.getColumnIndex(helper.COL_TIME));
                String date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
                String category = cursor.getString(cursor.getColumnIndex(helper.COL_CATEGORY));
                int isComplete = cursor.getInt(cursor.getColumnIndex(helper.COL_IS_COMPLETE));
                String path = cursor.getString(cursor.getColumnIndex(helper.COL_PATH));

                TodoTask response = new TodoTask(id,title,desp,time,date,category,isComplete,path);

                // add to list
                recordsList.add(response);

            } while (cursor.moveToNext());
        }

        cursor.close();
        this.close();

        // return the list of records
        return recordsList;
    }
}
