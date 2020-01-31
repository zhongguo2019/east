package com.krm.ps.model.xlsinit.vo;

import java.io.Serializable;

import com.krm.ps.model.xlsinit.constants.TypeConfig;


public class TableField implements Serializable{

	/**
	 * 存放xml中的字段对象
	 */
	private static final long serialVersionUID = 3858025983560340802L;

	private String _name = null;	
	private int _type = TypeConfig.STRING;
	private boolean _pk = false;
	private String _seq = "";
	private boolean _drop = false;    
    private String _constval = "";
    private String _map = null;
    
	public TableField(String _name, int _type,boolean _pk,String _seq ,boolean _drop ,String _constval,String _map) {
		super();
		// TODO Auto-generated constructor stub
		this._name = _name;
		this._type = _type;
		this._pk = _pk;
		this._seq = _seq;
		this._drop = _drop;
		this._constval = _constval;
		this._map = _map;
	}

	public String get_constval() {
		return _constval;
	}

	public void set_constval(String _constval) {
		this._constval = _constval;
	}

	public boolean is_drop() {
		return _drop;
	}

	public void set_drop(boolean _drop) {
		this._drop = _drop;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public boolean is_pk() {
		return _pk;
	}

	public void set_pk(boolean _pk) {
		this._pk = _pk;
	}

	public int get_type() {
		return _type;
	}

	public void set_type(int _type) {
		this._type = _type;
	}

	public String get_map() {
		return _map;
	}

	public void set_map(String _map) {
		this._map = _map;
	}

	public String get_seq() {
		return _seq;
	}

	public void set_seq(String _seq) {
		this._seq = _seq;
	}

            
    
}
