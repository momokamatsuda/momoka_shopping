package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Items;
import com.example.demo.repositoy.ItemsRepository;
import com.example.demo.repositoy.UsersRepository;

@Controller
public class CartController {

	@Autowired
	HttpSession session;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	ItemsRepository itemsRepository;

	// カート内容を表示
	@RequestMapping("/cart")
	public ModelAndView cartList(ModelAndView mv) {
		Cart cart = getCartFromSession();
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		mv.setViewName("cart");
		return mv;
	}

	// カートに追加
	// <a th:href="|/cart/add/${item.id}|">カートに追加</a>
	@RequestMapping("/cart/add/{itemId}")
	public ModelAndView addCart(@PathVariable(name = "itemId") int itemId, ModelAndView mv) {
		Cart cart = getCartFromSession();
		// 商品コードを元にアイテム情報を取得
		Items item = itemsRepository.getById(itemId);
		// カート情報にアイテム情報を追加
		cart.addCart(item, 1);
		// ページに表示したい情報を設定
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		// カートページに遷移
		mv.setViewName("cart");
		return mv;
	}

	// カートの中身の削除処理
	@RequestMapping("/cart/delete/{id}")
	public ModelAndView deleteCart(@PathVariable("id") int id, ModelAndView mv) {
		// カートの情報を取得
		// カート情報が取れない場合、初期処理
		// メソッドgetCart()を使用
		Cart cart = getCartFromSession();
		// カートの中からcodeが一致するアイテムを削除
		cart.deleteCart(id);
		// ページ表示に必要なデータを設定
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		// カートの中身を表示するページを遷移
		mv.setViewName("cart");
		return mv;
	}

	private Cart getCartFromSession() {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);

		}
		return cart;
	}
}
