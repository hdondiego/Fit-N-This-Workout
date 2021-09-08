package com.example.fit_n_thisworkout;

import java.util.ArrayList;
import java.util.Calendar;


public class Workout {
    private String uid;
    private String workoutName;
    private String date;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void addExercise (Exercise e) {
        exercises.add(e);
    }

    public Workout(String uid) {
        setUid(uid);
    }

    public Workout() {}

    /*
    public Workout(String workoutName, String date) {
        setWorkoutName(workoutName);
        setDate(date);
        ArrayList<Exercise> exercises = new ArrayList<>();
        setExercises(exercises);
    }
    */
}