package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {

	@Autowired
	private SiteService siteService;
	
	@RequestMapping({"", "/main"})
	public String index(Model model) {
		siteService.findContext(model);
		
		return "main/index";
	}

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
//		UserVo userVo;
//		return userVo;
		return "안녕하세요";
	}
}
