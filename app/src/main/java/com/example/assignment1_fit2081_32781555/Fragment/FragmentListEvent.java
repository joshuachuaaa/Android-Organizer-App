package com.example.assignment1_fit2081_32781555.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment1_fit2081_32781555.Manager.EventManager;
import com.example.assignment1_fit2081_32781555.R;

public class FragmentListEvent extends Fragment {
    private RecyclerView recyclerViewEvent;
    private final EventManager eventManager;

    public FragmentListEvent(EventManager eventManager){
        this.eventManager = eventManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerViewEvent = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewEvent.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewEvent.setAdapter(eventManager.getAdapter());
    }
}
