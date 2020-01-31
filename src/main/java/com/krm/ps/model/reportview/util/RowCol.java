package com.krm.ps.model.reportview.util;

/**
 * a Object to save row and col.
 * 
 * @author alvin(2006-10-11)
 */
public class RowCol {
	private int row;
	private int col;

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setRow(int value) {
		row = value;
	}

	public void setCol(int value) {
		col = value;
	}

	public RowCol() {
		super();
		row = -1;
		col = -1;
	}
}
