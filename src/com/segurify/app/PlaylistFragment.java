package com.segurify.app;

import android.app.Service;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;

public class PlaylistFragment extends ListFragment {
	public class atask extends AsyncTask<String, String, Response> {
		
		@Override
		protected Response doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Response result = dataSource.getPlaylists(0, 25);
			return result;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Response response) {
			// TODO Auto-generated method stub
			if (response != null) {
				if (response.getResult().getCode() != 500) {
					SpotifyResourceListAdapter adapter = new SpotifyResourceListAdapter(PlaylistFragment.this.getActivity().getApplicationContext(), 0, response.getResources());
					setListAdapter(adapter);
					return;
				}
			}
			
			// Otherwise set error view
			LayoutInflater li = (LayoutInflater)PlaylistFragment.this.getActivity().getApplicationContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			View v = li.inflate(R.layout.playlist_view_error, null);
			
			getListView().setEmptyView(v);
		}
		
	}
	public static DataSource dataSource = new SDataSource();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new atask().execute();
		
	}
}
