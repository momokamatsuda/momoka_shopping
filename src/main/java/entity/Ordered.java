package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ordered")
public class Ordered {
	@Id
	@Column(name = "id")
	private  Integer id;
	
	@Column(name = "user_id")
	private  Integer userId;
	
	@Column(name = "ordered_date")
	private  Date orderedDate;
	
	@Column(name = "total_price")
	private  Integer totalPrice;
	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public Date getOrderedDate() {
		return orderedDate;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	

}
