package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class ReplyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		int gNo = Integer.parseInt(request.getParameter("gNo"));
//		int oNo = Integer.parseInt(request.getParameter("oNo"));
//		int depth = Integer.parseInt(request.getParameter("dep"));

		Long no = Long.parseLong(request.getParameter("no"));
		request.setAttribute("no", no);
		
		request.setAttribute("reply", "ok");
		
		WebUtil.forward("/WEB-INF/views/board/write.jsp", request, response);

	}

}
