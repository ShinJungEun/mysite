package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;

public class BoardRepository {

	public BoardVo find() {
		BoardVo result = new BoardVo();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			conn = getConnection();

			String sql = "select no, title, contents, user_no from board order by no desc";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery(sql);

			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long userNo = rs.getLong(4);

				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setUserNo(userNo);

				return result;
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

	public List<BoardVo> findList() {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			conn = getConnection();

			String sql = "select board.no, title, name, hit, date_format(reg_date,'%Y-%m-%d %h:%i:%s'), user_no, g_no, o_no, depth from board, user where user_no = user.no order by g_no DESC, o_no ASC";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery(sql);

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

	public BoardVo findByNo(Long boardNo) {
		BoardVo result = new BoardVo();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			conn = getConnection();

			String sql = "select no, title, contents, user_no, g_no, o_no, depth, hit from board where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, boardNo);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long userNo = rs.getLong(4);
				int gNo = rs.getInt(5);
				int oNo = rs.getInt(6);
				int depth = rs.getInt(7);
				int hit = rs.getInt(8);

				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setUserNo(userNo);
				result.setgNo(gNo);
				result.setoNo(oNo);
				result.setDepth(depth);
				result.setHit(hit);

				return result;
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
	
	
	public Boolean insert(BoardVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = "insert into board values(null, ?, ?, 0, now(), (select ifnull(max(b.g_no),0)+1 from board b), 0, 0, ?)"; 


			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());


			int count = pstmt.executeUpdate();

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
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

	public Boolean update(BoardVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board set title = ?, contents = ? where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());


			int count = pstmt.executeUpdate();

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
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

	// vo 객체의 정보들은 부모에서 따옴
	public Boolean insertReply(BoardVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?)"; 

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getgNo());
			pstmt.setInt(4, vo.getoNo()+1);
			pstmt.setInt(5, vo.getDepth()+1);
			pstmt.setLong(6, vo.getUserNo());

			int count = pstmt.executeUpdate();

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
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

	public Boolean updateReply(BoardVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board set o_no=o_no+1 where g_no = ? and o_no > ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getgNo());
			pstmt.setLong(2, vo.getoNo());

			int count = pstmt.executeUpdate();

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
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

	public Boolean delete(Long no) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update user as a inner join board as b on a.no = b.user_no set reg_date=\"0000-00-00 00:00:00\" where b.no=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			int count = pstmt.executeUpdate();

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
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

	public Boolean updateHit(BoardVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board set hit = hit+1 where no=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getNo());

			int count = pstmt.executeUpdate();

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
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

	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mysql://192.168.1.99:3307/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");		
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 

		return conn;	
	}

}
