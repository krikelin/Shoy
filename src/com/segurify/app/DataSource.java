package com.segurify.app;

public abstract class DataSource {
	public abstract Response generatePlaylist(String title, String description, String query);
	public abstract Response getPlaylists(int page, int size);
	public abstract Response getFeed();
}
