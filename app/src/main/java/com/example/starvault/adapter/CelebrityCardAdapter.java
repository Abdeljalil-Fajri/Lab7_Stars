package com.example.starvault.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.starvault.R;
import com.example.starvault.model.Celebrity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CelebrityCardAdapter extends RecyclerView.Adapter<CelebrityCardAdapter.CardHolder>
        implements Filterable {

    private List<Celebrity> originalCatalog;
    private List<Celebrity> filteredCatalog;
    private Context ctx;
    private OnCelebrityClickListener clickListener;

    public interface OnCelebrityClickListener {
        void onCelebrityClick(Celebrity celebrity, int position);
    }

    public CelebrityCardAdapter(Context ctx, List<Celebrity> data, OnCelebrityClickListener listener) {
        this.ctx = ctx;
        this.originalCatalog = data;
        this.filteredCatalog = new ArrayList<>(data);
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(ctx).inflate(R.layout.card_celebrity, parent, false);
        return new CardHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        Celebrity celeb = filteredCatalog.get(position);
        holder.lblName.setText(celeb.getFullName());
        holder.barScore.setRating(celeb.getScore());
        Glide.with(ctx)
                .load(celeb.getPhotoUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.portrait);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onCelebrityClick(celeb, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredCatalog.size();
    }

    public void sortByName() {
        Collections.sort(filteredCatalog,
                (a, b) -> a.getFullName().compareToIgnoreCase(b.getFullName()));
        notifyDataSetChanged();
    }

    public void sortByScore() {
        Collections.sort(filteredCatalog,
                (a, b) -> Float.compare(b.getScore(), a.getScore()));
        notifyDataSetChanged();
    }

    public Celebrity removeAt(int position) {
        Celebrity removed = filteredCatalog.remove(position);
        notifyItemRemoved(position);
        return removed;
    }

    public void insertAt(int position, Celebrity celeb) {
        filteredCatalog.add(position, celeb);
        notifyItemInserted(position);
    }

    public void refreshData() {
        filteredCatalog = new ArrayList<>(originalCatalog);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Celebrity> results = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    results.addAll(originalCatalog);
                } else {
                    String query = constraint.toString().toLowerCase().trim();
                    for (Celebrity c : originalCatalog) {
                        if (c.getFullName().toLowerCase().contains(query)) {
                            results.add(c);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = results;
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCatalog.clear();
                filteredCatalog.addAll((List<Celebrity>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    static class CardHolder extends RecyclerView.ViewHolder {
        CircleImageView portrait;
        TextView lblName;
        RatingBar barScore;

        CardHolder(@NonNull View itemView) {
            super(itemView);
            portrait = itemView.findViewById(R.id.portrait);
            lblName = itemView.findViewById(R.id.lblName);
            barScore = itemView.findViewById(R.id.barScore);
        }
    }
}