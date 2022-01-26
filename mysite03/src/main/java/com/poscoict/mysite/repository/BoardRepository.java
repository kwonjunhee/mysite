package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	
	public List<BoardVo> findAll(int page, String kwd) {
		List<BoardVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			if(kwd==null) {
				String sql =
						" select a.no, b.no, b.title, a.name, b.hit, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as regDate, depth "
						+ "from user a, board b where a.no = b.user_no "
						+ "order by b.g_no desc, b.o_no asc limit " + page + " , 5 ";
				pstmt = conn.prepareStatement(sql);
				
			} else {
				String sql =
						" select a.no, b.no, b.title, a.name, b.hit, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as regDate, depth "
						+ "from user a, board b where a.no = b.user_no and title like '%" + kwd + "%' "
						+ "order by b.g_no desc, b.o_no asc "
						+ " limit " + page + " , 5";				
				pstmt = conn.prepareStatement(sql);
			}
			rs = pstmt.executeQuery();			
			while(rs.next()) {
				Long userNo = rs.getLong(1);
				Long no = rs.getLong(2);
				String title = rs.getString(3);
				String UserName = rs.getString(4);
				int hit = rs.getInt(5);
				String regDate = rs.getString(6);
				int depth = rs.getInt(7);
				
				BoardVo vo = new BoardVo();
				vo.setUserNo(userNo);
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserName(UserName);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setDepth(depth);
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	public BoardVo findOne(Long no) {
		
		return sqlSession.selectOne("board.findOne", no);
	}
	public boolean delete(Long no, Long userNo) {
		Map<String, Long> map = new HashMap<>();
		map.put("no", no);
		map.put("userno", userNo);
		
		return 1==sqlSession.delete("board.delete", map);
	}
	
	public int insert(BoardVo vo) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			if(vo.getGroupNo() == null) {
				String sql = "insert into board values(null, ?, ?, 0, "
						+ " (select num from( select ifnull(max(g_no)+1,1) as num from board) tmp), 1, 0, now(), ?, ?)";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setLong(3, vo.getUserNo());
				pstmt.setString(4, vo.getUserName());
			} else {
				String sql = "insert  into board values(null, ?, ?, 0, ?, ?+1, ?+1, now(), ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setInt(3, vo.getGroupNo());
				pstmt.setInt(4, vo.getOrderNo());
				pstmt.setInt(5, vo.getDepth());
				pstmt.setLong(6, vo.getUserNo());
				pstmt.setString(7, vo.getUserName());
				
			}
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return result;
	}
	
	public boolean reply(BoardVo boardvo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		try {
			conn = getConnection();
			
			String sql1 = "update board set o_no = o_no+1 where o_no > ? and  g_no=?";
			pstmt1 = conn.prepareStatement(sql1);
			
			pstmt1.setInt(1, boardvo.getOrderNo());
			pstmt1.setInt(2, boardvo.getGroupNo());
			pstmt1.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt1 != null) {
					pstmt1.close();
				}
				if(pstmt2 != null) {
					pstmt2.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return result;
	}

	public boolean modify(BoardVo boardvo) {
		return 1==sqlSession.update("board.modify", boardvo);
	}
	
	public boolean updateHit(Long no) {
		return 1== sqlSession.update("board.updateHit", no);
	}

	public int getCount(String kwd) {
		int count =sqlSession.selectOne("board.getCount", kwd);
		return count;
	}
	
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}


}
