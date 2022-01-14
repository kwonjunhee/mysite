<%@page import="com.poscoict.mysite.dao.GuestbookDao"%>
<%@page import="com.poscoict.mysite.vo.GuestbookVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String name = request.getParameter("name");
	String password = request.getParameter("password");
	String message = request.getParameter("content");
	
	GuestbookVo vo = new GuestbookVo();
	vo.setName(name);
	vo.setPassword(password);
	vo.setMessage(message);
	
	new GuestbookDao().insert(vo);
	
	response.sendRedirect(request.getContextPath());
%>