package com.example.assignment1_fit2081_32781555.Manager;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment1_fit2081_32781555.Adapter.EventAdapter;
import com.example.assignment1_fit2081_32781555.Utility.Utility;

public class EventManager extends Manager {
    EventAdapter eventAdapter = new EventAdapter();

    //Generate New EventID
    public String generateEventID() {

        return 'E'
                //Add Two random Characters
                + Utility.generateCharacters(2)
                //Add '-'
                + '-'
                //Random 4 numbers
                + Utility.generateNumbers(5);
    }

    public boolean validEntry(EditText categoryID,EditText name, EditText ticketsAvailable,Context context) {

        if (!validName(name)) {
            feedBack(context, "Invalid Event Name!");
            return false;
        }
        if (!validNumber(ticketsAvailable)){
            feedBack(context,"Invalid Tickets Available!");
            return false;
        }
        return true;
    }
    private void feedBack(Context context, String msg){Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();}
    public EventAdapter getAdapter(){
        return eventAdapter;
    }


}
