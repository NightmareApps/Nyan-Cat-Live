package com.NightmareApps.NyanCatLive;

import com.NightmareApps.NyanCatLive.Engine.RenderEngine;

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

		getPreferenceManager().setSharedPreferencesName(
				Engine.SHARED_PREFS_NAME);
		addPreferencesFromResource(R.layout.wallpaper_settings);
		getPreferenceManager().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 0, 0, "Show Live Wallpaper").setIcon(
				R.drawable.ic_menu_apply);
		return super.onCreateOptionsMenu(menu);
	}

	  @Override
	    protected void onResume() {
	        super.onResume();
	    }

	    @Override
	    protected void onDestroy() {
	        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
	                this);
	        super.onDestroy();
	    }

	    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
	            String key) {
	    }
}
