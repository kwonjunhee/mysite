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

public class writeAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 접근 제한도 해주기
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);
			return;
		}
		
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		Long userNo = Long.parseLong(request.getParameter("userNo"));
		String userName = request.getParameter("userName");
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserName(userName);
		vo.setUserNo(userNo);
		
		new BoardDao().insert(vo);
		MvcUtil.redirect(request.getContextPath()+"/board", request, response);

	}

}
