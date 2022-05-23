package com.example.demo.entity;

import java.util.HashMap;
import java.util.Map;

public class Cart {

	private Map<Integer, Items> itemlist = new HashMap<>();
	private int total;

	public Map<Integer, Items> getItems() {
		return itemlist;
	}

	public Cart() {

	}

	public int getTotal() {
		return total;
	}

	public void addCart(Items item, int quantity) {
		// itemsのidを取得してexistedItemに格納
		Items existedItem = itemlist.get(item.getId());
		// itemsが存在しないときはカートに追加
		if (existedItem == null) {
			// 今カートに入ってる数を取得
			item.setQuantity(quantity);
			itemlist.put(item.getId(), item);

		} else {
			// アイテムがexistedItemに存在する場合は数量のみ追加
			existedItem.setQuantity(existedItem.getQuantity() + quantity);

		}
		recalcTotal();
	}

	/**
	 * カートの削除処理
	 * 
	 * @param itemCode
	 */
	public void deleteCart(int itemCode) {
		// item.codeを使用してカート配列から削除
		itemlist.remove(itemCode);
		recalcTotal();
	}

	/**
	 * カートの中身の総計を算出
	 */
	public void recalcTotal() {
		total = 0;
		for (Items item : itemlist.values()) {
			total += item.getPrice() * item.getQuantity();
		}
	}

}
