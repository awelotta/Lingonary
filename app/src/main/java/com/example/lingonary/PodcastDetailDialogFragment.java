package com.example.lingonary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.lingonary.models.Podcast;

public class PodcastDetailDialogFragment extends DialogFragment {

    private static final String ARG_PODCAST = "podcast";
    private HomeFragment.OnHomeFragmentListener listener;

    public static PodcastDetailDialogFragment newInstance(Podcast podcast) {
        PodcastDetailDialogFragment fragment = new PodcastDetailDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PODCAST, podcast);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Assume the parent fragment has the listener
        if (getParentFragment() instanceof HomeFragment.OnHomeFragmentListener) {
            listener = (HomeFragment.OnHomeFragmentListener) getParentFragment();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_podcast_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView ivPodcastCover = view.findViewById(R.id.ivPodcastCover);
        TextView tvPodcastTitle = view.findViewById(R.id.tvPodcastTitle);
        TextView tvPodcastDescription = view.findViewById(R.id.tvPodcastDescription);
        Button btnPlay = view.findViewById(R.id.btnPlay);

        if (getArguments() != null) {
            Podcast podcast = getArguments().getParcelable(ARG_PODCAST);
            if (podcast != null) {
                ivPodcastCover.setImageResource(R.drawable.podcast_cover_one); // Placeholder
                tvPodcastTitle.setText(podcast.getTitle());
                tvPodcastDescription.setText(podcast.getDescription());
            }
        }

        btnPlay.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOpenTranscript();
            }
            dismiss();
        });
    }
}
