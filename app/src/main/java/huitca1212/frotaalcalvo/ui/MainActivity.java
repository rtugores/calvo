package huitca1212.frotaalcalvo.ui;

import android.os.Bundle;
import android.view.View;

import huitca1212.frotaalcalvo.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		View dayTodayAdviceButton = findViewById(R.id.day_to_day_advice_advice);
		View loveAdviceButton = findViewById(R.id.love_advice_text);
		dayTodayAdviceButton.setOnClickListener(this);
		loveAdviceButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.day_to_day_advice_advice) {
			AdvicesActivity.startActivity(this, "advice");
		} else if (id == R.id.love_advice_text) {
			AdvicesActivity.startActivity(this, "loveAdvice");
		}
	}
}