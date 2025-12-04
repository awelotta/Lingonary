package com.example.lingonary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingonary.R;
import com.example.lingonary.models.Podcast;

import java.util.List;

public class PodcastAdapter extends RecyclerView.Adapter<PodcastAdapter.PodcastViewHolder> {

    private List<Podcast> podcastList;
    private OnPodcastClickListener listener;

    public interface OnPodcastClickListener {
        void onPodcastClick(Podcast podcast);
    }

    public PodcastAdapter(List<Podcast> podcastList, OnPodcastClickListener listener) {
        this.podcastList = podcastList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PodcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_podcast_grid, parent, false);
        return new PodcastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastViewHolder holder, int position) {
        holder.bind(podcastList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return podcastList.size();
    }

    public void updateList(List<Podcast> newList) {
        podcastList = newList;
        notifyDataSetChanged();
    }

    static class PodcastViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPodcastCover;

        PodcastViewHolder(View itemView) {
            super(itemView);
            ivPodcastCover = itemView.findViewById(R.id.iv_podcast_cover);
        }

        public void bind(final Podcast podcast, final OnPodcastClickListener listener) {
            // Here you would load the real image using a library like Glide or Picasso
            // For now, we'll just set a placeholder.
            ivPodcastCover.setImageResource(R.drawable.podcast_cover_one);
            itemView.setOnClickListener(v -> listener.onPodcastClick(podcast));
        }
    }
}
