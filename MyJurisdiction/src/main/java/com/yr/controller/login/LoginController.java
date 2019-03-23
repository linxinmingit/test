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
	 * �������ݿ���֤�˺������Ƿ���ȷ
	 * @return	String
	 */
	@RequestMapping("/verification")
	public String verification(@Valid User user, BindingResult result, @RequestParam(value="codes",required=false) String codes, HttpServletRequest request,Map<String, Object> map){
		map.put("name", user.getName());//��name������ҳ��
		if(result.getErrorCount() > 0){//��ʾЧ����ִ���
			for (FieldError error : result.getFieldErrors()) {
				if(error.getField().equals("name")){//���ֶ�Ϊname
 					map.put("error", error.getDefaultMessage());
					break;
				}else{//����password
					map.put("error", error.getDefaultMessage());
					break;
				}
			}
			return LOGIN;
		}else{
			String code = (String)request.getSession().getAttribute("myCode");
			if(code.equalsIgnoreCase(codes)){//�ж���֤���Ƿ���ͬ
				user.setPasswd(DesUtil.encrypt(user.getPasswd(), Charset.forName("gb2312"), "password"));//�����뾭��des�����滻ԭ��������
				boolean lean = loginService.isVerification(user);//�ж������Ƿ���ȷ
				if(!lean){//false����
					map.put("error", "�˻����������");
					return LOGIN;
				}
				User users = loginService.queryByName(user.getName());//�����û�����ѯ������
				HttpSession session = request.getSession();
				session.setAttribute("user", users);//����session��
				return "redirect:/index";//��ת����ҳ
			}else{//��֤�����
				map.put("error", "��֤�����");
				return LOGIN;
			}
		}
	}
}
