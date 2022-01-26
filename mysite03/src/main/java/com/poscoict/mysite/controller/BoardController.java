package com.poscoict.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.mysite.web.util.WebUtil;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardservice;

	@RequestMapping({"", "list"})
	public String list(Model model,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="page", required=true, defaultValue="1") int page) {
		
		model.addAttribute("list", boardservice.getContentsList(page, kwd).get("list"));
		model.addAttribute("page", boardservice.getContentsList(page, kwd));
		return "board/list";
	}
	
	@RequestMapping("/view/{no}")
	public String view(HttpSession session, Model model,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@PathVariable("no") Long no) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		model.addAttribute("boardvo", boardservice.getContents(no));
		return "board/view";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write (HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write (HttpSession session
			, @ModelAttribute BoardVo boardvo) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		boardvo.setUserNo(authUser.getNo());
		boardvo.setUserName(authUser.getName());
		boardservice.addContents(boardvo);
		return "redirect:/board";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete (HttpSession session,@PathVariable("no") Long no) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		boardservice.deleteContents(no,authUser.getNo());
		
		return "redirect:/board";
	}
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modify( HttpSession session,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			Model model, @PathVariable("no") Long no) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		model.addAttribute("boardvo", boardservice.getContents(no, authUser.getNo()));
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify (BoardVo boardvo,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="page", required=true, defaultValue="1") int page) {
		boardservice.updateContents(boardvo);
		
		return "redirect:/board?page=" + page + "&kwd=" + WebUtil.encodeURL(kwd, "UTF-8");
		
	}
	
	@RequestMapping(value="/reply/{no}", method=RequestMethod.GET)
	public String reply (HttpSession session, @PathVariable("no") Long no, Model model) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		BoardVo boardvo = boardservice.getContents(no);
		System.out.println(boardvo);
		boardvo.setOrderNo(boardvo.getOrderNo() + 1);
		boardvo.setDepth(boardvo.getDepth() + 1);

		model.addAttribute("boardvo", boardvo);

		return "board/reply";
	}
	

	
	
}
