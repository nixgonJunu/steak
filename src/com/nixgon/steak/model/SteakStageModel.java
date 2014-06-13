package com.nixgon.steak.model;

import java.util.ArrayList;
import java.util.Date;

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

	@Persistent
	private String pipeline;

	@Persistent
	private Date createdDate;

	@Persistent
	private Date modifiedDate;

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

	public String getPipeline() {
		return pipeline;
	}

	public void setPipeline( String pipeline ) {
		this.pipeline = pipeline;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate( Date createdDate ) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate( Date modifiedDate ) {
		this.modifiedDate = modifiedDate;
	}
}
