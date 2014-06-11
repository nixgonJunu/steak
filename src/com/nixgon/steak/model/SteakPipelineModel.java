package com.nixgon.steak.model;

import java.util.ArrayList;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class SteakPipelineModel {

	@PrimaryKey
	private String pipeline;

	@Persistent
	private ArrayList< String > stages;

	@Persistent
	private ArrayList< String > columns;

	public String getPipeline() {
		return pipeline;
	}

	public void setPipeline( String pipeline ) {
		this.pipeline = pipeline;
	}

	public ArrayList< String > getStages() {
		return stages;
	}

	public void setStages( ArrayList< String > stages ) {
		this.stages = stages;
	}

	public ArrayList< String > getColumns() {
		return columns;
	}

	public void setColumns( ArrayList< String > columns ) {
		this.columns = columns;
	}

}
