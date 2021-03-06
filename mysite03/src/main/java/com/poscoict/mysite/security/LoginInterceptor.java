package com.poscoict.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.mysite.service.UserService;
import com.poscoict.mysite.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");	
//		new UserService().getUser(email, password); => 이거 오류
//		autowired가 일어나지 않아서
//		new는 거의 안 함
		
		UserVo authUser = userService.getUser(email, password);
		if(authUser == null) {
			request.setAttribute("result", "fail");
			request.setAttribute("email", email);
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			return false;
		}
		
		// session 처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		System.out.println("@@@@@@@@@" + request.getContextPath());

		response.sendRedirect(request.getContextPath());		
		
		return false;
	}

}
