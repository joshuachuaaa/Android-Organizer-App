package com.example.assignment1_fit2081_32781555.Manager;

import android.content.Context;
import android.widget.EditText;

public abstract class Manager {

    public String generateID(){
        return "default";}

    //Valid Entry Checks
    public boolean validName(EditText name) {
        return name.getText().toString().
                matches(
                        "^(?=.*[a-zA-Z])[a-zA-Z0-9 ]+$")
                &&
                !nameEmpty(name);
    }
    private boolean nameEmpty(EditText name){return name.getText().toString().isEmpty();}
    public boolean numberEmpty(EditText numberField){return numberField.getText().toString().isEmpty();}
    public boolean validNumber(EditText numberField) {

        if (numberEmpty(numberField)) {
            numberField.setText("0");
            return true;
        }
        try {
            int number = Integer.parseInt
                    (numberField.getText().toString());

            if (number < 0) {
                numberField.setText("0");
            }
            return true;
        } catch (NumberFormatException e) {
            numberField.setText("0");
            return true;
        }
    }
}
