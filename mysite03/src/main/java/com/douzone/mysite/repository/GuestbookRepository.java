package com.douzone.mysite.repository;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}

	public int insert(GuestbookVo guestbookVo) {
		return sqlSession.insert("guestbook.insert", guestbookVo);
	}

	public int delete(Long no, String password) {
			String correctPassword = findPassword(no);
			
			if(password.equals(correctPassword)) {
				return sqlSession.delete("guestbook.delete", no);
			}
			else {
				System.out.println("비밀번호가 틀렸습니다");
				return 0;
			}
	}

	public String findPassword(Long no) {
		String password = null;
		List<GuestbookVo> list = sqlSession.selectList("guestbook.findAll");
		for(GuestbookVo vo : list) {
			if(vo.getNo() == no) {
				password = vo.getPassword();
				break;
			}
		}
		return password;

	}

}
