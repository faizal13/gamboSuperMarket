package com.gamboSupermarket.application.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.gamboSupermarket.application.utils.Utility;

@MappedSuperclass
public class BaseModel {

	@Column(name = "created_by")
	private String created_by ;

	@Column(name = "modified_on")
	private Timestamp modified_on;

	@Column(name = "created_on")
	private Timestamp created_on = new Timestamp(System.currentTimeMillis());

	@Column(name = "modified_by")
	private String modified_by;

	public String getCreated_by() {
		return created_by;
	}

	public Timestamp getModified_on() {
		return modified_on;
	}

	public void setModified_on(Timestamp modified_on) {
		this.modified_on = modified_on;
	}

	public Timestamp getCreated_on() {
		return created_on;
	}

	public String getModified_by() {
		return modified_by;
	}

}
