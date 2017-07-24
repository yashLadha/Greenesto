package io.github.yashladha.project.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import io.github.yashladha.project.R;
import io.github.yashladha.project.Fragments.util.utilPlayLecture;

public class Lecture extends Fragment
    implements YouTubeThumbnailView.OnInitializedListener {

  private String TAG = getClass().getSimpleName();

  private YouTubeThumbnailView ytThumbView;
  private YouTubeThumbnailLoader ytThumbnailLoader;

  public Lecture() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.d(TAG, "Lecture - Student Portal get's inflated");
    View view = inflater.inflate(R.layout.fragment_lecture, container, false);
    ytThumbView = (YouTubeThumbnailView) view.findViewById(R.id.youtube_thumbnail_view);
    ytThumbView.initialize(getString(R.string.youtube_api_key), this);

    ytThumbView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), utilPlayLecture.class);
        Bundle bundle = new Bundle();
        bundle.putString("Video ID", "kCiHeSyTF3Y");
        intent.putExtra("Video Details", bundle);
        startActivity(intent);
      }
    });
    return view;
  }

  @Override
  public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView,
                                      YouTubeThumbnailLoader youTubeThumbnailLoader) {
    ytThumbnailLoader = youTubeThumbnailLoader;
    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailLoader());
    ytThumbnailLoader.setVideo("kCiHeSyTF3Y");
  }

  @Override
  public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView,
                                      YouTubeInitializationResult youTubeInitializationResult) {
    Log.e(TAG, "Error while Initialization");
  }
}
