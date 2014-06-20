package com.nixgon.steak.model;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class SteakTableModel {

	@PrimaryKey
	private String table;

	@Persistent
	private String owner;

	@Persistent
	private ArrayList< String > dish;

	@Persistent
	private ArrayList< String > columns;

	@Persistent
	private Date createdDate;

	@Persistent
	private Date modifiedDate;

	@Persistent
	private ArrayList< Integer > cellWidth;

	// TODO: 공유 대상 - 연락처 연동

	public SteakTableModel( String table, String owner, ArrayList< String > dish, ArrayList< String > columns,
			Date createdDate, Date modifiedDate ) {
		this.table = table;
		this.owner = owner;
		this.dish = dish;
		this.columns = columns;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.cellWidth = new ArrayList< Integer >();
		cellWidth.add( 200 );
		cellWidth.add( 200 );
		cellWidth.add( 200 );
		cellWidth.add( 200 );
	}

	public SteakTableModel( String table, String owner, Date createdDate, Date modifiedDate ) {
		this.table = table;
		this.owner = owner;
		this.dish = new ArrayList< String >();
		this.dish.add( "Main dish" );
		this.columns = new ArrayList< String >();
		this.columns.add( "Dish" );
		this.columns.add( "Name" );
		this.columns.add( "Note" );
		this.columns.add( "Author" );
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.cellWidth = new ArrayList< Integer >();
		cellWidth.add( 200 );
		cellWidth.add( 200 );
		cellWidth.add( 200 );
		cellWidth.add( 200 );
	}

	public String getTable() {
		return table;
	}

	public void setTable( String table ) {
		this.table = table;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner( String owner ) {
		this.owner = owner;
	}

	public ArrayList< String > getDish() {
		return dish;
	}

	public void setDish( ArrayList< String > dish ) {
		this.dish = dish;
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

	public ArrayList< Integer > getCellWidth() {
		return cellWidth;
	}

	public void setCellWidth( ArrayList< Integer > cellWidth ) {
		this.cellWidth = cellWidth;
	}
}
