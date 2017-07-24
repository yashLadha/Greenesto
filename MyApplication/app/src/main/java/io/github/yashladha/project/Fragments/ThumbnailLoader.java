package io.github.yashladha.project.Fragments;

import android.util.Log;

import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

class ThumbnailLoader implements YouTubeThumbnailLoader.OnThumbnailLoadedListener {

  private String TAG = getClass().getSimpleName();

  @Override
  public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
    Log.d(TAG, "Thumbnail Loaded");
  }

  @Override
  public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView,
                               YouTubeThumbnailLoader.ErrorReason errorReason) {
    Log.e(TAG, "Error while loading thumbnail");
  }
}
