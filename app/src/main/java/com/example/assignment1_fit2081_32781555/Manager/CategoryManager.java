package com.example.assignment1_fit2081_32781555.Manager;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment1_fit2081_32781555.Adapter.CategoryAdapter;
import com.example.assignment1_fit2081_32781555.Utility.Utility;

public class CategoryManager extends Manager {

    private final CategoryAdapter categoryAdapter = new CategoryAdapter();

    @Override
    public String generateID(){

        return 'C'
                //Add Two random Characters
                + Utility.generateCharacters(2)
                //Add '-'
                +'-'
                //Random 4 numbers
                +Utility.generateNumbers(4);
    }

    public boolean validEntry(EditText name, EditText count,Context context){
        if(!validName(name)){
            feedBack(context,"Invalid Category Name!");
            return false;
        }
        else if(!validNumber(count)){
            feedBack(context,"Invalid Event Count!");
            return false;
        }
        return true;
    }

    private void feedBack(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public CategoryAdapter getAdapter(){return categoryAdapter;}
}
