package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll(Long sn) {
		return sqlSession.selectList("guestbook.findAll", sn);
	}
	
	public int insert(GuestbookVo guestbookvo) {
		return sqlSession.insert("guestbook.insert", guestbookvo);
	}	
	
	public boolean delete(Long no, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("n", no);
		map.put("p", password);
		return  1 == sqlSession.delete("guestbook.delete", map);
	}
}
