package com.poscoict.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookservice;
	
	@RequestMapping({"", "index"})
	public String index(Model model) {
		model.addAttribute("list", guestbookservice.getMessageList());
		return "guestbook/index";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(GuestbookVo guestbookvo) {
		System.out.println("guestbookvo: "+guestbookvo);
		guestbookservice.addMessage(guestbookvo);
		System.out.println("guestbookvo: "+guestbookvo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete() {
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(
			@RequestParam(value="no", required=true, defaultValue="") Long no,
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		guestbookservice.deleteMessage(no, password);
		return "redirect:/guestbook";
	}
	
}
