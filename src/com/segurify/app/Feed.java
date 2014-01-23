package com.segurify.app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Feed {
	private ArrayList<FeedEntry> entries;
	public List<FeedEntry> entries() {
		return entries;
	}
	public Feed() {
		
	}
	public Feed(JSONObject source) {
		JSONArray items;
		entries = new ArrayList<FeedEntry>();
		try {
			items = source.getJSONArray("items");
		
			for (int i = 0; i < items.length(); i++) {
				JSONObject entry = items.getJSONObject(i);
				FeedEntry feedEntry = new FeedEntry(entry);
				entries.add(feedEntry);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
