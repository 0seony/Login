package com.yseon.login;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(Locale locale, Model model) {
		return "main";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		return "login";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Locale locale, Model model) {
		UserDB db = new UserDB();
		boolean isSuccess = db.createTable();
		if(isSuccess) {
			model.addAttribute("notice", "회원정보 테이블이 생성되었습니다.");
		} else {
			model.addAttribute("notice", "DB Error");
		}
		return "message";
	}
	
	@RequestMapping(value = "/login_action", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Locale locale, Model model) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String user_id = request.getParameter("user_id");
		String user_pwd = request.getParameter("user_pwd");
		UserDB db = new UserDB();
		boolean isSuccess = db.userlogin(user_id, user_pwd);
		if(isSuccess) {
			HttpSession session = request.getSession();
			session.setAttribute("is_login", true);
			return "redirect:/";
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Locale locale, Model model) {
		return "insert";
	}
	
	@RequestMapping(value = "/insert_action", method = RequestMethod.POST)
	public String insertData(HttpServletRequest request, Locale locale, Model model) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String user_id = request.getParameter("user_id");
		String user_pwd = request.getParameter("user_pwd");
		String user_name = request.getParameter("user_name");
		String user_birthday = request.getParameter("birthday");
		String user_address = request.getParameter("address");
		SimpleDateFormat createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now= createTime.format(Calendar.getInstance().getTime());
		UserInfo userinfo = new UserInfo(user_id, user_pwd, user_name, user_birthday, user_address, now, now);
		UserDB db = new UserDB();
		String resultString = db.insertData(userinfo);
//		boolean isSuccess = db.insertData(userinfo);
//		System.out.println(isSuccess);
//		if(isSuccess) {
//			model.addAttribute("notice", "회원정보가 입력되었습니다.");
//		} else {
//			model.addAttribute("notice", "입력 실패");
//		}
		model.addAttribute("notice", resultString);
		return "message";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String userlist(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			boolean isLogin = (Boolean) session.getAttribute("is_login");
			if(isLogin) {
				UserDB db = new UserDB();
				String htmlStirng = db.selectData();
				model.addAttribute("list", htmlStirng);
				return "list";
			}else {
				model.addAttribute("notice", "로그인이 필요합니다.");
				return "message";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("notice", "로그인이 필요합니다.");
			return "message";
		}

	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Locale locale, Model model, @RequestParam("no") int no) {
		UserDB db = new UserDB();
		UserInfo onedata = db.detalisData(no);
		if (onedata != null) {
			model.addAttribute("no", onedata.no);
			model.addAttribute("id", onedata.id);
			model.addAttribute("name", onedata.name);
			model.addAttribute("birthday", onedata.birthday);
			model.addAttribute("address", onedata.address);
		}
		return "update";
	}
	
	@RequestMapping(value = "/update_action", method = RequestMethod.GET)
	public String updateData(Locale locale, Model model
			,@RequestParam("no") int no
			,@RequestParam("user_id") String user_id
			,@RequestParam("user_name") String user_name
			,@RequestParam("birthday") String user_birthday
			,@RequestParam("address") String user_address) {
		SimpleDateFormat createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now= createTime.format(Calendar.getInstance().getTime());
		UserInfo userinfo = new UserInfo(no, user_id, user_name, user_birthday, user_address, now);
		UserDB db = new UserDB();
		boolean isSuccess = db.updateData(userinfo);
		if(isSuccess) {
			model.addAttribute("notice", "회원정보가 수정되었습니다.");
		} else {
			model.addAttribute("notice", "입력 실패");
		}
		return "message";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Locale locale, Model model, @RequestParam("no") int no) {
		UserDB db = new UserDB();
		boolean isSuccess = db.deleteData(no);
		if (isSuccess) {
			model.addAttribute("notice", "회원정보가 삭제되었습니다.");
		}else {
			model.addAttribute("notice", "Error");
		}
		return "message";
	}
	
	
	
}
