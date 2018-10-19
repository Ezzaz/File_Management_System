package com.app.android.file_management.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.android.file_management.R;
import com.app.android.file_management.database.DatabaseSource;
import com.app.android.file_management.database.TodoTask;
import com.app.android.file_management.fragment.MainFragment;
import com.app.android.file_management.fragment.SettingsFragment;
import com.app.android.file_management.fragment.TaskFragment;
import com.app.android.file_management.search.CustomAutoCompleteTextChangedListener;
import com.app.android.file_management.search.CustomAutoCompleteView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    /*
     * Change to type CustomAutoCompleteView instead of AutoCompleteTextView
     * since we are extending to customize the view and disable filter
     * The same with the XML view, type will be CustomAutoCompleteView
     */
    public static CustomAutoCompleteView myAutoComplete;
    public ImageButton search_btn;

    // adapter for auto-complete
    public static ArrayAdapter<String> myAdapter;


    // just to add some initial value
    public static String[] item = new String[] {"Please search..."};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        search_btn = findViewById(R.id.search_btn);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String search = myAutoComplete.getText().toString();
               DatabaseSource source  = new DatabaseSource(MainActivity.this);
                ArrayList<TodoTask> tasks = source.getTaskByTitle(search);
               if(tasks.size()!=0){

                   TaskFragment fragment = new TaskFragment();
                   fragment.Constructor("search");
                   fragment.searchVaule(tasks);
                   FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                   fragmentTransaction.replace(R.id.mainFragmentContainer,fragment);
                   fragmentTransaction.commit();

               }
               else {
                   Toast.makeText(MainActivity.this, "No file found", Toast.LENGTH_SHORT).show();
               }
            }
        });

        addAutoCompleteSearch();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MainFragment fragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFragmentContainer,fragment);
        fragmentTransaction.commit();

    }



    public void addAutoCompleteSearch(){
        try{

            // autocompletetextview is in activity_main.xml
            myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.searchautocomplete);

            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));

            // set our adapter
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            myAutoComplete.setAdapter(myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // this function is used in CustomAutoCompleteTextChangedListener.java
    public String[] getItemsFromDb(String searchTerm){

        DatabaseSource source = new DatabaseSource(this);

        // add items on the array dynamically
        List<TodoTask> products = source.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (TodoTask record : products) {
            item[x] = record.getTitle();
            x++;
        }

        return item;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to exit ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_todo) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this,AddTodoActivity.class));
            finish();
        }
        else if(id == R.id.nav_all){
            MainFragment fragment = new MainFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFragmentContainer,fragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_settings) {
            SettingsFragment fragment = new SettingsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFragmentContainer,fragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_share) {
            //shareApplication();
            Intent intent = new Intent (android.content.Intent.ACTION_SEND);
            intent.setData (Uri.parse ("mailto:"));
            intent.setType ("message/rfc822");
            intent.putExtra (Intent.EXTRA_SUBJECT, "File Management");
            Intent chooser = Intent.createChooser (intent, "Share using");
            startActivity (chooser);
        }

        else if (id == R.id.nav_help) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Help");
            alertDialog.setMessage("File Management is an intelligent and smart app that makes it easy to use. " +
                    "Whether it’s for work, school or home.It will help you to increase your productivity and decrease your stress levels. " +
                    "It combines beautiful design to empower you to create a simple daily workflow.\n\n@File Management \nVersion: 3.1.4");
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
