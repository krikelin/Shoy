package com.segurify.app;

import org.json.JSONException;
import org.json.JSONObject;

public class Result {
	private int code;
	private String status;
	public Result() {
		
	}
	public Result(JSONObject obj) {
		try {
			this.setCode(obj.getInt("code"));
		
			this.setStatus(obj.getString("status"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
