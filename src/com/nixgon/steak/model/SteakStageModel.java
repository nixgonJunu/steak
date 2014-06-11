package com.nixgon.steak.model;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class SteakStageModel {

	@PrimaryKey
	private String stage;

	@Persistent
	private ArrayList< Key > rows;

	public String getStage() {
		return stage;
	}

	public void setStage( String stage ) {
		this.stage = stage;
	}

	public ArrayList< Key > getRows() {
		return rows;
	}

	public void setRows( ArrayList< Key > rows ) {
		this.rows = rows;
	}

}
