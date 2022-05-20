package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "items")
public class Items {
	@Id
	//新規登録に必要
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "price")
	private Integer price;
	@Column(name = "stock")
	private Integer stock;
	@Column(name = "image")
	private String image;
	@Column(name = "name")
	private String name;
	@Column(name = "delivery_days")
	private Integer deliveryDays;
	@Transient
	private Integer quantity;
	public Items() {
		super();
	}
	public Items(Integer id,Integer price, Integer stock,String image,String name,Integer deliveryDays) {
		super();
		this.id=id;
		this.price=price;
		this.stock=stock;
		this.image=image;
		this.name=name;
		this.deliveryDays=deliveryDays;
	}
	//アイテムに新規追加するときに使うコンストラクタ
		public Items(Integer id, String name, Integer price) {
			super();

			this.id = id;
			this.name = name;
			this.price = price;
		}
	
	public Integer getId() {
		return id;
	}

	public Integer getPrice() {
		return price;
	}

	public Integer getStock() {
		return stock;
	}

	public String getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public Integer getDeliveryDays() {
		return deliveryDays;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity=quantity;
	}
}
