package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;

	public List<BoardVo> findAll(int page, String keyword) {
		Map<String, Object> map = new HashMap<>();
		
		// 시작 글번호
		int startIndex = (page - 1) * 5;
		map.put("startIndex", startIndex);

		// 한페이지 글 개수
		int indexSize = 5;
		map.put("indexSize", indexSize);
		
		// 키워드
		map.put("keyword", keyword);
		
		return sqlSession.selectList("board.findAll", map);
	}

	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}
	
//	public int findgNo(Long no) {
//		return sqlSession.selectOne("board.findgNo", no);
//	}

	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}

	public int updateReply(BoardVo vo) {
		return sqlSession.update("board.updateReply", vo);
	}
	
	public int insertReply(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}
	
	public int updateHit(BoardVo vo) {
		return sqlSession.update("board.updateHit", vo);
	}

	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
	}
	
	public int delete(Long no) {
		return sqlSession.update("board.delete", no);

	}
	
	public int listLen(String keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		return sqlSession.selectOne("board.listLen", map);
	}
	
/**
	public List<BoardVo> searchList(String kwd) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			conn = getConnection();

			String sql = "select board.no, title, name, hit, date_format(reg_date,'%Y-%m-%d %h:%i:%s'), user_no, g_no, o_no, depth from board, user where user_no = user.no and title like concat('%', ?, '%') order by g_no DESC, o_no ASC";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, kwd);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				Timestamp regDate = rs.getTimestamp(5);
				Long userNo = rs.getLong(6);
				int gNo = rs.getInt(7);
				int oNo = rs.getInt(8);
				int depth = rs.getInt(9);

				BoardVo vo = new BoardVo();

				vo.setNo(no);
				vo.setTitle(title);
				vo.setName(name);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setUserNo(userNo);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);

				result.add(vo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			try {
				if(rs != null)
					rs.close();

				if(pstmt != null)
					pstmt.close();

				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
**/
}
