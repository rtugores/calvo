package huitca1212.frotaalcalvo.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import huitca1212.frotaalcalvo.R;
import huitca1212.frotaalcalvo.business.AdvicesBusiness;
import huitca1212.frotaalcalvo.business.AllBusinessListener;

public class AdvicesActivity extends AppCompatActivity {
	private static final String PREFERENCE_NAME = "preference";
	private static final String ADVICE_NUMBER = "adviceNumber";

	private TextView adviceText;
	private View loadingBar;
	private SharedPreferences sharedPreferences;
	private CircularList<String> advices = new CircularList<>();
	private int adviceNumber;
	private boolean isGettingAdvices;
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

	public static void startActivity(Activity activity) {
		Intent intent = new Intent(activity, AdvicesActivity.class);
		activity.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advices);

		adviceText = (TextView) findViewById(R.id.advice_text);
		loadingBar = findViewById(R.id.loading_bar);
		ImageView baldManImage = (ImageView) findViewById(R.id.bald_man_image);
		baldManImage.setOnTouchListener(onSwipeTouchListener);

		sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
		adviceNumber = sharedPreferences.getInt(ADVICE_NUMBER, 0);
	}

	@Override
	public void onResume() {
		super.onResume();
		getAdvices();
	}

	private void getAdvices() {
		showLoader();
		AdvicesBusiness.getAdvices(new AllBusinessListener<CircularList<String>>() {
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
		sharedPreferences.edit().putInt(ADVICE_NUMBER, adviceNumber).apply();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_share:
				Utils.shareApp(this);
				return true;
			case R.id.menu_info:
				Utils.showInfoApp(this);
				return true;
		}
		return true;
	}
}