package com.poscoict.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.service.FileUploadService;
import com.poscoict.mysite.service.SiteService;
import com.poscoict.mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private SiteService siteservice;
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping({"", "main"})
	public String main(Model model) {
		model.addAttribute("siteVo", siteservice.findSite());
		return "admin/main";
	}
	
	@RequestMapping(value="/main/update", method=RequestMethod.POST)
	public String update(SiteVo siteVo, Model model,
			@RequestParam(value="file1") MultipartFile multipartFile) {
		String profile = fileUploadService.restore(multipartFile);
		siteVo.setProfile(profile);
		
		siteservice.updateSite(siteVo);
		model.addAttribute("siteVo", siteservice.findSite());
		System.out.println(siteVo);
		return "redirect:/admin";
	}
	

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
	
}
