package com.example.fit_n_thisworkout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class WorkoutListAdapter extends FirebaseRecyclerAdapter<Workout, WorkoutListAdapter.WorkoutHolder> {
    @Override
    protected void onBindViewHolder(@NonNull WorkoutHolder holder, int position, @NonNull Workout model) {
        holder.workoutNameTextView.setText(model.getWorkoutName());
        holder.dateTextView.setText(model.getDate());
    }

    @NonNull
    @Override
    public WorkoutHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.workout_list, viewGroup, false);
        return new WorkoutListAdapter.WorkoutHolder(view);
    }

    class WorkoutHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView workoutNameTextView;
        private final TextView dateTextView;
        //private final LinearLayout workoutListLayout;

        WorkoutHolder(View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.workoutNameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            //workoutListLayout = itemView.findViewById(R.id.workout_list_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            clickHandler.onClick(adapterPosition);
        }
    }

    private final WorkoutListAdapterOnClickHandler clickHandler;

    public interface WorkoutListAdapterOnClickHandler {
        void onClick(int position);
    }

    public WorkoutListAdapter(@NonNull FirebaseRecyclerOptions<Workout> options, WorkoutListAdapterOnClickHandler clickHandler) {
        super(options);
        this.clickHandler = clickHandler;
    }


}
