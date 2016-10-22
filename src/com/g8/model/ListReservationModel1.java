package com.g8.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.g8.common.Utilities;

public class ListReservationModel1 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nameRes;
	private String name;
	private String resCatID;
	private String reSID;
	public void ListReservationModel(){
		
	}

	public void setData(JSONObject jSonInfo) throws JSONException {
		this.setnameRes(Utilities.getDataString(jSonInfo, "NameRes"));
		this.setName(Utilities.getDataString(jSonInfo, "name"));
		this.setResCatID(Utilities.getDataString(jSonInfo, "resCatID"));
		this.setReSID(Utilities.getDataString(jSonInfo, "reSID"));

	}

	public String getnameRes() {
		return nameRes;
	}

	public void setnameRes(String resId) {
		this.nameRes = resId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResCatID() {
		return resCatID;
	}

	public void setResCatID(String resCatID) {
		this.resCatID = resCatID;
	}

	public String getReSID() {
		return reSID;
	}

	public void setReSID(String reSID) {
		this.reSID = reSID;
	}

}