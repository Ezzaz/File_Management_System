package com.app.android.file_management.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.android.file_management.R;
import com.app.android.file_management.database.Category;
import com.app.android.file_management.database.DatabaseSource;
import com.app.android.file_management.database.UserInfo;

import java.util.ArrayList;
import java.util.List;


public class SettingsFragment extends Fragment {


    //-- Layout Component From XML --//
    private EditText newCategoryET;
    private Button addCategory_btn;
    private Button deleteCategory_btn;
    private Spinner categorySP;

    //--Database Source files --//
    private DatabaseSource source;
    public static ArrayList<UserInfo> userInfoList = new ArrayList<>();

    //-- category spiner text--//
    private String categoryName;

    //--Fragment constructor --//
    public SettingsFragment() {
        //-- Required empty public constructor --//
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //-- Inflate the layout for this fragment --//
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //-- Find Views by id from layout xml --//
        newCategoryET = view.findViewById(R.id.settings_newCategory);
        addCategory_btn = view.findViewById(R.id.settings_addCategory);
        categorySP = view.findViewById(R.id.settings_categotySpiner);
        deleteCategory_btn = view.findViewById(R.id.settings_deleteCategory);

        //-- Define database source --//
        source = new DatabaseSource(getContext());

        //-- Category Spiner adapter--//
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,getCategoryList());
        categorySP.setAdapter(adapter);

        //-- Set Category Spiner Data --//
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

        //-- Create new category  when click add button --//
        addCategory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //-- call add category method--//
                addCategory();
            }
        });

        //-- delete selected category  when click delete button --//
        deleteCategory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //-- call delete category method--/
                deleteCategory();
            }
        });

        return view;
    }

    public  ArrayList<String> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        categoryList = source.getAllCategory();
        int size = categoryList.size();

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Select Category");
        for (int i=0;i<size;i++){
            categories.add(categoryList.get(i).getName().toString());
        }
        return categories;
    }

    private void addCategory(){
        //-- Get values form editText --//
        String categoryname = newCategoryET.getText().toString();

        //--Check if the category name is empty or not . if empty then show error message --//
        if (TextUtils.isEmpty(categoryname)) {
            newCategoryET.setError("Category name cannot be empty");
        }
        else{
            Category category = new Category(0,categoryname);
            boolean isAdded = source.addCategory(category);
            newCategoryET.setText("");
            //--check is added successfully --//
            if (isAdded){
                //-- Refresh fragment --//
                refresh();
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setMessage("Successfully "+categoryname+" added");
                alertDialog.show();
            }
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setMessage("Please try again.");
                alertDialog.show();
            }

        }

    };

    private void deleteCategory(){
        //-- check if any category selected or not --//
        if(categoryName.equals("Select Category")){
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setMessage("Select any category");
            alertDialog.show();
        }
        else{
            Category category = new Category();
            category.setName(categoryName);
            boolean isDeleted = source.deteteCategory(category);

            //--check is deleted successfully --//
            if (isDeleted){
                //-- Refresh fragment --//
                refresh();
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setMessage("Successfully "+categoryName+" deleted");
                alertDialog.show();
            }
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setMessage("Please try again.");
                alertDialog.show();
            }

        }

    }

    private void refresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
}



