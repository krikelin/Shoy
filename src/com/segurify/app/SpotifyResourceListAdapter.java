package com.segurify.app;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpotifyResourceListAdapter extends ArrayAdapter<SpotifyResource> {
	private ArrayList<SpotifyResource> resources = new ArrayList<SpotifyResource>();
	
	public SpotifyResourceListAdapter(Context context, int textViewResourceId, ArrayList<SpotifyResource> resources) {
		
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
		this.resources = resources;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		SpotifyResource resource = resources.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.resource_entry, null);
			
		}
		TextView title = (TextView)convertView.findViewById(R.id.tvTitle);
		title.setText(resource.getTitle());
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.resources.size();
	}

	@Override
	public SpotifyResource getItem(int position) {
		// TODO Auto-generated method stub
		return resources.get(position);
	}

}
