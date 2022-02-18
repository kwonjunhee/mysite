package com.poscoict.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.GalleryRepository;
import com.poscoict.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	@Autowired
	private GalleryRepository galleryRepository;

	public List<GalleryVo> getImages() {
		return galleryRepository.getImage();
	}

	public boolean saveImage(GalleryVo galleryVo) {
		return 1==galleryRepository.saveImage(galleryVo);
	}

	public boolean removeImage(Long no) {
		return 1==galleryRepository.removeImage(no);
	}
	
	
	
}
