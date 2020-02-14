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

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		List<BoardVo> list = new BoardRepository().findList();
		
		request.setAttribute("list", list);
		
		HttpSession session = request.getSession(true);
		

		request.setAttribute("page", 1);
		
		int listLen = list.size();
		double pageLen = Math.ceil((double)listLen/5);
		
		// page 개수
		request.setAttribute("pageLen", (int)pageLen);
		
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		request.setAttribute("userVo", userVo);
		
		WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
	}

}
