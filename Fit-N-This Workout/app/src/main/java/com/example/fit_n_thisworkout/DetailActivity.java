package com.example.fit_n_thisworkout;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity{
    private EditText weightEditText;
    private EditText repsEditText;
    private Button addSet;

    private String userID;
    private String ref;

    private Workout workout;
    private Set set;

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DetailListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        weightEditText = findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(new OnTextChangedListener(weightEditText));
        repsEditText = findViewById(R.id.repsEditText);
        repsEditText.addTextChangedListener(new OnTextChangedListener(repsEditText));

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userID = getIntent().getStringExtra("uid");
        ref = getIntent().getStringExtra("ref");

        //setAdapter();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Workout").child(ref);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                workout = dataSnapshot.getValue(Workout.class);
                //Toast.makeText(getApplicationContext(), "workout_name:" + workout.getWorkoutName(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "workout_date:" + workout.getDate(), Toast.LENGTH_SHORT).show();

                //setUi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);

        //ArrayList<Exercise> exercises = workout.getExercises();
        //for (Exercise e : exercises) {
        //    if (e.getExerciseName() == )
        //}


        set = new Set();

        addSet = findViewById(R.id.addSet_button);
        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //workout.addExercise(exercise);
                databaseReference.setValue(workout);
                set.setRepetitions(0);
                set.setWeight(0.0);
                //finish();
            }
        });
    }

    private void setAdapter() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = firebaseDatabase.getReference().child("Workout").child(ref).child("exercises").child("workoutName").child("exerciseSets");
        FirebaseRecyclerOptions<Set> options = new FirebaseRecyclerOptions.Builder<Set>()
                .setQuery(query, Set.class).build();
        adapter = new DetailListAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    private class OnTextChangedListener implements TextWatcher {
        private View editText;

        public OnTextChangedListener(View view) {
            editText = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(editText == weightEditText){
                set.setWeight(Double.parseDouble(s.toString()));
            } else if(editText == repsEditText){
                set.setRepetitions(Integer.parseInt(s.toString()));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    /*
    // creates the menu button and uses the options_menu xml file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater.inflate(R.)
        return super.onCreateOptionsMenu(menu);

    }
    */
}
