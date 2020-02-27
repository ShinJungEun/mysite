package com.douzone.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Auth
@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value= {"", "/list"}, method = {RequestMethod.GET, RequestMethod.POST}) 
	public String index(@AuthUser UserVo authUser,
			Model model,	
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="keyword", required=true, defaultValue="")String keyword) {

		Map<String, Object> map = new HashMap<>();
		map = boardService.mapContents(page, keyword);
		model.addAllAttributes(map);

		return "board/list";
	}

	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}

	@RequestMapping(value="/reply/{no}")
	public String reply(@AuthUser UserVo authUser,
			@PathVariable("no") Long no, Model model) {
		
		return "board/write";
	}

	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@AuthUser UserVo authUser,
			@ModelAttribute BoardVo boardVo,
			@RequestParam(value="no", required=true, defaultValue="0") Long no) {
		Long userNo = authUser.getNo();
		boardVo.setUserNo(userNo);

		// 새글 작성
		if(no == 0) {
			// 새글이라는 것을 세팅해줌
			boardVo.setDepth(-1);
			boardService.insertContents(boardVo);
		}
		// 답글 작성
		else {
			// 부모 vo 가져오기
			BoardVo vo = boardService.getParentContents(no);

			vo.setUserNo(userNo);
			vo.setTitle(boardVo.getTitle());
			vo.setContents(boardVo.getContents());

			boardService.updateReplyContents(vo);
			boardService.insertReplyContents(vo);
		}
		return "redirect:/board";
	}
	

	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(@AuthUser UserVo authUser,
			@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.findBoardVoContents(no);
		model.addAttribute("boardVo", boardVo);

		boardService.hitContents(boardVo);

		model.addAttribute("userVo", authUser);
		return "board/view";	
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.findBoardVoContents(no);
		model.addAttribute("boardVo", boardVo);
		
		return "board/modify";
	}
	
	
	@Auth	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.POST)
	public String modify(
			@AuthUser UserVo authUser,
			@PathVariable("no") Long no,
			@ModelAttribute BoardVo boardVo) {
		System.out.println("no:"+no);
		boardVo.setNo(no);
	
		boardService.modifyContents(boardVo);
		
		
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(
			HttpSession session,
			@PathVariable("no") Long no,
			Model model) {
		
		boardService.deleteContents(no);
		
		return "redirect:/board";
	}

}
