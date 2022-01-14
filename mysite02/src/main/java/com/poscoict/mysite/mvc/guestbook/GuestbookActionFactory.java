package com.poscoict.mysite.mvc.guestbook;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;

public class GuestbookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		System.out.println("팩토리임"+actionName);
		if("deleteform".equals(actionName)) {
			action = new deleteformAction();
		} else if("delete".equals(actionName)) {
			action = new deleteAction();
		} else if("add".equals(actionName)) {
			action = new addAction();
		} else {

			action = new IndexAction();
		}
		return action;
	}

}
