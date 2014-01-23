package com.segurify.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class SDataSource extends DataSource {
	private String ConvertStreamToString(InputStream in)
	{
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try 
	    {
	        while ((line = reader.readLine()) != null) {
	        sb.append(line + "\n");
	    }
	    }
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    } 
	    finally 
	    {
	        try 
	        {
	            in.close();
	        } 
	        catch (IOException e) 
	        {
	             Log.e("REST_CLIENT", "ConvertStreamToString: " + e.getMessage());  
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	public static final String API_KEY = "be999bf1bd2b3368c8b74e2824aed552";
	@Override
	public Response generatePlaylist(String title, String description,
			String query) {
		try {
			JSONObject jo = new JSONObject();
			jo.put("title", title);
			jo.put("description", description);
			jo.put("query", query);
			
			// TODO Auto-generated method stub
			URL url = new URL("http://ws.segurify.com/playlist/generate?key=" + API_KEY);
	
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url.toURI());
	
			// Prepare JSON to send by setting the entity
			httpPost.setEntity(new StringEntity(jo.toString(), "UTF-8"));
	
			// Set up the header types needed to properly transfer JSON
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setHeader("Accept-Encoding", "UTF-8");
			httpPost.setHeader("Accept-Language", "en-US");
	
			// Execute POST
			HttpResponse response = httpClient.execute(httpPost);
			InputStream is = response.getEntity().getContent();
			String json = ConvertStreamToString(is);
			JSONObject object = new JSONObject(json);
			Response reply = new Response(object);
			return reply;
		} catch (Exception e) {
			Response response = new Response();
			response.setResult(new Result());
			response.getResult().setCode(500);
			return response;
		}
	}

	@Override
	public Response getPlaylists(int page, int size) {
		// TODO Auto-generated method stub
		URL url;
		try {
			url = new URL("http://ws.segurify.com/playlist?key=" + API_KEY + "&page=" + String.valueOf(page) + "&size=" + String.valueOf(size));
		
		
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url.toURI());
			HttpResponse response = httpClient.execute(httpGet);
			InputStream is = response.getEntity().getContent();
			String json = ConvertStreamToString(is);
			JSONObject obj = new JSONObject(json);
			Response _response = new Response(obj);
			return _response;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Response getFeed() {
		// TODO Auto-generated method stub
		return null;
	}

}
