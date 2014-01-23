package com.segurify.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Response {
	private Result result;
	private Object data;
	@SuppressWarnings("unchecked")
	public ArrayList<SpotifyResource> getResources() {
		if (this.data instanceof ArrayList<?>)
		return (ArrayList<SpotifyResource>)this.data;
		return null;
	}
	public SpotifyResource getResource() {
		if (this.data instanceof SpotifyResource) {
			return (SpotifyResource) this.data;
		}
		return null;
	}
	public Response() {
		
	}
	public Response(JSONObject source) {
		JSONObject result;
		try {
			result = source.getJSONObject("result");
			this.result = new Result(result);
			if (source.has("playlist"))
				this.data = new SpotifyResource(source.getJSONObject("playlist"));
			if (source.has("playlists")) {
				JSONArray playlists = source.getJSONArray("playlists");
				ArrayList<SpotifyResource> resources = new ArrayList<SpotifyResource>();
				for(int i = 0; i < playlists.length(); i++) {
					SpotifyResource playlist = new SpotifyResource(playlists.getJSONObject(i));
					resources.add(playlist);
				}
				this.data = resources;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
