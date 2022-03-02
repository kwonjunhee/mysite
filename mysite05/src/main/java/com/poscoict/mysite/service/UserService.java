package com.poscoict.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.UserRepository;
import com.poscoict.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public void join(UserVo uservo) {
		userRepository.insert(uservo);
	}

	public UserVo getUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
		
	}

	public UserVo getUser(Long userNo) {
		return userRepository.findByNo(userNo);
	}

	public void updateUser(UserVo uservo) {
		userRepository.update(uservo);
		
	}

	public UserVo getUser(String email) {
		return userRepository.findByEmail(email);
	}
}
