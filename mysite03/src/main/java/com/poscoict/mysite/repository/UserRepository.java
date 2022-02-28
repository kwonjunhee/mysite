package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(UserVo uservo) {
		return 1 == sqlSession.insert("user.insert", uservo);
	}
		
	public boolean update(UserVo uservo) {
		int count = sqlSession.update("user.update", uservo);
		return count ==1;
	}
	

	public UserVo findByEmailAndPassword(String email, String password) {
		Map<String, String> map = new HashMap<>();
		map.put("e", email);
		map.put("p", password);
		return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}

	public UserVo findByNo(Long userNo) {
		return sqlSession.selectOne("user.findByNo", userNo);

	}

	public UserVo findByEmail(String email) {
		return sqlSession.selectOne("user.findByEmail", email);

	}

}
