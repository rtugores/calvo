package huitca1212.frotaalcalvo.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import huitca1212.frotaalcalvo.R;

public class BaseActivity extends AppCompatActivity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
		} else if (id == R.id.menu_share) {
			Utils.shareApp(this);
		} else if (id == R.id.menu_info) {
			Utils.showInfoApp(this);
		}
		return super.onOptionsItemSelected(item);
	}
}