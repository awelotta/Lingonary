package com.example.lingonary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingonary.adapters.PodcastAdapter;
import com.example.lingonary.models.Podcast;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private OnHomeFragmentListener listener;
    private List<Podcast> allPodcasts;
    private PodcastAdapter featuredAdapter;
    private PodcastAdapter recentAdapter;

    public interface OnHomeFragmentListener {
        void onOpenPodcast(Podcast podcast);
        void onOpenTranscript();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentListener) {
            listener = (OnHomeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView tvGreeting = view.findViewById(R.id.tvGreetingHome);
        Button btnLanguage = view.findViewById(R.id.btnCurrentLanguage);
        AutoCompleteTextView actvSearch = view.findViewById(R.id.actvSearch);

        RecyclerView rvFeatured = view.findViewById(R.id.rvFeatured);
        rvFeatured.setLayoutManager(new GridLayoutManager(getContext(), 3));

        String description = "This is a podcast app built by Team Spaghetti during the fall semester of 2025.";
        allPodcasts = new ArrayList<>();
        allPodcasts.add(new Podcast("Add words", description));
        allPodcasts.add(new Podcast("Podcast", description));
        allPodcasts.add(new Podcast("Podcast 3", description));
        allPodcasts.add(new Podcast("Podcast 4", description));
        allPodcasts.add(new Podcast("Podcast 5", description));
        allPodcasts.add(new Podcast("Podcast 6", description));
        allPodcasts.add(new Podcast("Podcast 7", description));
        allPodcasts.add(new Podcast("Podcast 8", description));
        allPodcasts.add(new Podcast("Podcast 9", description));

        // Create adapters for the RecyclerViews
        featuredAdapter = new PodcastAdapter(allPodcasts.subList(0, 6), podcast -> {
            if (podcast.getTitle().equals("Podcast")) {
                if (listener != null) {
                    listener.onOpenTranscript();
                }
            } else {
                PodcastDetailDialogFragment.newInstance(podcast).show(getParentFragmentManager(), "podcast_detail_dialog");
            }
        });
        rvFeatured.setAdapter(featuredAdapter);

        RecyclerView rvRecent = view.findViewById(R.id.rvRecent);
        rvRecent.setLayoutManager(new GridLayoutManager(getContext(), 3));

        recentAdapter = new PodcastAdapter(allPodcasts.subList(6, 9), podcast -> {
            PodcastDetailDialogFragment.newInstance(podcast).show(getParentFragmentManager(), "podcast_detail_dialog");
        });
        rvRecent.setAdapter(recentAdapter);

        // Setup AutoCompleteTextView
        List<String> podcastTitles = new ArrayList<>();
        for (Podcast podcast : allPodcasts) {
            podcastTitles.add(podcast.getTitle());
        }
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, podcastTitles);
        actvSearch.setAdapter(searchAdapter);

        actvSearch.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedTitle = (String) parent.getItemAtPosition(position);
            for (Podcast podcast : allPodcasts) {
                if (podcast.getTitle().equals(selectedTitle)) {
                    PodcastDetailDialogFragment.newInstance(podcast).show(getParentFragmentManager(), "podcast_detail_dialog");
                    break;
                }
            }
        });


        Bundle args = getArguments();
        if (args != null) {
            String username = args.getString("username");
            String targetLang = args.getString("targetLanguage");

            if (username != null && !username.isEmpty()) {
                tvGreeting.setText("Hello " + username + "!");
            } else {
                tvGreeting.setText("Hello!");
            }

            if (targetLang != null && !targetLang.isEmpty()) {
                btnLanguage.setText(targetLang);
            } else {
                btnLanguage.setText("Language");
            }
        }

        TextView tvViewMore = view.findViewById(R.id.tvViewMore);
        TextView tvViewAll = view.findViewById(R.id.tvViewAll);

        View.OnClickListener popupListener = v -> {
            PodcastListDialogFragment.newInstance().show(getParentFragmentManager(), "podcast_detail_dialog");
        };

        tvViewMore.setOnClickListener(popupListener);
        tvViewAll.setOnClickListener(popupListener);

        return view;
    }
}
