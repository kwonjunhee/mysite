package com.poscoict.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.mysite.service.SiteService;
import com.poscoict.mysite.vo.SiteVo;

public class SiteInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private SiteService siteService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		
		SiteVo siteVo = (SiteVo) request.getServletContext().getAttribute("SiteVo");
		if(siteVo == null || siteVo.equals(null)) {
			request.setAttribute("siteVo", siteService.findSite());
			return true;
		}
		System.out.println("siteVo"+siteVo);
		request.setAttribute("siteVo", siteVo);
		// 서블릿컨텍스트에 site 저장
		return true;
	}

}
