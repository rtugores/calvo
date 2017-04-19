package huitca1212.frotaalcalvo.ui;

import android.content.SharedPreferences;

import java.util.Random;

import huitca1212.frotaalcalvo.business.AdvicesBusiness;
import huitca1212.frotaalcalvo.business.AllBusinessListener;

public class AdvicesPresenter {

	private static final String DAILY_ADVICE_NUMBER = "dailyAdviceNumber";
	private static final String LOVE_ADVICE_NUMBER = "loveAdviceNumber";

	private AdvicesActivity activity;
	private SharedPreferences sharedPreferences;
	private CircularList<String> advices;
	private String[] botherSentences;
	private String adviceType;
	private int adviceNumber;

	public AdvicesPresenter(AdvicesActivity activity) {
		this.activity = activity;
	}

	public void init(SharedPreferences sharedPreferences, String adviceType, String[] botherSentences) {
		this.sharedPreferences = sharedPreferences;
		this.adviceType = adviceType;
		this.botherSentences = botherSentences;
		adviceNumber = sharedPreferences.getInt(getPreferenceFieldByAdviceType(), 0);
	}

	private String getPreferenceFieldByAdviceType() {
		return adviceType.equals(AdvicesBusiness.DAILY_ADVICES) ? DAILY_ADVICE_NUMBER : LOVE_ADVICE_NUMBER;
	}

	public void retrieveAdvices(String adviceType) {
		activity.showLoader();
		AdvicesBusiness.getAdvices(adviceType, new AllBusinessListener<CircularList<String>>() {
			@Override
			public void onDatabaseSuccess(CircularList<String> object) {
				if (!object.isEmpty()) {
					advices = object;
					activity.showAdvice(advices.get(adviceNumber));
					activity.hideLoader();
				}
			}

			@Override
			public void onServerSuccess(CircularList<String> object) {
				if (!object.isEmpty()) {
					advices = object;
					activity.showAdvice(advices.get(adviceNumber));
				}
				activity.hideLoader();
			}

			@Override
			public void onFailure(Exception result) {
				if (advices.isEmpty()) {
					activity.showNoInternetText();
				}
				activity.hideLoader();
			}
		});
	}

	public void onSwipe() {
		if (!advices.isEmpty()) {
			adviceNumber++;
			sharedPreferences.edit().putInt(getPreferenceFieldByAdviceType(), adviceNumber).apply();
			activity.showAdvice(advices.get(adviceNumber));
		} else if (!activity.isAdviceLoading()) {
			retrieveAdvices(adviceType);
		}
	}

	public void onTap() {
		int index = new Random().nextInt(botherSentences.length);
		activity.showBotherSentence(botherSentences[index]);
	}
}