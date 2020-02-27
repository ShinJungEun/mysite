package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class FindAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String kwd = request.getParameter("kwd");
		List<BoardVo> list = new BoardRepository().searchList(kwd);
		
		request.setAttribute("list", list);

		
		int listLen = list.size();
		double pageLen = Math.ceil((double)listLen/5);
		
		// page 개수
		request.setAttribute("pageLen", (int)pageLen);
		
		String page = request.getParameter("page");
		if(page == null)
			request.setAttribute("page", 1);
		else
			request.setAttribute("page", page);
		
		request.setAttribute("kwd", kwd);

		HttpSession session = request.getSession(true);
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		request.setAttribute("userVo", userVo);
		
		WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
	}

}
