package com.example.lingonary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingonary.R;
import com.example.lingonary.models.Podcast;

import java.util.List;

public class PodcastAdapter extends RecyclerView.Adapter<PodcastAdapter.PodcastViewHolder> {

    private List<Podcast> podcastList;

    public PodcastAdapter(List<Podcast> podcastList) {
        this.podcastList = podcastList;
    }

    @NonNull
    @Override
    public PodcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_podcast, parent, false);
        return new PodcastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastViewHolder holder, int position) {
        Podcast podcast = podcastList.get(position);
        holder.tvPodcastTitle.setText(podcast.getTitle());
        holder.tvPodcastDescription.setText(podcast.getDescription());
    }

    @Override
    public int getItemCount() {
        return podcastList.size();
    }

    static class PodcastViewHolder extends RecyclerView.ViewHolder {
        TextView tvPodcastTitle, tvPodcastDescription;

        PodcastViewHolder(View itemView) {
            super(itemView);
            tvPodcastTitle = itemView.findViewById(R.id.tvPodcastTitle);
            tvPodcastDescription = itemView.findViewById(R.id.tvPodcastDescription);
        }
    }
}
