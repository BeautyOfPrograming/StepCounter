package com.example.SaberiGhdamShomar.word;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.SaberiGhdamShomar.MainActivity;
import com.example.SaberiGhdamShomar.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.SaberiGhdamShomar.MainActivity.mWordViewModel;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> implements MainActivity.ItemTouchHelperAdapter {

    private onItemClickLisnter lisn;


    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;
        private final TextView duration;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
            duration = itemView.findViewById(R.id.duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (lisn != null && position != RecyclerView.NO_POSITION) {

                        lisn.onItemClick(getItem(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    mWordViewModel.delete(mWords.get(getAdapterPosition()));


                    notifyItemRemoved(getAdapterPosition());
                    Log.d("longClick", "longremove");
                    return false;
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    public List<Word> mWords = new ArrayList<>(); // Cached copy of words

    public WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mWords != null) {

            Word current = mWords.get(position);
            holder.wordItemView.setText(current.getWord());
            holder.duration.setText(current.getDuration());

        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    public void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mWords, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mWords, i, i - 1);
            }
        }
        notifyAll();
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

        mWordViewModel.delete(mWords.get(position));
//        Toast.makeText(,"donedone",Toast.LENGTH_LONG).show();


        Log.d("remove", "done");
        notifyItemRemoved(position);
//        notifyAll();
    }


    public Word getItem(int position) {


        return mWords.get(position);
    }

    public Word getPosition(int position) {


        return mWords.get(position);
    }

    public interface onItemClickLisnter {

        void onItemClick(Word noteBase);

    }

    public void setOnClickListener(onItemClickLisnter listener) {

        this.lisn = listener;
    }


//    The formula is BMI = kg/m2 where kg is a person’s weight in kilograms and m2 is their height in metres squared.

}