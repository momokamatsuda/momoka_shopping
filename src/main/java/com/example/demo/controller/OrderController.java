package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Pay;
import com.example.demo.entity.Users;
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
	@RequestMapping("/ordered")
	public ModelAndView ordered(ModelAndView mv) {
		mv.setViewName("ordered");
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
	private Cart getCartFromSession() {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);

		}
		return cart;
	}
	//注文画面で変更されたユーザー情報を更新
	@RequestMapping(value = "/orderComplete", method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(name = "name") String name, 
			@RequestParam(name = "email") String email,
			@RequestParam(name = "address") String address,
			@RequestParam(name = "tell") String tell, 
			@RequestParam(name = "creditNo") String creditNo,
			@RequestParam(name = "creditSecurity") Integer creditSecurity,
			ModelAndView mv) {
			
			// セッションからUser情報を取得
			Users user = (Users) session.getAttribute("user");
		
			// 入力された情報をUsersテーブルに更新(IDは設定)
			Users users = new Users(user.getId(), name, email, user.getPassword(), address, tell);
			usersRepository.saveAndFlush(users);
			session.setAttribute("user", users);
			
			
			// クレジットカード情報をDBに登録または更新？？？
//			List<Pay> payList = payRepository.findAll();
			Pay pay = new Pay(user.getId(), creditNo, creditSecurity);
			
			//
			// sessionにクレジットカードを入れる
			session.setAttribute("pay", pay);
			
			Cart cart =getCartFromSession();
			mv.addObject("items",cart.getItems());
			mv.addObject("total",cart.getTotal());
			mv.setViewName("orderComplete");
	

		return mv;
	}
}
