package com.nixgon.steak.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class SteakDataModel {

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
	private Date date;

	@Persistent
	private Date startDate;

	@Persistent
	private Date dueDate;

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

	public Date getDate() {
		return date;
	}

	public void setDate( Date date ) {
		this.date = date;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate( Date startDate ) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate( Date dueDate ) {
		this.dueDate = dueDate;
	}

}
