package com.example.assignment1_fit2081_32781555.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment1_fit2081_32781555.Manager.CategoryManager;
import com.example.assignment1_fit2081_32781555.R;

public class FragmentListCategory extends Fragment {
    private RecyclerView recyclerViewCategory;
    private final CategoryManager categoryManager;

    public FragmentListCategory(CategoryManager categoryManager){this.categoryManager = categoryManager;}
    public CategoryManager getCategoryManager(){
        return categoryManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerViewCategory = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewCategory.setAdapter(categoryManager.getAdapter());
    }

}
