package com.poscoict.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.security.AuthUser;
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
	
	@Auth
	@RequestMapping("/view/{no}")
	public String view(@AuthUser UserVo authUser, Model model,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@PathVariable("no") Long no) {
		
		model.addAttribute("boardvo", boardservice.getContents(no));
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write () {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write (@AuthUser UserVo authUser, BoardVo boardvo) {
		boardservice.addContents(boardvo);
		boardvo.setUserNo(authUser.getNo());
		boardvo.setUserName(authUser.getName());
		System.out.println(boardvo);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete (
			@AuthUser UserVo authUser,
			@PathVariable("no") Long boardNo) {
		System.out.println(authUser);
		Long authNo = authUser.getNo();
		boardservice.deleteContents(boardNo, authNo);
		System.out.println(boardNo);
		return "redirect:/board";
	}
	@Auth
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			Model model, @PathVariable("no") Long no) {
		System.out.println(no);
		model.addAttribute("boardvo", boardservice.getContents(no));
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify (BoardVo boardvo, @AuthUser UserVo authUser,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="page", required=true, defaultValue="1") int page) {
		boardservice.updateContents(boardvo);
		
		return "redirect:/board?page=" + page + "&kwd=" + WebUtil.encodeURL(kwd, "UTF-8");
		
	}
	
	@Auth
	@RequestMapping(value="/reply/{no}", method=RequestMethod.GET)
	public String reply (@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model,
			@RequestParam(value="g", required=true, defaultValue="1") int groupNo,
			@RequestParam(value="o", required=true, defaultValue="1") int orderNo,
			@RequestParam(value="d", required=true, defaultValue="1") int depth
			) {
		
		BoardVo boardvo = boardservice.getContents(no);
		System.out.println("boardvo controller"+boardvo);

		boardvo.setOrderNo(boardvo.getOrderNo() + 1);
		boardvo.setDepth(boardvo.getDepth() + 1);
		
		model.addAttribute("boardvo", boardvo);

		return "board/reply";
	}
	

	
	
}
