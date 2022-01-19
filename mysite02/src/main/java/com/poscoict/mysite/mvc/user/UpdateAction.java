package com.poscoict.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.UserDao;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 접근 제어
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);
			return;
		}
		Long no = Long.parseLong(request.getParameter("no"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		if(password.isEmpty()) {
			password=request.getParameter("old_password");
		}
		UserVo vo = new UserVo();
		
		vo.setNo(no);
		vo.setName(name);
		vo.setPassword(password);
		vo.setEmail(email);
		vo.setGender(gender);
		new UserDao().update(vo);

		
		authUser = new UserDao().findByNo(no);
		session.setAttribute("authUser", authUser);

		MvcUtil.redirect(request.getContextPath()+"/user", request, response);

	}

}
