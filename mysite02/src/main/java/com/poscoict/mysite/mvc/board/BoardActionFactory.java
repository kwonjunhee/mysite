package com.poscoict.mysite.mvc.board;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		System.out.println("factory: " +actionName);
		if("writeform".equals(actionName)) {
			action = new writeFormAction();
		} else if("write".equals(actionName)) {
			action = new writeAction();
		} else if("view".equals(actionName)) {
			action = new viewAction();
		} else if("modifyform".equals(actionName)) {
			action = new modifyFormAction();
		} else if("modify".equals(actionName)) {
			action = new modifyAction();
		} else if("delete".equals(actionName)) {
			action = new deleteAction();
		} else {
			action = new ListAction();
		}
		return action;
	}

}
