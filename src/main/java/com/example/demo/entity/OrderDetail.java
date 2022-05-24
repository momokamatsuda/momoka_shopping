
package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "ordered_id")
	private Integer orderedId;

	@Column(name = "item_id")
	private Integer itemId;

	@Column(name = "quantity")
	private Integer quantity;
	
	public OrderDetail() {
		super();
	}
	public OrderDetail(Integer id,Integer orderedId,Integer itemId,Integer quantity) {
		this(orderedId,itemId,quantity);
		this.id=id;
	}
	public OrderDetail(Integer orderedId,Integer itemId,Integer quantity) {
	super();
	this.orderedId=orderedId;
	this.itemId=itemId;
	this.quantity=quantity;
	}
	public OrderDetail(Integer orderedId,Items item) {
		this(orderedId,item.getId(),item.getQuantity());
	}
	public Integer getId() {
		return id;
	}

	public Integer getOrderedId() {
		return orderedId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

}
