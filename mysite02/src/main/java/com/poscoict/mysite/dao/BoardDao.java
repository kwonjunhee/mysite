package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.BoardVo;

public class BoardDao {
	public List<BoardVo> findAll(int page, String kwd) {
		List<BoardVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			if(kwd==null) {
				String sql =
						" select a.no, b.no, b.title, a.name, b.hit, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as reg_date "
						+ "from user a, board b where a.no = b.user_no "
						+ "order by b.g_no desc, b.o_no asc limit " + page + " , 5 ";
				pstmt = conn.prepareStatement(sql);
				
			} else {
				String sql =
						" select a.no, b.no, b.title, a.name, b.hit, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as reg_date "
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
				
				BoardVo vo = new BoardVo();
				vo.setUserNo(userNo);
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserName(UserName);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				
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
		BoardVo vo = new BoardVo();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			
			String sql =
				" select a.no, b.title, b.contents, a.name, b.hit, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as reg_date "
				+ " from user a, board b where a.no = b.user_no and b.no= " + no 
				+ " order by b.g_no desc, b.o_no asc";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long userNo = rs.getLong(1);
				String title = rs.getString(2);
				String Contents = rs.getString(3);
				String UserName = rs.getString(4);
				int hit = rs.getInt(5);
				String regDate = rs.getString(6);
				
				vo.setNo(no);
				vo.setUserNo(userNo);
				vo.setTitle(title);
				vo.setContents(Contents);
				vo.setUserName(UserName);
				vo.setHit(hit);
				vo.setRegDate(regDate);
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
		
		return vo;
	}
	public boolean delete(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = " delete from board where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getNo());
			
			int count = pstmt.executeUpdate();
			result = count == 1;
			
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
	
	public boolean insert(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "insert into board values(null, ? , ?, 0, "
					+ " (select num from( select ifnull(max(g_no)+1,1) as num from board) tmp), 1, 1, now(), ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());
			pstmt.setString(4, vo.getUserName());
			int count = pstmt.executeUpdate();
			result = count == 1;
			
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
	public boolean modify(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "update board set title=?, contents=? where no=?";
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
	
	public boolean updateHit(Long no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "update board set hit=ifnull(hit,0)+1 where no = "+no;
			pstmt = conn.prepareStatement(sql);
			
			int count = pstmt.executeUpdate();
			result = count == 1;
			
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
	public int getCount() {
		int result = 0;
		ResultSet rs = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();			
			while(rs.next()) {
				result = rs.getInt(1);
			}
			
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
