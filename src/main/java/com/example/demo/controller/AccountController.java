package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Items;
import com.example.demo.entity.Ordered;
import com.example.demo.entity.Pay;
import com.example.demo.entity.Users;
import com.example.demo.repositoy.ItemsRepository;
import com.example.demo.repositoy.OrderedRepository;
import com.example.demo.repositoy.UsersRepository;

@Controller
public class AccountController {
	@Autowired
	HttpSession session;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	ItemsRepository itemsRepository;

	@Autowired
	OrderedRepository orderedRepository;

	@RequestMapping("/")
	public String login() {
		// セッション情報の削除
		session.invalidate();
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password, ModelAndView mv) {
		// 未入力チェック
		if (email == null || email.length() == 0) {
			// 未入力の場合はログイン画面に戻る
			mv.addObject("message", "未入力項目があります");
			mv.setViewName("login");
			return mv;
		}
		// DBの「users」に入っているデータ(emailとpassword)を変数名「usersList」に格納
		List<Users> usersList = usersRepository.findAllByEmailAndPassword(email, password);
		//
		if (usersList.size() != 0) {
			// ユーザー情報をセッション(ログインからログ アウトまでに保存）にいれる
			session.setAttribute("user", usersList.get(0));
			// DBに保存されているユーザの名前をnameに格納（session.nameで呼び出す）
//			session.setAttribute("name", usersList.get(0).getName());
//			session.setAttribute("adress", usersList.get(0).getAddressd());
//			session.setAttribute("email", usersList.get(0).getEmail());
//			session.setAttribute("tell", usersList.get(0).getTell());
			// top.htmlに遷移
			List<Items> itemList = itemsRepository.findAll();
			mv.addObject("items", itemList);
			mv.setViewName("top");
		} else {
			//// 入力された「email」「password」がusersListの中になかったらメッセージを表示
			mv.addObject("message", "会員情報が登録されていません");

			mv.addObject("email", email);
			mv.addObject("password", password);
			mv.setViewName("login");
		}
		return mv;
	}

	// "/signup"新規会員登録はこちらのリンクを押すと遷移
	@RequestMapping("/signup")
	public String signup() {

		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam(name = "name") String name, @RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password, @RequestParam(name = "address") String address,
			@RequestParam(name = "tell") String tell, ModelAndView mv) {
		// 未入力チェック
		if (isNull(name) || isNull(email) || isNull(password) || isNull(address) || isNull(tell)) {
			mv.addObject("message", "未入力項目があります");
			mv.setViewName("signup");
		} else {
			// 重複チェック
			// ①DBから同一のNameを持つデータを取得
			// ②データが取得できたら登録しない
			List<Users> userList = usersRepository.findAllByEmail(email);
			mv.addObject("users", userList);
			
			if(userList.size() != 0) {
				mv.addObject("message", "登録済のユーザーです");
				mv.setViewName("signup");
			}
			else {
				
				// 入力された情報をUsersテーブルに登録
				Users users = new Users(name, email, password, address, tell);
				usersRepository.saveAndFlush(users);
				mv.setViewName("login");
			}
			
		}

		return mv;
	}

	public boolean isNull(String text) {
		if (text == null) {
			return true;
		} else if (text.length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	// 注文画面で変更されたユーザー情報を更新
	@RequestMapping(value = "/orderComplete", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam(name = "name") String name, @RequestParam(name = "email") String email,
			@RequestParam(name = "address") String address, @RequestParam(name = "tell") String tell,
			@RequestParam(name = "creditNo") String creditNo,
			@RequestParam(name = "creditSecurity", defaultValue = "0") Integer creditSecurity, ModelAndView mv) {

		// セッションからUser情報を取得
		Users user = (Users) session.getAttribute("user");
		if (isNull(name) || isNull(email) || isNull(address) || isNull(tell) || isNull(creditNo)
				|| creditSecurity == 0) {
			mv.addObject("message", "未入力項目があります");

			// 更新画面を再表示
			mv.addObject("userInfo", session.getAttribute("userInfo"));
			mv.setViewName("orderInfo");
		} else {
			// 入力された情報をUsersテーブルに更新(IDは設定)
			Users users = new Users(user.getId(), name, email, user.getPassword(), address, tell);
			usersRepository.saveAndFlush(users);
			session.setAttribute("user", users);

			// クレジットカード情報をDBに登録または更新？？？
			// List<Pay> payList = payRepository.findAll();
			Pay pay = new Pay(user.getId(), creditNo, creditSecurity);

			//
			// sessionにクレジットカードを入れる
			session.setAttribute("pay", pay);

			Cart cart = getCartFromSession();
			mv.addObject("items", cart.getItems());
			mv.addObject("total", cart.getTotal());
			mv.setViewName("orderComplete");
		}
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

	// マイページの表示
	@RequestMapping("/mypage")
	public ModelAndView mypage(ModelAndView mv) {
		// セッションからUser情報を取得
//		Users user = (Users) session.getAttribute("user");
//		Users users = new Users(user.getName(), user.getEmail(), user.getPassword(), user.getAddress(), user.getTell());
//		session.setAttribute("user", users);
		mv.setViewName("mypage");
		return mv;
	}

	// 登録情報変更変更ページに遷移
	@RequestMapping("/editUser")
	public ModelAndView editUserPage(ModelAndView mv) {
		mv.setViewName("editUser");
		return mv;
	}

	// 登録情報変更
	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public ModelAndView editUser(@RequestParam("name") String name, @RequestParam("address") String address,
			@RequestParam("tell") String tell, @RequestParam("email") String email,
			@RequestParam("password") String password, ModelAndView mv) {

		// 未入力チェック
		if (isNull(name) || isNull(email) || isNull(password) || isNull(address) || isNull(tell)) {
			mv.addObject("message", "未入力項目があります");

			// 更新画面を再表示
			mv.addObject("userInfo", session.getAttribute("userInfo"));
			mv.setViewName("editUser");
		} else {

			// セッションからUser情報を取得
			Users user = (Users) session.getAttribute("user");

			// 入力された情報をUsersテーブルに更新(IDは設定)
			Users users = new Users(user.getId(), name, email, password, address, tell);
			usersRepository.saveAndFlush(users);
			session.setAttribute("user", users);
			mv.setViewName("mypage");
		}
		return mv;
	}

	@RequestMapping(value = "/history")
	public ModelAndView history(ModelAndView mv) {
		// セッションからUser情報を取得
		Users user = (Users) session.getAttribute("user");

		List<Ordered> orders = orderedRepository.findAllByUserId(user.getId());
		mv.addObject("orders", orders);
		mv.setViewName("history");
		return mv;
	}
	//退会
//	<form action="withdrawal">
	@RequestMapping(value = "/withdrawal")
	public ModelAndView withdrawal(
			ModelAndView mv) {
		// セッションからUser情報を取得
		Users user = (Users) session.getAttribute("user");
		// idを基にデータを削除
		usersRepository.deleteById(user.getId());
		// 削除を確定
		usersRepository.flush();
		mv.setViewName("login");
		return mv;
	}

}
