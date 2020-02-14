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

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long no = Long.parseLong(request.getParameter("no"));
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		Long userNo = userVo.getNo();
		
		BoardVo vo = new BoardRepository().findByNo(no);
		
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(userNo);
		
		new BoardRepository().updateReply(vo);
		new BoardRepository().insertReply(vo);

		WebUtil.redirect(request.getContextPath() + "/board", request, response);
	}

}
