package com.segurify.app;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * Represents a playlist parcelable
 * @author Alecca <alecca@segurify.com>
 *
 */
public class SpotifyResource implements Parcelable {
	private String uri;
	private String title;
	private String description;
	private String type;
	private int followers;
	
	public SpotifyResource() {
		
	}
	
	/**
	 * Creates a SpotifyResource from json object
	 * @param object the Json object
	 */
	public SpotifyResource(JSONObject object) {
		try {
			this.uri = object.getString("uri");
			this.description = object.getString("description");
			this.type = "playlist"; // object.getString("type");
			this.title = object.getString("title");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a spotify resource from parcel
	 * @param source The parcel to extract
	 */
	public SpotifyResource(Parcel source) {
		this.setUri(source.readString());
		this.setTitle(source.readString());
		this.setDescription(source.readString());
		this.setFollowers(source.readInt());
		this.setType(source.readString());
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public static Parcelable.Creator<SpotifyResource> CREATOR = new Creator<SpotifyResource>() {
		
		@Override
		public SpotifyResource[] newArray(int size) {
			// TODO Auto-generated method stub
			return new SpotifyResource[size];
		}
		
		@Override
		public SpotifyResource createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			SpotifyResource playlist = new SpotifyResource(source);
			
			return playlist;
		}
	};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(uri);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeInt(followers);
		dest.writeString(type);
	}
	public int getFollowers() {
		return followers;
	}
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
