package com.example.fit_n_thisworkout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ExerciseListAdapter extends FirebaseRecyclerAdapter<Exercise, ExerciseListAdapter.ExerciseHolder> {
    @Override
    protected void onBindViewHolder(@NonNull ExerciseHolder holder, int position, @NonNull Exercise model) {
        holder.exerciseNameTextView.setText(model.getExerciseName());
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exercise_list, viewGroup, false);
        return new ExerciseListAdapter.ExerciseHolder(view);
    }

    /*@Override
    protected void onBindViewHolder(@NonNull ExerciseHolder holder, int position, @NonNull Workout model) {
        ArrayList<Exercise> exercises = model.getExercises();
        for (Exercise e : exercises){
            holder.exerciseNameEditText.setText(e.getExerciseName());
        }

    }*/

    class ExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView exerciseNameTextView;

        public ExerciseHolder (View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            clickHandler.onClick(adapterPosition);
        }
    }

    private final ExerciseListAdapterOnClickHandler clickHandler;

    public interface ExerciseListAdapterOnClickHandler {
        void onClick(int position);
    }

    public ExerciseListAdapter(@NonNull FirebaseRecyclerOptions<Exercise> options, ExerciseListAdapterOnClickHandler clickHandler) {
        super(options);
        this.clickHandler = clickHandler;
    }
}
