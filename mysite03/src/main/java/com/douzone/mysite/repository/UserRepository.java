package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
	}
	
	public UserVo findByEmailAndPassword(UserVo vo) {
		return sqlSession.selectOne("user.findByEmailAndPassword", vo);		
	}

	public UserVo findByEmailAndPassword2(String email, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("e", email);
		map.put("p", password);
		return sqlSession.selectOne("user.findByEmailAndPassword2", map);		
	}
	
	public UserVo findByNo(Long no) {
		return sqlSession.selectOne("user.find", no);	
	}
	
	public int update(UserVo userVo) {
//		int count = 0;
//		if("".equals(userVo.getPassword())) {
//			count = sqlSession.update("user.update1", userVo);
//		}
//		else {
//			count = sqlSession.update("user.update2", userVo);
//		}
				
		return sqlSession.update("user.update", userVo);
	}
}
