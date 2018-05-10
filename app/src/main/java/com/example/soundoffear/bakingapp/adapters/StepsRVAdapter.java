package com.example.soundoffear.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soundoffear.bakingapp.R;
import com.example.soundoffear.bakingapp.interfaces.OnRecyclerViewClickListener;
import com.example.soundoffear.bakingapp.models.StepsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsRVAdapter extends RecyclerView.Adapter<StepsRVAdapter.StepsViewHolder> {

    private Context context;
    private List<StepsModel> stepsModelList;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public StepsRVAdapter(Context context, List<StepsModel> stepsModelList, OnRecyclerViewClickListener onRecyclerViewClickListenerList) {
        this.context = context;
        this.stepsModelList = stepsModelList;
        onRecyclerViewClickListener = onRecyclerViewClickListenerList;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View stepsView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recipe_steps_item_list, parent, false);

        return new StepsViewHolder(stepsView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {

        holder.tv_title.setText(stepsModelList.get(position).getStepShortDescription());

    }

    @Override
    public int getItemCount() {
        return stepsModelList.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.steps_title) TextView tv_title;

        private StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecyclerViewClickListener.onRecyclerViewClick(getLayoutPosition());
        }
    }

}
