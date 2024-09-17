package com.example.assignment1_fit2081_32781555.Utility;

import com.example.assignment1_fit2081_32781555.Objects.Event;

public interface EventAddCallback {
    void onSuccess(String eventID, Event newEvent);
    void onError(String message);
}