package com.nixgon.steak.model;

import java.util.ArrayList;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class SteakShapeModel {

	@PrimaryKey
	private String ownerTable;

	@Persistent
	private ArrayList< Integer > width;

	public SteakShapeModel( String ownerTable ) {
		this.ownerTable = ownerTable;
		this.width = new ArrayList< Integer >();
		width.add( 200 );
		width.add( 200 );
		width.add( 200 );
		width.add( 200 );
	}

	public SteakShapeModel( String ownerTable, ArrayList< Integer > width ) {
		this.ownerTable = ownerTable;
		this.width = width;
	}

	public String getOwnerTable() {
		return ownerTable;
	}

	public void setOwnerTable( String ownerTable ) {
		this.ownerTable = ownerTable;
	}

	public ArrayList< Integer > getWidth() {
		return width;
	}

	public void setWidth( ArrayList< Integer > width ) {
		this.width = width;
	}
}