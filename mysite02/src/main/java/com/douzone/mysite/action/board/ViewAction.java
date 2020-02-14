package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Long no = Long.parseLong(request.getParameter("no"));
		
		BoardVo boardVo = new BoardRepository().findByNo(no);
		request.setAttribute("vo", boardVo);
		
		new BoardRepository().updateHit(boardVo);
		
		
		HttpSession session = request.getSession(true);
		
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		
		request.setAttribute("userVo", userVo);
		
		WebUtil.forward("/WEB-INF/views/board/view.jsp", request, response);
	}

}
