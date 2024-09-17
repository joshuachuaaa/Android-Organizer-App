package com.example.assignment1_fit2081_32781555.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment1_fit2081_32781555.Adapter.CategoryAdapter;
import com.example.assignment1_fit2081_32781555.Manager.CategoryManager;
import com.example.assignment1_fit2081_32781555.Objects.Category;
import com.example.assignment1_fit2081_32781555.Provider.ViewModel.CategoryViewModel;
import com.example.assignment1_fit2081_32781555.R;
import com.example.assignment1_fit2081_32781555.SMS.SMSFormat;
import com.example.assignment1_fit2081_32781555.SMS.SMSReceiver;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryFormActivity extends AppCompatActivity {

    TextView tvCategoryID;
    EditText etCategoryName;
    EditText etEventCount;

    EditText etEventLocation;
    Switch btnIsActive;

    CategoryManager categoryManager;
    CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category_form_page);
        setUpCategoryManager();
        setUpToolBar();
        setUpUI();
        setUpViewModel();
        setUpBroadcastReceiver();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void setUpViewModel(){
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, newData -> {
            // cast List<Category> to ArrayList<Category>
            CategoryAdapter categoryAdapter = categoryManager.getAdapter();
            categoryAdapter.setData(new ArrayList<Category>(newData));
            categoryAdapter.notifyDataSetChanged();

        });
    }

    private void setUpBroadcastReceiver(){
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);
    }
    private void setUpCategoryManager(){categoryManager = new CategoryManager();}
    private void setUpToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Category Form");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }
    private void setUpUI(){

        //Get the Textview to display the generated CategoryID
        tvCategoryID = findViewById(R.id.tvCategoryID);
        //Get References to input fields
        etCategoryName = findViewById(R.id.etCategoryName);
        etEventCount = findViewById(R.id.etEventCount);
        btnIsActive = findViewById(R.id.btnIsActive);
        etEventLocation = findViewById(R.id.etEventLocation);
    }
    public void createCategory(View view) {

        if (categoryManager.validEntry(etCategoryName,etEventCount,this)){

            tvCategoryID.setText(categoryManager.generateID());
            categoryViewModel.insert(
                    new Category(
                            tvCategoryID.getText().toString(),
                            etCategoryName.getText().toString(),
                            Integer.parseInt(etEventCount.getText().toString()),
                            btnIsActive.isChecked(),
                            etEventLocation.getText().toString()));


            feedBack("Category Saved!");
            finish();
        }
        else{
            snackBar("Invalid Input");
        }
    }

    //SnackBar
    public void snackBar(String msg) {
        Snackbar.make(findViewById(R.id.rootLayout), msg, Snackbar.LENGTH_LONG)
                .show();
    }
    private void feedBack(String msg){Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Finish this activity and return to the parent activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class MyBroadCastReceiver extends BroadcastReceiver {
        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.CATEGORY_KEY);
            //If message is empty
            if (msg == null){snackBar("Error");return;}

            //Split the data into array
            String[] st = msg.split(";",-1);
            for(String s : st){
                if (s.equals("")){snackBar("Error!");return;}
            }

            //If number of data points matches
            if (st.length != SMSFormat.CATEGORY_AMT_TOKENS){snackBar("Error!");}
            else{
                try{
                    //Get the three data points
                    String str_catName = st[SMSFormat.CAT_NAME_INDEX];
                    String str_eventCount = st[SMSFormat.CAT_EVENT_COUNT_INDEX];
                    String str_isActive = st[SMSFormat.CAT_IS_ACTIVE_INDEX];

                    //Parse eventCount to integer to check for any formatting issues
                    Integer.parseInt(str_eventCount);
                    String lowerCase_isActive = str_isActive.toLowerCase();
                    if(SMSFormat.invalidTrueFalse(lowerCase_isActive)){SMSFormat.errorMsg(context);return;}

                    //update UI
                    SMSFormat.updateUI(etCategoryName,str_catName);
                    SMSFormat.updateUI(etEventCount,str_eventCount);
                    SMSFormat.updateUIButton(context,btnIsActive,lowerCase_isActive);

                }
                catch(Exception e){SMSFormat.errorMsg(context);}
            }
        }
    }


}



