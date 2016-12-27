package huitca1212.frotaalcalvo;

import android.app.Application;
import android.content.Context;

public class BaldApplication extends Application {

	private static Context instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public static Context getInstance() {
		return instance;
	}
}
