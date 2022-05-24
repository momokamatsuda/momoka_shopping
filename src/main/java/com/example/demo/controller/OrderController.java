package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Items;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Ordered;
import com.example.demo.entity.Users;
import com.example.demo.repositoy.OrderDetailRepository;
import com.example.demo.repositoy.OrderedRepository;
import com.example.demo.repositoy.PayRepository;
import com.example.demo.repositoy.UsersRepository;

@Controller
public class OrderController {
	@Autowired
	HttpSession session;
	@Autowired
	UsersRepository usersRepository;

	@Autowired
	PayRepository payRepository;
	
	@Autowired
	OrderedRepository orderedRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;

	@RequestMapping("/orderItem")
	public ModelAndView orderItem(ModelAndView mv) {
		// カート情報を取得
		Cart cart = getCart();
		// 表示に必要な情報を入力する
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());

		// クレジット情報をDBから取得

		// お客様情報入力画面
		mv.setViewName("orderInfo");
		return mv;
	}

	@RequestMapping("/orderComplete")
	public ModelAndView orderComplete(ModelAndView mv) {
		Cart cart = getCartFromSession();
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		mv.setViewName("orderComplete");
		return mv;
	}

	public Cart getCart() {

		// セッションのカート情報を取得
		Cart cart = (Cart) session.getAttribute("cart");
		// カート情報がない場合、カート情報の初期処理
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	//注文する
	//<form action="/order/doOrder" method ="post">
	@RequestMapping(value = "/order/doOrder", method = RequestMethod.POST)
	public ModelAndView ordered(

			ModelAndView mv) {
		Users user = (Users) session.getAttribute("user");
		//注文情報をDBに格納する
		Cart cart = getCartFromSession();
		//登録処理
		Ordered order = new Ordered(
				user.getId(),
				new Date(),
				cart.getTotal()
				);
		int orderCode = orderedRepository.saveAndFlush(order).getId();
		
		//注文詳細情報をDBに格納する
		Map<Integer,Items> items =cart.getItems();
		List<OrderDetail> orderDetails = new ArrayList<>();
		for (Items item:items.values()) {
			orderDetails.add(new OrderDetail(orderCode,item));
		}
		orderDetailRepository.saveAll(orderDetails);
		//セッションスコープのカート情報をクリアする
		session.setAttribute("cart", new Cart());
		//画面返却用注文番号を設定する
		mv.addObject("orderNumber",orderCode);
		mv.setViewName("ordered");
		return mv;
	}
	
	//セッションスコープからカート情報を取得
	//カートが存在しない場合は、セッションスコープに追加した
	//上で空のカート情報を返却
	private Cart getCartFromSession() {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);

		}
		return cart;
	}

}
