package huitca1212.frotaalcalvo.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import huitca1212.frotaalcalvo.R;
import huitca1212.frotaalcalvo.business.AdvicesBusiness;

public class AdvicesActivity extends BaseActivity {

	private static final String ADVICE_TYPE = "adviceType";
	private static final String PREFERENCE_NAME = "preference";

	private AdvicesPresenter presenter;
	private String adviceType;

	private TextView adviceText;
	private View loadingBar;

	public static void startActivity(Activity activity, String adviceType) {
		Intent intent = new Intent(activity, AdvicesActivity.class);
		intent.putExtra(ADVICE_TYPE, adviceType);
		activity.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_advices);

		adviceType = getIntent().getStringExtra(ADVICE_TYPE);
		configureActionBar();

		presenter = new AdvicesPresenter(this);

		SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
		String[] botherSentences = new String[]{getString(R.string.bother_1), getString(R.string.bother_2), getString(R.string.bother_3),
				getString(R.string.bother_4), getString(R.string.bother_5), getString(R.string.bother_6), getString(R.string.bother_7)};
		presenter.init(sharedPreferences, adviceType, botherSentences);

		initViews();
	}

	@Override
	public void onResume() {
		super.onResume();
		presenter.retrieveAdvices(adviceType);
	}

	private void configureActionBar() {
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		setTitle(adviceType.equals(AdvicesBusiness.DAILY_ADVICES) ? R.string.daily_advices : R.string.love_advices);
	}

	private void initViews() {
		adviceText = (TextView) findViewById(R.id.advice_text);
		loadingBar = findViewById(R.id.loading_bar);
		ImageView baldManImage = (ImageView) findViewById(R.id.bald_man_image);

		baldManImage.setImageResource(adviceType.equals(AdvicesBusiness.DAILY_ADVICES) ? R.drawable.bald_man : R.drawable.bald_man_love);
		baldManImage.setOnTouchListener(new OnSwipeTouchListener(this) {
			@Override
			public void onSwipe() {
				presenter.onSwipe();
			}

			@Override
			public void onTap() {
				presenter.onTap();
			}
		});
	}

	public void showNoInternetText() {
		adviceText.setText(R.string.no_internet);
	}

	public void showAdvice(String advice) {
		adviceText.setText(advice);
	}

	public void showBotherSentence(String botherSentence) {
		Toast.makeText(AdvicesActivity.this, botherSentence, Toast.LENGTH_SHORT).show();
	}

	public void showLoader() {
		loadingBar.setVisibility(View.VISIBLE);
		adviceText.setVisibility(View.GONE);
	}

	public void hideLoader() {
		loadingBar.setVisibility(View.GONE);
		adviceText.setVisibility(View.VISIBLE);
	}

	public boolean isAdviceLoading() {
		return loadingBar.getVisibility() == View.VISIBLE;
	}

}