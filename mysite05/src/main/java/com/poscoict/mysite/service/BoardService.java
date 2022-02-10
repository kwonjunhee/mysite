package com.poscoict.mysite.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.BoardRepository;
import com.poscoict.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	//새 글, 답글 달기
	public boolean addContents(BoardVo boardvo) {
		if(boardvo.getGroupNo() != null) {
			increaseGroupOrderNo(boardvo);
		}
		System.out.println("boardservice: " + boardvo);
		return 1==boardRepository.insert(boardvo);
		
	}
	
	//view 글 보기
	public BoardVo getContents(Long no) {
		boardRepository.updateHit(no);
		System.out.println("boardservice view: "+boardRepository.findOne(no));
		return boardRepository.findOne(no);
	}
	
	// 글 수정하기 전 -> 본인 글 볼 때
	// 난 아직도 얘의 효용성을 모르겠다
//	public BoardVo getContents(Long no, Long userNo) {
//		return boardRepository.findOne(no);
//	}
	
	// 글 수정
	public boolean updateContents(BoardVo boardvo) {
		return boardRepository.modify(boardvo);
	}
	
	// 글 삭제
	public boolean deleteContents(Long boardNo, Long authNo) {
		return boardRepository.delete(boardNo, authNo);
		
	}
	
	// 글 리스트 (search 결과)
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
		
		Map<String, Object> map = new HashMap<>();
		int cnt = boardRepository.getCount(keyword);
		
		map.put("pagecount", 5);
		map.put("list", boardRepository.findAll((currentPage-1)*(int)map.get("pagecount"), keyword));
		map.put("currentpage", currentPage);
		map.put("nextpage", 1);
		map.put("prepage", -1);
		map.put("cnt", cnt);
		map.put("listcnt",  cnt/(int)map.get("pagecount")+1);
		map.put("boardcnt", cnt-(currentPage-1)*(int)map.get("pagecount"));
		
		return map;
	}
	
	private boolean increaseGroupOrderNo(BoardVo boardvo) {
		return boardRepository.reply(boardvo);
		
	}
}
