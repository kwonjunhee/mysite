package com.poscoict.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.SiteRepository;
import com.poscoict.mysite.vo.SiteVo;

@Service
public class SiteService {
	@Autowired
	private SiteRepository siteRepository;
	
	public SiteVo findSite() {
		
		return siteRepository.findSite();
	}
	
	public boolean updateSite(SiteVo siteVo) {
		int count = siteRepository.updateSite(siteVo);
		return count ==1;
	}
}