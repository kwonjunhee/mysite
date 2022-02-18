package com.poscoict.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<GalleryVo> getImage() {
		return sqlSession.selectList("gallery.getImage");
	}

	public int saveImage(GalleryVo galleryVo) {
		return sqlSession.insert("gallery.saveImage", galleryVo);
	}

	public int removeImage(Long no) {
		return sqlSession.delete("gallery.removeImage", no);
	}

}
