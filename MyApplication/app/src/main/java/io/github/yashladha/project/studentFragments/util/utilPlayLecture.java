package io.github.yashladha.project.studentFragments.util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import io.github.yashladha.project.R;

public class utilPlayLecture extends YouTubeBaseActivity
    implements YouTubePlayer.OnInitializedListener{

  private String TAG = getClass().getSimpleName();
  private String VideoID;
  private static final int RECOVERY_REQUEST = 1;

  private YouTubePlayerView ytView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_util_play_lecture);

    Intent intent = getIntent();
    Bundle bundle = intent.getBundleExtra("Video Details");
    VideoID = bundle.getString("Video ID");

    ytView = (YouTubePlayerView) findViewById(R.id.youtube_play_view);
    ytView.initialize(getString(R.string.youtube_api_key), this);

  }

  @Override
  public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                      YouTubePlayer youTubePlayer, boolean b) {
    if (!b) {
      youTubePlayer.cueVideo(VideoID);
    }
  }

  @Override
  public void onInitializationFailure(YouTubePlayer.Provider provider,
                                      YouTubeInitializationResult youTubeInitializationResult) {
    if (youTubeInitializationResult.isUserRecoverableError()) {
      youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
    } else {
      Log.e(TAG, "Unrecoverable error occurred");
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == RECOVERY_REQUEST) {
      ytView.initialize(getString(R.string.youtube_api_key), this);
    }
  }
}
