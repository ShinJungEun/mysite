package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.security.Auth;

@Auth("ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private SiteService siteService;

	@RequestMapping("")
	public String main(Model model) {
		siteService.findContext(model);
		return "admin/main";
	}

	@RequestMapping(value="/main/update", method=RequestMethod.POST)
	public String update(SiteVo siteVo, 
			@RequestParam(value="upload-file") MultipartFile multipartFile) {
		
		String profile = "";
		if(multipartFile.isEmpty()) {
			profile = siteService.findProfileContext();
			siteVo.setProfile(profile);
			siteService.updateContext(siteVo);
			return "redirect:/admin";
		}
		profile = siteService.restore(multipartFile);
		siteVo.setProfile(profile);
		
		siteService.updateContext(siteVo);
		return "redirect:/admin";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}

}