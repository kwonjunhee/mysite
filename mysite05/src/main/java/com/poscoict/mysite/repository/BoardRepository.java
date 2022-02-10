package com.poscoict.mysite.repository;

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
		Map<String, Object> map = new HashMap<>();
		map.put("page", page);
		map.put("kwd", kwd);
		return sqlSession.selectList("board.findAll", map);
	}
	public BoardVo findOne(Long no) {
		return sqlSession.selectOne("board.findOne", no);
	}
	public boolean delete(Long boardNo, Long authNo) {
		Map<String, Long> map = new HashMap<>();
		map.put("boardNo", boardNo);
		map.put("authNo", authNo);
		return 1==sqlSession.delete("board.delete", map);
	}
	
	public int insert(BoardVo boardvo) {
		return sqlSession.insert("board.insert", boardvo);
	}
	
	public boolean reply(BoardVo boardvo) {
		return 1==sqlSession.update("board.reply", boardvo);
	}

	public boolean modify(BoardVo boardvo) {
		return 1==sqlSession.update("board.modify", boardvo);
	}
	
	public boolean updateHit(Long no) {
		return 1== sqlSession.update("board.updateHit", no);
	}

	public int getCount(String kwd) {
		int count = sqlSession.selectOne("board.getCount", kwd);
		return count;
	}
}
