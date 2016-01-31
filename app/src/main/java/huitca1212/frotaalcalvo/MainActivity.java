package huitca1212.frotaalcalvo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

	private int sentenceNumber;
	private String[] sentenceArray;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView textView = (TextView)findViewById(R.id.text_display);
		ImageView imageView = (ImageView)findViewById(R.id.calvo);

		sentenceArray = new String[]{
				getString(R.string.c0),
				getString(R.string.c1),
				getString(R.string.c2),
				getString(R.string.c3),
				getString(R.string.c4),
				getString(R.string.c5),
				getString(R.string.c6),
				getString(R.string.c7),
				getString(R.string.c8),
				getString(R.string.c9),
				getString(R.string.c10),
				getString(R.string.c11),
				getString(R.string.c12),
				getString(R.string.c13),
				getString(R.string.c14),
				getString(R.string.c15),
				getString(R.string.c16),
				getString(R.string.c17),
				getString(R.string.c18),
				getString(R.string.c19),
				getString(R.string.c20),
				getString(R.string.c21),
				getString(R.string.c22),
				getString(R.string.c23),
				getString(R.string.c24),
				getString(R.string.c25),
				getString(R.string.c26),
				getString(R.string.c27),
				getString(R.string.c28),
				getString(R.string.c29),
				getString(R.string.c30),
				getString(R.string.c31),
				getString(R.string.c32),
				getString(R.string.c33),
		};

		boolean firstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
		if (firstRun) {
			Utils.showInitDialog(this);
			getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("firstrun", false).commit();
		}

		sentenceNumber = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("numero", -1);
		if (sentenceNumber != -1) {
			textView.setText(sentenceArray[sentenceNumber]);
		}
		//Definimos qué hacer con cada movimiento
		imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
			public void onSwipe() {
				MediaPlayer mpRes;
				String currentDate = DateFormat.getDateInstance().format(new Date());
				String lastDate = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("dateant", "Defecto");
				//Las comparo
				if (currentDate.contains(lastDate)) {
					mpRes = MediaPlayer.create(getApplicationContext(), R.raw.come_tomorrow);
					mpRes.start();
					Toast.makeText(MainActivity.this, R.string.come_tomorrow, Toast.LENGTH_LONG).show();
				} else {
					mpRes = MediaPlayer.create(getApplicationContext(), R.raw.new_advise);
					mpRes.start();
					if (sentenceNumber == -1) {
						sentenceNumber = (int)(Math.random() * (sentenceArray.length + 1)); //primera vez comienza en número aleatorio
						TextView textView = (TextView)findViewById(R.id.text_display);
						textView.setText(sentenceArray[sentenceNumber]); //si es el veinte, vuelve a empezar
						textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
						getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("dateant", currentDate).commit();
						getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("numero", sentenceNumber).commit();
					}
					if (sentenceNumber == sentenceArray.length) {
						sentenceNumber = 0;
						TextView textView = (TextView)findViewById(R.id.text_display);
						textView.setText(sentenceArray[sentenceNumber]); //si es el veinte, vuelve a empezar
						textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
						getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("dateant", currentDate).commit();
						getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("numero", sentenceNumber).commit();
					}
					if (sentenceNumber != -1 && sentenceNumber != sentenceArray.length) {
						sentenceNumber++;
						TextView textView = (TextView)findViewById(R.id.text_display);
						textView.setText(sentenceArray[sentenceNumber]); //si es el veinte, vuelve a empezar
						textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
						getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("dateant", currentDate).commit();
						getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("numero", sentenceNumber).commit();
					}
				}
			}
		});
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
