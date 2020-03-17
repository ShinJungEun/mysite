package com.douzone.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.repository.SiteRepository;
import com.douzone.mysite.vo.SiteVo;

@Service
public class SiteService {

	@Autowired
	private SiteRepository siteRepository;
	
	private static final String SAVE_PATH = "/mysite-uploads";
	private static final String URL = "/images";
	
	public void findContext(Model model) {
		SiteVo siteVo = siteRepository.find();
		model.addAttribute("siteVo", siteVo);
	}

	public boolean updateContext(SiteVo siteVo) {
		int count = siteRepository.update(siteVo);
		return count == 1;
	}
	
	public String findProfileContext() {
		SiteVo siteVo = siteRepository.find();
		String profile = siteVo.getProfile();
		
		return profile;
	}

	public String restore(MultipartFile multipartFile) {
		String url = "";
		try {
			if(multipartFile.isEmpty()) {
				return url;
			}

			String originFilename = multipartFile.getOriginalFilename();
			// 확장자 제거를 위한 시작 index 추출
			String extName = originFilename.substring(originFilename.lastIndexOf('.') + 1);	
			String saveFilename = generateSaveFilename(extName);
			long fileSize = multipartFile.getSize();

			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(fileData);
			os.close();
			
			url = URL + "/" + saveFilename;

		} catch(IOException ex) {
			throw new RuntimeException("file upload error:" + ex);
		}

		return url;
	}
	
	private String generateSaveFilename(String extName) {
		String filename = "";

		Calendar calendar = Calendar.getInstance();		// 현재시간
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);

		return filename;
	}
}
