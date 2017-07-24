package io.github.yashladha.project.Fragments.util;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

import io.github.yashladha.project.Adapter.CustomAdapter;
import io.github.yashladha.project.Models.Quiz;
import io.github.yashladha.project.R;

public class utilPlayLecture extends YouTubeBaseActivity
    implements YouTubePlayer.OnInitializedListener {

  private static int NUM_PAGES = 3;
  private int curPage = 0;
  private ViewPager mPager;
  private PagerAdapter pagerAdapter;

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

    ArrayList<Quiz> questions = new ArrayList<>();
    questions.add(new Quiz("Which one of the following does not involve coagulation? ",
        new String[]{
            "Peptization",
            "Formation of delta regions",
            "Treatment of drinking water by potash alum",
            "Clotting of blood by the use of ferric chloride"
        }));

    questions.add(new Quiz("Rate of physical adsorption increase with? ",
        new String[]{
            "increase in temperature",
            "decrease in pressure",
            "decrease in temperature",
            "decrease in surface area"
        }));

    questions.add(new Quiz("The colour of sky is due to? ",
        new String[]{
            "absorption of light by atmospheric gases",
            "wavelength of scattered light",
            "transmission of light",
            "All of these"
        }));

    mPager = (ViewPager) findViewById(R.id.slide_quiz);
    pagerAdapter = new CustomAdapter(getApplicationContext(), questions);
    mPager.setAdapter(pagerAdapter);

    ytView = (YouTubePlayerView) findViewById(R.id.youtube_play_view);
    ytView.initialize(getString(R.string.youtube_api_key), this);

    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        if (curPage == NUM_PAGES)
          curPage = 0;
        mPager.setCurrentItem(curPage++, true);
        handler.postDelayed(this, 15000);
      }
    };
    handler.postDelayed(runnable, 15000);

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
