package com.nixgon.steak.model;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class SteakDishModel {

	@PrimaryKey
	private String dish;

	@Persistent
	private String owner;

	@Persistent
	private String table;

	@Persistent
	private ArrayList< Key > rows;

	@Persistent
	private Date createdDate;

	@Persistent
	private Date modifiedDate;

	public SteakDishModel( String dish, String owner, String table, ArrayList< Key > rows, Date createdDate, Date modifiedDate ) {
		this.dish = dish;
		this.owner = owner;
		this.table = table;
		this.rows = rows;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	public SteakDishModel( String dish, String owner, String table, Date createdDate, Date modifiedDate ) {
		this.dish = dish;
		this.owner = owner;
		this.table = table;
		this.rows = new ArrayList< Key >();
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	public String getDish() {
		return dish;
	}

	public void setDish( String dish ) {
		this.dish = dish;
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

	public ArrayList< Key > getRows() {
		return rows;
	}

	public void setRows( ArrayList< Key > rows ) {
		this.rows = rows;
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
