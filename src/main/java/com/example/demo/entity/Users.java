package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name = "users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "tell")
	private String tell;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = " password")
	private String  password;
	
	public Users() {
		
	}
	public Users(String name,String email,
			String  password,String address,
			String tell) {
		super();
	
		this.address=address;
		this.email=email;
		this.tell=tell;
		this.name=name;
		this.password=password;
		
	}
	public Users(Integer id, String name,String email,
			String  password,String address,
			String tell) {
		super();
		this.id = id;
		this.address=address;
		this.email=email;
		this.tell=tell;
		this.name=name;
		this.password=password;
		
	}
	public Integer getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getTell() {
		return tell;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

}
