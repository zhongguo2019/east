package com.krm.ps.model.datavalidation.bo;

public class ResultEntity {
	private String rulecode;
	private String value;
	private String rulemsg;
	private String range;

	public String getRulecode() {
		return rulecode;
	}

	public void setRulecode(String rulecode) {
		this.rulecode = rulecode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRulemsg() {
		return rulemsg;
	}

	public void setRulemsg(String rulemsg) {
		this.rulemsg = rulemsg;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

}
