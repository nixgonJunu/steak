package com.nixgon.steak.model;

import java.util.ArrayList;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class SteakShapeModel {

	@PrimaryKey
	private String table;

	@Persistent
	private ArrayList< Integer > width;

	public SteakShapeModel( String table ) {
		this.table = table;
		this.width = new ArrayList< Integer >();
		width.add( 200 );
		width.add( 200 );
		width.add( 200 );
		width.add( 200 );
	}

	public SteakShapeModel( String table, ArrayList< Integer > width ) {
		this.table = table;
		this.width = width;
	}

	public String getTable() {
		return table;
	}

	public void setTable( String table ) {
		this.table = table;
	}

	public ArrayList< Integer > getWidth() {
		return width;
	}

	public void setWidth( ArrayList< Integer > width ) {
		this.width = width;
	}
}