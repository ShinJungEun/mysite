package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");

//		<GuestbookVo> 

		
	}

	public int insert(GuestbookVo vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = "insert into guestbook values(null, ?, ?, ?, now())"; 

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getPassword());

			int count = pstmt.executeUpdate();

			result = count;

		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.getMessage());
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();

				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				throw new GuestbookRepositoryException(e.getMessage());
			}

		}
		return result;
	}

	public int delete(Long no, String password) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			String correctPassword = findPassword(no);
			if(password.equals(correctPassword)) {
				conn = getConnection();

				String sql = "delete from guestbook where no=?"; 

				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1, no);

				pstmt.execute();

				int count = pstmt.executeUpdate();

				result = count;

			}
			else {
				System.out.println("비밀번호가 틀렸습니다");
				return 0;
			}


			int count = pstmt.executeUpdate();

			result = count;

		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.getMessage());
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();

				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				throw new GuestbookRepositoryException(e.getMessage());
			}

		}
		return result;
	}

	public String findPassword(Long no) {
		String password = null;
		List<GuestbookVo> list = new GuestbookRepository().findAll();

		for(GuestbookVo vo : list) {
			if(vo.getNo() == no) {
				password = vo.getPassword();
				break;
			}
		}
		return password;

	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mysql://192.168.1.99:3307/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");		
			
		} catch (ClassNotFoundException e) {
			throw new GuestbookRepositoryException("드라이버 로딩 실패:" + e);			
		} 

		return conn;	
	}


}
