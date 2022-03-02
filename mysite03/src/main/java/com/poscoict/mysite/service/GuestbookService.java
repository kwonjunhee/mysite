package com.poscoict.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.GuestbookRepository;
import com.poscoict.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookRepository guestbookrepository;
	
	public List<GuestbookVo> getMessageList(Long sn) {
		return guestbookrepository.findAll(sn);
	}
	
	public Boolean deleteMessage(Long no, String password) {
		return guestbookrepository.delete(no, password);
	}
	
	public int addMessage(GuestbookVo guestbookvo) {
		return guestbookrepository.insert(guestbookvo);
	}
}
