package huitca1212.frotaalcalvo.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import huitca1212.frotaalcalvo.R;
import huitca1212.frotaalcalvo.business.AdvicesBusiness;
import huitca1212.frotaalcalvo.business.AllBusinessListener;

public class AdvicesActivity extends BaseActivity {
	private static final String ADVICE_TYPE = "adviceType";
	private static final String PREFERENCE_NAME = "preference";
	private static final String DAILY_ADVICE_NUMBER = "dailyAdviceNumber";
	private static final String LOVE_ADVICE_NUMBER = "loveAdviceNumber";

	private TextView adviceText;
	private View loadingBar;
	private SharedPreferences sharedPreferences;
	private CircularList<String> advices = new CircularList<>();
	private int adviceNumber;
	private boolean isGettingAdvices;
	private String adviceType;
	private OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener() {
		@Override
		public void onSwipe() {
			if (!advices.isEmpty()) {
				adviceNumber++;
				updateAdvice();
			} else if (!isGettingAdvices) {
				getAdvices();
			}
		}
	};

	public static void startActivity(Activity activity, String adviceType) {
		Intent intent = new Intent(activity, AdvicesActivity.class);
		intent.putExtra(ADVICE_TYPE, adviceType);
		activity.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advices);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		adviceType = getIntent().getStringExtra(ADVICE_TYPE);
		setTitle(adviceType.equals(AdvicesBusiness.DAILY_ADVICES) ? R.string.daily_advices : R.string.love_advices);

		adviceText = (TextView) findViewById(R.id.advice_text);
		loadingBar = findViewById(R.id.loading_bar);
		ImageView baldManImage = (ImageView) findViewById(R.id.bald_man_image);
		baldManImage.setOnTouchListener(onSwipeTouchListener);

		sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
		adviceNumber = sharedPreferences.getInt(getPreferenceFieldByAdviceType(), 0);
	}

	@Override
	public void onResume() {
		super.onResume();
		getAdvices();
	}

	private void getAdvices() {
		showLoader();
		AdvicesBusiness.getAdvices(adviceType, new AllBusinessListener<CircularList<String>>() {
			@Override
			public void onDatabaseSuccess(CircularList<String> object) {
				if (!object.isEmpty()) {
					advices = object;
					updateAdvice();
					hideLoader();
				}
			}

			@Override
			public void onServerSuccess(CircularList<String> object) {
				if (!object.isEmpty()) {
					advices = object;
					updateAdvice();
				}
				hideLoader();
			}

			@Override
			public void onFailure(Exception result) {
				if (advices.isEmpty()) {
					adviceText.setText(R.string.no_internet);
				}
				hideLoader();
			}
		});
	}

	private void updateAdvice() {
		adviceText.setText(advices.get(adviceNumber));
		sharedPreferences.edit().putInt(getPreferenceFieldByAdviceType(), adviceNumber).apply();
	}

	private void showLoader() {
		loadingBar.setVisibility(View.VISIBLE);
		adviceText.setVisibility(View.GONE);
		isGettingAdvices = true;
	}

	private void hideLoader() {
		loadingBar.setVisibility(View.GONE);
		adviceText.setVisibility(View.VISIBLE);
		isGettingAdvices = false;
	}

	private String getPreferenceFieldByAdviceType() {
		return adviceType.equals(AdvicesBusiness.DAILY_ADVICES) ? DAILY_ADVICE_NUMBER : LOVE_ADVICE_NUMBER;
	}
}