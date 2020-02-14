package com.douzone.mysite.action.board;

import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		switch(actionName) {
			case "view" : return new ViewAction();
			case "writeform" : return new WriteFormAction();
			case "insert" : return new InsertAction();
			case "replyform" : return new ReplyFormAction();
			case "reply" : return new ReplyAction();
			case "modifyform" : return new ModifyFormAction();
			case "modify" : return new ModifyAction();
			case "delete" : return new DeleteAction();
			case "find" : return new FindAction();
			default : return new ListAction();
		}
	}

}
