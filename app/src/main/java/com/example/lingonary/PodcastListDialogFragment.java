package com.example.lingonary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingonary.adapters.PodcastAdapter;
import com.example.lingonary.models.Podcast;

import java.util.ArrayList;
import java.util.List;

public class PodcastListDialogFragment extends DialogFragment {

    public static PodcastListDialogFragment newInstance() {
        return new PodcastListDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_podcast_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvPodcasts = view.findViewById(R.id.rvPodcasts);
        rvPodcasts.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Podcast> podcastList = new ArrayList<>();
        podcastList.add(new Podcast("Maxi Ferraro: “Lo ideal sería que el Gobierno no se radicalice”", "CNN en Español"));
        podcastList.add(new Podcast("Podcast 2", "Description 2"));
        podcastList.add(new Podcast("Podcast 3", "Description 3"));

        PodcastAdapter adapter = new PodcastAdapter(podcastList);
        rvPodcasts.setAdapter(adapter);
    }
}
