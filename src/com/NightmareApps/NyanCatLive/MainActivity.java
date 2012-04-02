/*
 *    Copyright (C) 2010 Stewart Gateley <birbeck@gmail.com>
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.NightmareApps.NyanCatLive;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.install_help);
		
		final Button set_wallpaper = (Button)findViewById(R.id.set_wallpaper);
		set_wallpaper.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
				i.putExtra("android.live_wallpaper.package", getPackageName());
				i.putExtra("android.live_wallpaper.settings", Settings.class);
				startActivity(i);
			}
		});

		final Button configure = (Button)findViewById(R.id.configure);
		configure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Settings.class);
				startActivity(i);
			}
		});
	}

}
