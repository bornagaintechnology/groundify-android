package deftsoft.iground.android.CustomizeListView;

import com.nostra13.universalimageloader.core.ImageLoader;

import deftsoft.iground.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_clear_memory_cache:
			imageLoader.clearMemoryCache();

			return true;
		case R.id.item_clear_disc_cache:
			imageLoader.clearDiskCache();

			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}

	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

}
