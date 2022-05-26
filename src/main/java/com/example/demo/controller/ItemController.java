package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Items;
import com.example.demo.repositoy.ItemsRepository;

@Controller
public class ItemController {
	@Autowired
	HttpSession session;

	@Autowired
	ItemsRepository itemsRepository;

	// 検索
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(ModelAndView mv, @RequestParam("searchWord") String searchWord) {
		List<Items> itemList = itemsRepository.findAllByNameContaining(searchWord);
		if(itemList.size()==0) {
			mv.addObject("message", "検索ワードを含む商品がありません");
			mv.setViewName("top");
		}else {
		mv.addObject("items", itemList);
		mv.setViewName("top");
		}
		return mv;
	}

	// トップページに全商品を表示する
	@RequestMapping(value = "/items")
	public ModelAndView items(ModelAndView mv) {
		mv.addObject("items", itemsRepository.findAll());
		// itemsの要素をすべてitemListに格納する
		List<Items> itemList = itemsRepository.findAll();
		// itemListを変数名itemsに格納
		mv.addObject("items", itemList);

		mv.setViewName("top");
		return mv;
	}

}
