package com.yr.controller.login;

import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.entity.User;
import com.yr.service.login.LoginService;
import com.yr.util.DesUtil;

@Controller
@RequestMapping("/login")
public class LoginController {
	private final String LOGIN = "login/login";
	@Autowired
	@Qualifier("loginServiceImpl")
	private LoginService loginService;
	
	/**
	 * 进入数据库验证账号密码是否正确
	 * @return	String
	 */
	@RequestMapping("/verification")
	public String verification(@Valid User user, BindingResult result, @RequestParam(value="codes",required=false) String codes, HttpServletRequest request,Map<String, Object> map){
		map.put("name", user.getName());//将name回显至页面
		if(result.getErrorCount() > 0){//表示效验出现错误
			for (FieldError error : result.getFieldErrors()) {
				if(error.getField().equals("name")){//当字段为name
 					map.put("error", error.getDefaultMessage());
					break;
				}else{//或者password
					map.put("error", error.getDefaultMessage());
					break;
				}
			}
			return LOGIN;
		}else{
			String code = (String)request.getSession().getAttribute("myCode");
			if(code.equalsIgnoreCase(codes)){//判断验证码是否相同
				user.setPasswd(DesUtil.encrypt(user.getPasswd(), Charset.forName("gb2312"), "password"));//将密码经过des加密替换原本的密码
				boolean lean = loginService.isVerification(user);//判断密码是否正确
				if(!lean){//false进入
					map.put("error", "账户或密码错误");
					return LOGIN;
				}
				User users = loginService.queryByName(user.getName());//根据用户名查询出对象
				HttpSession session = request.getSession();
				session.setAttribute("user", users);//放入session中
				return "redirect:/index";//跳转到首页
			}else{//验证码错误
				map.put("error", "验证码错误");
				return LOGIN;
			}
		}
	}
}
