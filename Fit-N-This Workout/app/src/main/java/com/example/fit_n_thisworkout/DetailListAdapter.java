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

public class DetailListAdapter extends FirebaseRecyclerAdapter<Set, DetailListAdapter.SetHolder> {

    @Override
    protected void onBindViewHolder(@NonNull DetailListAdapter.SetHolder holder, int position, @NonNull Set model) {
        holder.repsTextView.setText(model.getRepetitions());
        //holder.weightTextView.setText(Double.toString(model.getWeight()));
        holder.weightTextView.setText(String.format("%2f",model.getWeight()));

    }

    @NonNull
    @Override
    public SetHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.detail_list, viewGroup, false);
        return new DetailListAdapter.SetHolder(view);
    }

    class SetHolder extends RecyclerView.ViewHolder {
        //private final TextView setTextView;
        private final TextView repsTextView;
        private final TextView weightTextView;

        public SetHolder (View itemView) {
            super(itemView);
            //setTextView = itemView.findViewById(R.id.setTextView);
            repsTextView = itemView.findViewById(R.id.repsTextView);
            weightTextView = itemView.findViewById(R.id.weightTextView);
            //itemView.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            clickHandler.onClick(adapterPosition);
        }*/
    }

    public DetailListAdapter(@NonNull FirebaseRecyclerOptions<Set> options) {
        super(options);
    }

}
