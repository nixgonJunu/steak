package com.nixgon.steak.model;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class SteakModel {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String stage;

	@Persistent
	private String name;

	@Persistent
	private String note;

	@Persistent
	private String author;

	@Persistent
	private Date createdDate;

	@Persistent
	private Date modifiedDate;

	@Persistent
	private ArrayList< String > data;

	public Key getKey() {
		return key;
	}

	public void setKey( Key key ) {
		this.key = key;
	}

	public String getStage() {
		return stage;
	}

	public void setStage( String stage ) {
		this.stage = stage;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote( String note ) {
		this.note = note;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor( String author ) {
		this.author = author;
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

	public ArrayList< String > getData() {
		return data;
	}

	public void setData( ArrayList< String > data ) {
		this.data = data;
	}
}
