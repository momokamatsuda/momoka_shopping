package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Users;
import com.example.demo.repositoy.UsersRepository;


@Controller
public class AccountController {
	@Autowired
	HttpSession session;
	
	@Autowired
	UsersRepository usersRepository;
	
	@RequestMapping("/")
	public String login() {
		//セッション情報の削除
		session.invalidate();
		return "login";
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(name = "email") String email, 
			@RequestParam(name = "password") String password, 
			ModelAndView mv) {
		// 未入力チェック
		if (email== null || email.length() == 0) {
			// 未入力の場合はログイン画面に戻る
			mv.addObject("message", "未入力項目があります");
			mv.setViewName("login");
			return mv;
		}
			
		//名前を追加
		//名前をセッション(ログインからログ	アウトまでに保存）にいれる
		session.setAttribute("email", email);
		mv.addObject("email",email);
		mv.addObject("password",password);	
		//top.htmlに遷移
		mv.setViewName("top");
		return mv;
	}
	//"/signup"新規会員登録はこちらのリンクを押すと遷移
	@RequestMapping("/signup")
	public String signup() {
		
		return "signup";
	}
	
	@RequestMapping(value ="/signup",method = RequestMethod.POST )
	public ModelAndView login(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "address") String address,
			@RequestParam(name = "tell") String tell,
			ModelAndView mv) {
		//未入力チェック
		if(isNull(name) || isNull(email) || isNull(password) || isNull(address) || isNull(tell)){
			mv.addObject("message", "未入力項目があります");
			mv.setViewName("signup");
		}else {
			// 入力された情報をUsersテーブルに登録
			Users users = new Users(name,email,password,address,tell);
			usersRepository.saveAndFlush(users);
			mv.setViewName("login");
		}
		
		
		
			return mv;
	}
	
	public boolean isNull(String text) {
		if(text==null) {
			return true;
		}else if(text.length()==0) {
			return true;
		}else {
			return false;
		}
	}
	
}