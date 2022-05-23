package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pay")
public class Pay {
	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "user_id")
	private Integer userId;//顧客番号
	@Column(name = "credit_no")
	private String creditNo;
	@Column(name = "credit_security")
	private Integer creditSecurity;

	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getCreditNo() {
		return creditNo;
	}

	public Integer getCreditSecurity() {
		return creditSecurity;
	}

	public Pay() {
		super();
	}

	public Pay(Integer userId, String creditNo, Integer creditSecurity) {
		super();
		this.userId = userId;
		this.creditNo = creditNo;
		this.creditSecurity = creditSecurity;
	}
}
