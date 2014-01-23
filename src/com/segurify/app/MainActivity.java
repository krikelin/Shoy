package com.segurify.app;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
public class MainActivity extends FragmentActivity {
	public Adapter adapter;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	protected CharSequence mDrawerTitle;
	protected CharSequence mTitle;
	private ArrayList<NavDrawerItem> mDrawerItems;
	public static final int PLAYLISTS = 1;
	
	public class GeneratePlaylistTask extends AsyncTask<String, Response, Response> {
		ProgressDialog pd;
		@Override
		protected Response doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Response response = PlaylistFragment.dataSource.generatePlaylist(arg0[0], arg0[1], arg0[2]);
			
			return response;
		}

		@Override
		protected void onPostExecute(Response response) {
			// TODO Auto-generated method stub
			if (response != null) {
				if (response.getResult().getCode() == 200) {
					Toast t = Toast.makeText(getApplicationContext(), "Succes", Toast.LENGTH_LONG);
					t.show();
					return;
				}
			}
			Toast t = Toast.makeText(getApplicationContext(), "Sorry, an error occured", Toast.LENGTH_LONG);
			t.show();
			
			pd.cancel();
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pd = ProgressDialog.show(getApplicationContext(), getResources().getString(R.string.title), "");
			
			super.onPreExecute();
		}
		
	}
	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			switch(arg2) {
			case PLAYLISTS:
				Fragment f = new PlaylistFragment();
				FragmentManager fm = getSupportFragmentManager();
				fm.beginTransaction()
					.replace(R.id.frame_container, f)
					.commit();
				
				mDrawerLayout.closeDrawers();
			
				break;
			}
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawerItems = new ArrayList<NavDrawerItem>();
		mDrawerItems.add(new NavDrawerItem(getResources().getString(R.string.home), R.drawable.ic_action_user));
		NavDrawerItem nItem = new NavDrawerItem(getResources().getString(R.string.playlists), R.drawable.ic_action_user);
		nItem.setId(PLAYLISTS);
		mDrawerItems.add(nItem);
		adapter = new NavDrawerListAdapter(getApplicationContext(), mDrawerItems);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		mDrawerList.setAdapter((ListAdapter) adapter);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		){
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	AlertDialog createDialog;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_new:
			{
				AlertDialog.Builder builder = new Builder(this);
				LayoutInflater li = (LayoutInflater)getApplicationContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
				View v = li.inflate(R.layout.prompt_create_playlist, null);
				final EditText tvTitle = (EditText) v.findViewById(R.id.uiTitle);
				final EditText tvDescription = (EditText) v.findViewById(R.id.uiDescription);
				final EditText tvQuery = (EditText) v.findViewById(R.id.uiQuery);
				builder.setView(v);
				builder.setCancelable(false).setPositiveButton("OK", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						new GeneratePlaylistTask().execute(tvTitle.getText().toString(), tvDescription.getText().toString(), tvQuery.getText().toString());
						
					}
				}).setNegativeButton("Cancel",	
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
				createDialog = builder.create();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_new).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
