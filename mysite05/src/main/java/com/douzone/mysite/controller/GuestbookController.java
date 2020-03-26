package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping(value={"", "/list"}, method=RequestMethod.GET)
	public String index(Model model) {
//		System.out.println("list");
		guestbookService.list(model);
		return "guestbook/list";
	}

	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert( @ModelAttribute GuestbookVo vo) {
		guestbookService.insert(vo);
		return "redirect:/guestbook";
	}

	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(
			@PathVariable("no") Long no, 
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		guestbookService.delete(no,  password);
		return "redirect:/guestbook";
	}
	
}
