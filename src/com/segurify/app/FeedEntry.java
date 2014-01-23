package com.segurify.app;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedEntry implements Parcelable {
	private Date time;
	private String title;
	private String link;
	
	public FeedEntry (JSONObject source) {
		try {
			this.time = new Date(source.getLong("time"));
			this.title = source.getString("title");
			this.link = source.getString("link");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public FeedEntry (Parcel source) {
		this.setTime(new Date(source.readLong()));
		this.setTitle(source.readString());
		this.setLink(source.readString());
	}
	
	public FeedEntry () {
		
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getTime() {
		return time;
	}
	public static Parcelable.Creator<FeedEntry> CREATOR = new Creator<FeedEntry>() {
		
		@Override
		public FeedEntry[] newArray(int size) {
			// TODO Auto-generated method stub
			return new FeedEntry[size];
		}
		
		@Override
		public FeedEntry createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			FeedEntry fe = new FeedEntry(source);
			
			return fe;
		}
	};
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeLong(time.getTime());
		arg0.writeString(title);
		arg0.writeString(link);
	}
	
}
