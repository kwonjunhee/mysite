package com.poscoict.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.GuestbookDao;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.web.mvc.Action;

public class addAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	}

}
