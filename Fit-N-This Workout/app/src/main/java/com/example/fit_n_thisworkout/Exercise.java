package com.example.fit_n_thisworkout;

import java.util.ArrayList;

public class Exercise {

    private String exerciseName;
    private ArrayList<Set> exerciseSets = new ArrayList<>();

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public ArrayList<Set> getExerciseSets() {
        return exerciseSets;
    }

    public void addExerciseSets(Set s) {
        exerciseSets.add(s);
    }

    public Exercise() {
        //exerciseSets = new ArrayList<>();
    }


}
