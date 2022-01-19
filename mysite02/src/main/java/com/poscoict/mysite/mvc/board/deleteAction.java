package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class deleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);
			return;
		}
		Long no = Long.parseLong(request.getParameter("no")); 
		Long userNo = Long.parseLong(request.getParameter("userNo"));
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setUserNo(userNo);
		if(userNo != authUser.getNo()) {
			MvcUtil.redirect(request.getContextPath() + "/board", request, response);
			return;
		}
		new BoardDao().delete(vo);
		
		response.sendRedirect(request.getContextPath());

	}

}
