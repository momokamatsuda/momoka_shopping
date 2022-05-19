
package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "ordered_id")
	private Integer orderedId;

	@Column(name = "item_id")
	private Integer itemId;

	@Column(name = "quantity")
	private Integer quantity;

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
