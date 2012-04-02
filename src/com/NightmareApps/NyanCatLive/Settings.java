package com.NightmareApps.NyanCatLive;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//extends PreferenceActivity to make Settings screen on live wallpaper
public class Settings extends Activity {

	String versionName = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);

		// Gets Version # for text view
		PackageInfo pi;
		try {
			pi = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionName = pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TextView versionText = (TextView) findViewById(R.id.versionText);
		versionText.setText("Version: " + versionName);

		Spinner themespinner = (Spinner) findViewById(R.id.themeselector);
		ArrayAdapter<CharSequence> themeadapter = ArrayAdapter
				.createFromResource(this, R.array.themes_array,
						android.R.layout.simple_spinner_item);
		themeadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		themespinner.setAdapter(themeadapter);

		// /

		Spinner speedspinner = (Spinner) findViewById(R.id.speedselector);
		ArrayAdapter<CharSequence> speedadapter = ArrayAdapter
				.createFromResource(this, R.array.speed_array,
						android.R.layout.simple_spinner_item);
		speedadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		speedspinner.setAdapter(speedadapter);

		// /

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 0, "Apply").setIcon(R.drawable.ic_menu_apply);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Toast toast = Toast.makeText(this.getApplicationContext(),
				"Select Nyan Cat Live", Toast.LENGTH_LONG);
		toast.show();
		Intent i = new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
		i.putExtra("android.live_wallpaper.package", getPackageName());
		i.putExtra("android.live_wallpaper.settings", Settings.class);
		startActivity(i);
		return false;
	}
}
