package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_detail")
public class Users {
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "address")
	private String iaddressd;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "tell")
	private String tell;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = " password")
	private String  password;
	public Integer getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getIaddressd() {
		return iaddressd;
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
