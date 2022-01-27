package com.poscoict.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.poscoict.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// TODO Auto-generated method stub
		if(!supportsParameter(parameter)) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser 가 안 붙어있음
		if(authUser == null) {
			return false;
		}

		// parameter type이 UserVo가 아님
		if (parameter.getParameterType().equals(UserVo.class) == false) {
			 return false;
		 }
		
		return true;
		
	}
}
