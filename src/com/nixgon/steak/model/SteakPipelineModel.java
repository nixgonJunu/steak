package com.nixgon.steak.model;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class SteakPipelineModel {

	@PrimaryKey
	private String pipeline;

	@Persistent
	private ArrayList< String > stages;

	@Persistent
	private ArrayList< String > columns;

	@Persistent
	private Date createdDate;

	@Persistent
	private Date modifiedDate;

	// TODO: 공유 대상 - 연락처 연동

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
