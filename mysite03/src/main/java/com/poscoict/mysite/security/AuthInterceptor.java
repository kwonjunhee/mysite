package com.poscoict.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			return true;
		} // 이렇게 하면 spring-servlet에서 <mvc:exclude-mapping path="/assets/**"/> 얘 빼도 됨
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. Handler Method의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4-1. Handler Method @Auth가 없다면 Type에 있는 지 확인 ( 과제
		if(auth == null) {
			
		}
		
		
		// 4-2. Type과 Method에 @Auth가 적용 안 되어있는 경우
		if(auth == null) {
			return true;
		}
		
		
		// 5. @Auth가 적용되어 있으므로 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		if(session == null ) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null ) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		// 6. 인증 확인 -> controller의 handler(method) 실행 
		return true;
	}

}
