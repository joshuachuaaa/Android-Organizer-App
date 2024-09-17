package com.example.assignment1_fit2081_32781555.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment1_fit2081_32781555.Adapter.CategoryAdapter;
import com.example.assignment1_fit2081_32781555.Adapter.EventAdapter;
import com.example.assignment1_fit2081_32781555.Fragment.FragmentListCategory;
import com.example.assignment1_fit2081_32781555.Manager.CategoryManager;
import com.example.assignment1_fit2081_32781555.Objects.Category;
import com.example.assignment1_fit2081_32781555.Objects.Event;
import com.example.assignment1_fit2081_32781555.Manager.EventManager;
import com.example.assignment1_fit2081_32781555.Provider.ViewModel.CategoryViewModel;
import com.example.assignment1_fit2081_32781555.Provider.ViewModel.EventViewModel;
import com.example.assignment1_fit2081_32781555.R;
import com.example.assignment1_fit2081_32781555.SMS.SMSFormat;
import com.example.assignment1_fit2081_32781555.SMS.SMSReceiver;
import com.example.assignment1_fit2081_32781555.Utility.EventAddCallback;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;


public class DashboardActivity extends AppCompatActivity implements EventAddCallback {

    TextView tvEventID;
    EditText etEventName;
    EditText etCategoryID;
    EditText etTicketsAvail;
    Switch btnIsActive;
    EventManager eventManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentListCategory categoryFragment;
    CategoryViewModel categoryViewModel;
    EventViewModel eventViewModel;
    View touchpad;

    private GestureDetectorCompat mDetector;

    Toolbar myToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Toolbar
        setContentView(R.layout.activity_dashboard_drawer_layout);
        setUpToolBar();
        setUpEventManager();
        setUpUI();
        setUpNavigationDrawer();
        setUpBroadcastReceiver();
        setUpFragments();
        setUpViewModels();
        setUpTouchPad();
    }

    private void setUpTouchPad() {
        touchpad = findViewById(R.id.touchPad);
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mDetector = new GestureDetectorCompat(this, customGestureDetector);

        touchpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mDetector.onTouchEvent(event);
                return true;
            }
        });
    }
    private void setUpEventManager() {eventManager = new EventManager();}
    @SuppressLint("NotifyDataSetChanged")
    private void setUpViewModels(){
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, newData -> {
            // cast List<Category> to ArrayList<Category>
            CategoryAdapter categoryAdapter = categoryFragment.getCategoryManager().getAdapter();
            categoryAdapter.setData(new ArrayList<Category>(newData));
            categoryAdapter.notifyDataSetChanged();

        });
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(this, newData -> {
            // cast List<Event> to ArrayList<Event>
            EventAdapter eventAdapter = eventManager.getAdapter();
            eventAdapter.setData(new ArrayList<Event>(newData));
            eventAdapter.notifyDataSetChanged();
        });
    }
    private void setUpNavigationDrawer(){
        //Drawer Layout
        drawerLayout = findViewById(R.id.drawer_layout);

        //Navigation View
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, myToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());
    }
    private void setUpToolBar(){
        myToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolBar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
    }
    private void setUpUI(){
        //Assign references to their respective variables
        this.tvEventID = findViewById(R.id.tvCategoryID);
        this.etEventName = findViewById(R.id.etEventName);
        this.etCategoryID = findViewById(R.id.etCategoryID);
        this.etTicketsAvail = findViewById(R.id.etTicketsAvailable);
        this.btnIsActive = findViewById(R.id.btnIsActive_Form);
    }
    private void setUpBroadcastReceiver(){
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);
    }
    private void setUpFragments(){
        categoryFragment = new FragmentListCategory(new CategoryManager());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, categoryFragment)
                .commit();
    }

    //Saving Event Functionality
    public void saveEvent(View view){registerEvent();}

    public void registerEvent(){

        if (eventManager.validEntry(etCategoryID,etEventName,etTicketsAvail,this)) {

            String eventID = eventManager.generateEventID();

            Event newEvent = new Event(
                    eventID,
                    etCategoryID.getText().toString(),
                    etEventName.getText().toString(),
                    Integer.parseInt(etTicketsAvail.getText().toString()),
                    btnIsActive.isActivated()
            );

            eventViewModel.insert(newEvent,this);

        }
        else{
            snackBar("Invalid Input");
        }
    }

    //Reset Text Fields
    public void resetTextFields(){
        tvEventID.setText("");
        etEventName.setText("");
        etCategoryID.setText("");
        etTicketsAvail.setText("");
        btnIsActive.setChecked(false);
    }

    //App bar Functionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if (item.getItemId() == R.id.menu_clear_event_form)
        {
            resetTextFields();
        }
        else if (item.getItemId() == R.id.menu_del_all_cat)
        {
            categoryViewModel.deleteAllCategories();
        }
        else if (item.getItemId() == R.id.menu_del_all_event)
        {
            eventViewModel.deleteAllEvents();
        }
        return true;
    }

    //Navigation View Functionality
    private void toCategoryForm(){ startActivity(new Intent(this, CategoryFormActivity.class)); }
    private void toCategoryView(){ startActivity(new Intent(this,CategoryListActivity.class)); }
    private void toEventView(){ startActivity(new Intent(this,EventListActivity.class));}
    private void toSignIn() {startActivity(new Intent(this, LoginActivity.class ));finish();}
    //SnackBar
    public void snackBar(String msg){Snackbar.make(drawerLayout, msg, Snackbar.LENGTH_LONG).show();}

    @Override
    public void onSuccess(String eventID, Event newEvent) {
        runOnUiThread(() -> {
            tvEventID.setText(eventID); // Make sure eventID is accessible here, might need adjustment
            // Snackbar with undo option
            Snackbar.make(findViewById(R.id.drawer_layout), "Event created successfully!", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            eventViewModel.deleteEvent(newEvent);
                            tvEventID.setText("");
                        }
                    }).show();
        });
    }
    @Override
    public void onError(String message) {
        runOnUiThread(() -> {
            snackBar("Category Does Not Exist!");
        });
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.EVENT_KEY);
            //If message is empty
            if (msg == null){return;}

            //Split the data into array
            String[] st = msg.split(";",-1);

            for(String s : st){
                if (s.equals("")){errorMsg(context);return;}
            }
            //If number of data points matches
            if (st.length != SMSFormat.EVENT_AMT_TOKENS){errorMsg(context);}
            else{
                try{
                    //Get the three data points
                    String str_eventName = st[SMSFormat.EVENT_NAME_INDEX];
                    String str_eventCatID = st[SMSFormat.EVENT_CAT_ID_INDEX];
                    String str_eventTickets = st[SMSFormat.EVENT_TICKETS_INDEX];
                    String str_isActive = st[SMSFormat.EVENT_IS_ACTIVE_INDEX];

                    //Parse eventCount to integer to check for any formatting issues
                    int int_EventCount = Integer.parseInt(str_eventTickets);
                    String lowerCase_isActive = str_isActive.toLowerCase();

                    if(SMSFormat.invalidTrueFalse(lowerCase_isActive)){return;}


                    //update UI
                    SMSFormat.updateUI(etEventName,str_eventName);
                    SMSFormat.updateUI(etCategoryID,str_eventCatID);
                    SMSFormat.updateUI(etTicketsAvail,str_eventTickets);
                    SMSFormat.updateUIButton(context,btnIsActive,lowerCase_isActive);

                }
                catch(Exception e){SMSFormat.errorMsg(context);}
            }
        }

        private void errorMsg(Context context){
            Toast.makeText(context,"Invalid message error!",Toast.LENGTH_LONG).show();
        }
    }
    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.item_view_all_category) {toCategoryView();}
            else if (id == R.id.item_add_category) {toCategoryForm();}
            else if (id == R.id.item_view_all_events) {toEventView();}
            else if (id == R.id.item_logout) {toSignIn();}

            drawerLayout.closeDrawers();
            return true;
        }
    }

    public class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener{

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            resetTextFields();
            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            registerEvent();
            return super.onDoubleTap(e);
        }

    }



}