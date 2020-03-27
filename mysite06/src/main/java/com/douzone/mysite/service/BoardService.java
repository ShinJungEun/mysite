package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
		
//	public void listContents(Model model) {
//		List<BoardVo> list = boardRepository.findAll();
//		int listLen = list.size();
//		double pageLen = Math.ceil((double)listLen/5);
//	
//		model.addAttribute("list", list);
//		model.addAttribute("pageLen", pageLen);
//	}
	
	public boolean insertContents(BoardVo vo) {
		int count = boardRepository.insert(vo);
		return count == 1;
	}
	
	public boolean hitContents(BoardVo vo) {
		int count = boardRepository.updateHit(vo);
		return count == 1;
	}

	public BoardVo findBoardVoContents(Long no) {
		return boardRepository.findByNo(no);
	}

	public boolean updateReplyContents(BoardVo vo) {
		int count = boardRepository.updateReply(vo);
		return count == 1;
	}

	public boolean insertReplyContents(BoardVo vo) {
		int count = boardRepository.insertReply(vo);
		return count == 1;	
	}

	public BoardVo getParentContents(Long no) {
		return boardRepository.findByNo(no);
	}

	public boolean modifyContents(BoardVo vo) {
		int count = boardRepository.update(vo);
		return count == 1;
	}
	
	public boolean deleteContents(Long no) {
		int count = boardRepository.delete(no);
		return count == 1;
	}
	
	public Map<String, Object> mapContents(int page, String keyword) {
		Map<String, Object> map = new HashMap<>();
		
		// 리스트
		List<BoardVo> list = boardRepository.findAll(page, keyword);
		
		map.put("list", list);
		
		// 검색 키워드
		map.put("keyword", keyword);
		
		// 현재 페이지
		map.put("page", page);
		
		// 페이지 길이
		int listLen = boardRepository.listLen(keyword);
		int pageLen = (int)(Math.ceil((double)listLen/5));
		map.put("pageLen", pageLen);
		
		// 시작 페이지
		int firstPageNo = 1;
		if(page % 5 == 0) {
			firstPageNo = ((page/5)-1) * 5 + 1;
		}
		else {
			firstPageNo = (page/5) * 5 + 1;
		}
		map.put("firstPageNo", firstPageNo);
		
		// 끝 페이지
		int lastPageNo = 1;
		if(pageLen < (firstPageNo + 4)) {
			lastPageNo = pageLen;
		}
		else {
			lastPageNo = firstPageNo + 4;
		}
		map.put("lastPageNo", lastPageNo);
		
		// 시작 글번호
		int startIndex = (page - 1)*5;
		map.put("startIndex", startIndex);

		// 한페이지 글 개수
		int indexSize = 5;
		map.put("indexSize", indexSize);
		
		return map;
	}
	
}
