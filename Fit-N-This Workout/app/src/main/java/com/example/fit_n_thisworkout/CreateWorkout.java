package com.example.fit_n_thisworkout;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class CreateWorkout extends AppCompatActivity {
    private final String TAG = CreateWorkout.class.getSimpleName();
    private EditText workoutNameEditText;
    private Button createWorkout;
    private DatePicker datePicker;

    private Workout workout;
    //private String workoutName;
    private int month;
    private int day;
    private int year;
    private String date;

    private Calendar defaultDate;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String userID;
    private String ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Workout");

        workoutNameEditText = findViewById(R.id.workoutNameEditText);
        workoutNameEditText.addTextChangedListener(new WorkoutNameChangedListener(workoutNameEditText));

        createWorkout = findViewById(R.id.createWorkout);
        datePicker = findViewById(R.id.datePicker);

        // automatically setting the DatePicker's default date to the current date
        defaultDate = Calendar.getInstance();
        year = defaultDate.get(Calendar.YEAR);
        month = defaultDate.get(Calendar.MONTH);
        day = defaultDate.get(Calendar.DAY_OF_MONTH);

        datePicker.init(year, month, day, null);

        userID = getIntent().getStringExtra("uid");
        workout = new Workout(userID);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = getIntent().getStringExtra("ref");

        createWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!workout.getWorkoutName().equals("")){
                    String m = Integer.toString(datePicker.getMonth());
                    String d = Integer.toString(datePicker.getDayOfMonth());
                    String y = Integer.toString(datePicker.getYear());
                    date = m + '/' + d + '/' + y;
                    workout.setDate(date);

                    String wName = workout.getWorkoutName();
                    String wDate = workout.getDate();

                    databaseReference.push().setValue(workout);

                    Intent returnWorkout = new Intent();
                    returnWorkout.putExtra("workoutName", wName);
                    returnWorkout.putExtra("workoutDate", wDate);
                    setResult(RESULT_OK, returnWorkout);

                    finish();
                } else {
                    String toastString = "Please provide a Workout Name";
                    Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
    // This is also for updating a Workout (if time permits)
    private void setUi() {
        if (workout != null) {
            workoutNameEditText.setText(workout.getWorkoutName());
            String dateRetrieved = workout.getDate();

        }
    }
    */

    private class WorkoutNameChangedListener implements TextWatcher {
        private View editText;

        public WorkoutNameChangedListener(View view) {
            editText = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            workout.setWorkoutName(s.toString());
            Log.d(TAG, "workout_name: " + s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
