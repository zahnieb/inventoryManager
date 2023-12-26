package com.Observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public boolean isObserver(Observer observer) {
        return observers.contains(observer);
    }
    public void notifyObservers(Enums.EventType eventType, String event){
        //System.out.println(eventType + ": " + event);
        if(observers!=null) {
            for (Observer observer : observers) {
                observer.update(eventType, event);
            }
        }
    }
}
