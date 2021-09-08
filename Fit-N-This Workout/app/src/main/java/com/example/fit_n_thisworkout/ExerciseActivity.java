package com.example.fit_n_thisworkout;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Arrays;

public class ExerciseActivity extends AppCompatActivity implements ExerciseListAdapter.ExerciseListAdapterOnClickHandler{
    // If time permits, include inside of the row of Exercise, the number of sets done
    private TextView exerciseName;
    private RecyclerView recyclerView;

    private String userID;
    private String ref;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ExerciseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userID = getIntent().getStringExtra("uid");
        ref = getIntent().getStringExtra("ref");

        setAdapter();

        Toast.makeText(getApplicationContext(), "UserID:" + userID, Toast.LENGTH_SHORT).show();


        FloatingActionButton exerciseFab = findViewById(R.id.exercise_fab);
        exerciseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseActivity.this, CreateExercise.class);
                //intent.putExtra("workout", workout);
                intent.putExtra("uid", userID);
                intent.putExtra("ref", ref);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }

    private void setAdapter() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = firebaseDatabase.getReference().child("Workout").child(ref).child("exercises");
        FirebaseRecyclerOptions<Exercise> options = new FirebaseRecyclerOptions.Builder<Exercise>()
                .setQuery(query, Exercise.class).build();
        adapter = new ExerciseListAdapter(options, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        Intent exerciseIntent = new Intent(this, DetailActivity.class);
        exerciseIntent.putExtra("uid", userID);
        DatabaseReference ref = adapter.getRef(position);
        String id = ref.getKey();
        exerciseIntent.putExtra("ref", id);
        Toast.makeText(getApplicationContext(), "ExerciseReference:" + id, Toast.LENGTH_SHORT).show();

        //exerciseIntent.putExtra("workout", workout);
        startActivity(exerciseIntent);
    }
}
