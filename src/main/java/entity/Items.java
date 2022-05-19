package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Items {
	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "price")
	private Integer price;
	@Column(name = "stock")
	private Integer stock;
	@Column(name = "image")
	private Integer image;
	@Column(name = "name")
	private Integer name;
	@Column(name = "delivery_days")
	private Integer deliveryDays;

	public Integer getId() {
		return id;
	}

	public Integer getPrice() {
		return price;
	}

	public Integer getStock() {
		return stock;
	}

	public Integer getImage() {
		return image;
	}

	public Integer getName() {
		return name;
	}

	public Integer getDeliveryDays() {
		return deliveryDays;
	}

}
