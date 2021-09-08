package com.example.fit_n_thisworkout;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements WorkoutListAdapter.WorkoutListAdapterOnClickHandler{
    private static final int RATING_INTENT_RESULT = 111;
    private WorkoutListAdapter adapter;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String userID;
    private String ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            userID = user.getUid();
            Toast.makeText(getApplicationContext(), "UserID:" + userID, Toast.LENGTH_SHORT).show();
        }

        setAdapter();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userID = user.getUid();
                } else {
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.GoogleBuilder().build())).build(), RATING_INTENT_RESULT);
                }
            }
        };

        FloatingActionButton workoutFab = findViewById(R.id.workout_fab);
        workoutFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateWorkout.class);
                intent.putExtra("uid", userID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(int position) {
        Intent exerciseIntent = new Intent(this, ExerciseActivity.class);
        exerciseIntent.putExtra("uid", userID);
        DatabaseReference ref = adapter.getRef(position);
        String id = ref.getKey();
        exerciseIntent.putExtra("ref", id);
        //exerciseIntent.putExtra("workout", workout);
        startActivity(exerciseIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Settings
        firebaseAuth.addAuthStateListener(authStateListener);
        adapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RATING_INTENT_RESULT) {
            if (resultCode == RESULT_OK) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userID = user.getUid();
                }
                setAdapter();
            }
            if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    private void setAdapter() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = firebaseDatabase.getReference().child("Workout").orderByChild("uid").equalTo(userID);
        //.orderByChild();
        FirebaseRecyclerOptions<Workout> options = new FirebaseRecyclerOptions.Builder<Workout>()
                .setQuery(query, Workout.class).build();
        adapter = new WorkoutListAdapter(options, this);
        recyclerView.setAdapter(adapter);
    }
}

