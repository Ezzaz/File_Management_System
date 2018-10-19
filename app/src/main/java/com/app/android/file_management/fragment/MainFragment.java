package com.app.android.file_management.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.file_management.adapter.CategoryAdapter;
import com.app.android.file_management.R;
import com.app.android.file_management.database.Category;
import com.app.android.file_management.database.DatabaseSource;

import java.util.List;

public class MainFragment extends Fragment {



    private RecyclerView dataView;
    private List<Category> categoryList;
    private CategoryAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        DatabaseSource source = new DatabaseSource(getContext());
        categoryList = source.getAllCategory();

        dataView = view.findViewById(R.id.categoryRV);

        adapter = new CategoryAdapter(getActivity(),categoryList);
        GridLayoutManager llm = new GridLayoutManager(getContext(),2);
        llm.setOrientation(GridLayoutManager.VERTICAL);
        dataView.setLayoutManager(llm);
        dataView.setAdapter(adapter);

        return view;
    }

}
