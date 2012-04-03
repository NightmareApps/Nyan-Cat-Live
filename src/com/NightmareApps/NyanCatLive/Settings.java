package com.NightmareApps.NyanCatLive;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

//extends PreferenceActivity to make Settings screen on live wallpaper
public class Settings extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.wallpaper_settings);
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		System.out.println(prefs.getString("theme_key", "0"));
		System.out.println(prefs.getString("speed_key", "100"));
		System.out.println(prefs.getBoolean("strobe_key", false));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 0, 0, "Show Live Wallpaper").setIcon(
				R.drawable.ic_menu_apply);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			finish();
			return true;
		}
		return false;
	}

	// String versionName = "";
	//
	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.settings_layout);
	//
	// // Gets Version # for text view
	// PackageInfo pi;
	// try {
	// pi = getPackageManager().getPackageInfo(getPackageName(), 0);
	// versionName = pi.versionName;
	// } catch (NameNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// TextView versionText = (TextView) findViewById(R.id.versionText);
	// versionText.setText("Version: " + versionName);
	//
	// // //
	// // TODO Make these spinners do something
	// Spinner themespinner = (Spinner) findViewById(R.id.themeselector);
	// ArrayAdapter<CharSequence> themeadapter = ArrayAdapter
	// .createFromResource(this, R.array.themes_array,
	// android.R.layout.simple_spinner_item);
	// themeadapter
	// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// themespinner.setAdapter(themeadapter);
	//
	// // //
	// // TODO Make these spinners do something
	// Spinner speedspinner = (Spinner) findViewById(R.id.speedselector);
	// ArrayAdapter<CharSequence> speedadapter = ArrayAdapter
	// .createFromResource(this, R.array.speed_array,
	// android.R.layout.simple_spinner_item);
	// speedadapter
	// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// speedspinner.setAdapter(speedadapter);
	//
	// // //
	//
	//
	//
	// }
	//
	// public boolean onCreateOptionsMenu(Menu menu) {
	// menu.add(1, 1, 0, "Apply").setIcon(R.drawable.ic_menu_apply);
	// return super.onCreateOptionsMenu(menu);
	// }
	//
	// public boolean onOptionsItemSelected(MenuItem item) {
	// Toast toast = Toast.makeText(this.getApplicationContext(),
	// "Select Nyan Cat Live", Toast.LENGTH_LONG);
	// toast.show();
	// Intent i = new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
	// i.putExtra("android.live_wallpaper.package", getPackageName());
	// i.putExtra("android.live_wallpaper.settings", Settings.class);
	// startActivity(i);
	// return false;
	// }
}
