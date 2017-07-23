package io.github.yashladha.project;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashScreen extends AwesomeSplash {

	@Override
	public void initSplash(ConfigSplash configSplash) {

		configSplash.setBackgroundColor(R.color.splashBg);
		configSplash.setAnimCircularRevealDuration(1500);
		configSplash.setRevealFlagX(Flags.REVEAL_BOTTOM);
		configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);

		configSplash.setLogoSplash(R.drawable.logo);
		configSplash.setAnimLogoSplashDuration(2000);
		configSplash.setAnimLogoSplashTechnique(Techniques.Flash);

		configSplash.setTitleSplash("Green Board");
		configSplash.setTitleTextColor(R.color.colorAccent);
		configSplash.setTitleTextSize(30f);
		configSplash.setAnimTitleDuration(3000);
		configSplash.setAnimTitleTechnique(Techniques.RubberBand);
	}

	@Override
	public void animationsFinished() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
