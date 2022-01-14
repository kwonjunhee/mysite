package com.poscoict.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.GuestbookDao;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.web.mvc.Action;

public class deleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String no = request.getParameter("no");
		String password = request.getParameter("password");
		
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(Long.parseLong(no));
		vo.setPassword(password);
		
		new GuestbookDao().delete(vo);
		
		response.sendRedirect(request.getContextPath());

	}

}
