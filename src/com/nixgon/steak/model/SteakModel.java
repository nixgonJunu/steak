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
	private String owner;

	@Persistent
	private String table;

	@Persistent
	private String dish;

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
	private ArrayList< String > values;

	public SteakModel( Key key, String owner, String table, String dish, String name, String note, String author, Date createdDate,
			Date modifiedDate, ArrayList< String > values ) {
		this.key = key;
		this.owner = owner;
		this.table = table;
		this.dish = dish;
		this.name = name;
		this.note = note;
		this.author = author;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.values = values;
	}

	public SteakModel( Key key, String owner, String table, String dish, String name, String note, String author, Date createdDate,
			Date modifiedDate ) {
		this.key = key;
		this.owner = owner;
		this.table = table;
		this.dish = dish;
		this.name = name;
		this.note = note;
		this.author = author;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.values = new ArrayList< String >();
	}

	public Key getKey() {
		return key;
	}

	public void setKey( Key key ) {
		this.key = key;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner( String owner ) {
		this.owner = owner;
	}

	public String getTable() {
		return table;
	}

	public void setTable( String table ) {
		this.table = table;
	}

	public String getDish() {
		return dish;
	}

	public void setDish( String dish ) {
		this.dish = dish;
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

	public ArrayList< String > getValues() {
		return values;
	}

	public void setValues( ArrayList< String > values ) {
		this.values = values;
	}
}
