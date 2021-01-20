package com.example.SaberiGhdamShomar.step;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SaberiGhdamShomar.R;

import java.util.List;

public class StepRecyclerAdaptor extends ListAdapter<StepBase, StepRecyclerAdaptor.StepViewHolder> {


    private onItemClickListener listener;

    // added later by habib
    private List<StepBase> stepBases;

    public StepRecyclerAdaptor() {

        super(CALLBACK);
    }


    private static final DiffUtil.ItemCallback<StepBase> CALLBACK = new DiffUtil.ItemCallback<StepBase>() {
        @Override
        public boolean areItemsTheSame(@NonNull StepBase oldItem, @NonNull StepBase newItem) {
            return oldItem.getId() == newItem.getId();
        }

        //        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull StepBase oldItem, @NonNull StepBase newItem) {

            return oldItem.getStepNumber() == (newItem.getStepNumber());
        }
    };


    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item_card_view, parent, false);

        return new StepViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {

        StepBase base = getPosition(position);
        holder.stepNumber.setText(String.valueOf(base.getStepNumber()));

    }

    public interface onItemClickListener {

        void onItemClick(StepBase step);
    }

    public void setOnItemClickListener(onItemClickListener listen) {
        this.listener = listen;
    }


    public StepBase getPosition(int position) {

        return getItem(position);
    }


    class StepViewHolder extends RecyclerView.ViewHolder {


        private TextView stepNumber;
        private TextView timeDuration;
        private TextView meterNumber;
        private TextView kilometerNumber;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);

            stepNumber = itemView.findViewById(R.id.hm);
            timeDuration = itemView.findViewById(R.id.kil);
            meterNumber = itemView.findViewById(R.id.kcal);
            kilometerNumber = itemView.findViewById(R.id.location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {

                        listener.onItemClick(getItem(position));

                    }
                }
            });
        }
    }


    // added later by habib

  public   void setStepBases(List<StepBase> stepBases) {

        stepBases = stepBases;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        if (stepBases != null)
            return stepBases.size();
        else return 0;


    }
}
