package com.example.fit_n_thisworkout;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateExercise extends AppCompatActivity {
    private final String TAG = CreateExercise.class.getSimpleName();
    private EditText exerciseNameEditText;
    private Button addExercise;

    private Workout workout;
    private Exercise exercise;

    private String exerciseName;

    private String ref;
    private String userID;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);

        //workout = getIntent().getStringExtra("workout");
        userID = getIntent().getStringExtra("uid");
        ref = getIntent().getStringExtra("ref");

        Toast.makeText(getApplicationContext(), "UserID:" + userID, Toast.LENGTH_SHORT).show();


        //workout = new Workout(userID);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Workout").child(ref);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                workout = dataSnapshot.getValue(Workout.class);
                Toast.makeText(getApplicationContext(), "workout_name:" + workout.getWorkoutName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "workout_date:" + workout.getDate(), Toast.LENGTH_SHORT).show();

                //setUi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);

        exerciseNameEditText = findViewById(R.id.exerciseNameEditText);
        exerciseNameEditText.addTextChangedListener(new ExerciseNameTextChanged(exerciseNameEditText));

        exercise = new Exercise();

        addExercise = findViewById(R.id.addExercise);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workout.addExercise(exercise);
                databaseReference.setValue(workout);
                finish();
            }
        });
    }

    private class ExerciseNameTextChanged implements TextWatcher {
        private View editText;

        public ExerciseNameTextChanged(View view) {
            editText = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            exercise.setExerciseName(s.toString());
            Log.d(TAG, "workout_name: " + s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
