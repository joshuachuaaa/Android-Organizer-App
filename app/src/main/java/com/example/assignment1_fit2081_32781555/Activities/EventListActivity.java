package com.example.assignment1_fit2081_32781555.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.assignment1_fit2081_32781555.Fragment.FragmentListCategory;
import com.example.assignment1_fit2081_32781555.Fragment.FragmentListEvent;
import com.example.assignment1_fit2081_32781555.Manager.CategoryManager;
import com.example.assignment1_fit2081_32781555.Manager.EventManager;
import com.example.assignment1_fit2081_32781555.Provider.ViewModel.EventViewModel;
import com.example.assignment1_fit2081_32781555.R;

import java.util.Objects;

public class EventListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setUpToolBar();

        FragmentListEvent fragmentRecyclerEvent = new FragmentListEvent(new EventManager());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_recycler_comp, fragmentRecyclerEvent)
                .commit();
    }

    private void setUpToolBar(){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setTitle("Event List");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Finish this activity and return to the parent activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}