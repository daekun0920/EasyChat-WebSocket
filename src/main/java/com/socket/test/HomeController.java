package com.socket.test;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	static HttpSession session;
	public static List<String> idList = new ArrayList<String>();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(SessionVO vo, HttpServletRequest req) {
		session = req.getSession();
		if (vo.getSession_id() != null) {
			session.setAttribute("userid", vo.getSession_id());
			
			if (!idList.contains(vo.getSession_id())) {
				idList.add(vo.getSession_id());
			}
		}
		return "home";
	}
	
	@RequestMapping(value = "sessList.do", method = RequestMethod.GET)
	public @ResponseBody List<String> getSessionList() {
		try {
			return idList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
