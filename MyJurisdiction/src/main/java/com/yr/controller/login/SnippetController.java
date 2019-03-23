package com.yr.controller.login;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.util.IdentifyCode;

//@WebServlet("/verificationCode")
@Controller
public class SnippetController{
	/*private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ������Ӧ�����͸�ʽΪͼƬ��ʽ
		resp.setContentType("image/jpeg");
		// ��ֹͼ�񻺴档
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		// �Զ�����ߡ������͸����ߵ�����
		IdentifyCode code = new IdentifyCode(116, 36, 4, 10);
		// ����session
		HttpSession session = req.getSession();
		session.setAttribute("myCode", code.getCode());
		// ��ӦͼƬ
		ServletOutputStream out = resp.getOutputStream();
		code.write(out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}*/
	@RequestMapping("/code")
	@ResponseBody
	public void getCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		// ������Ӧ�����͸�ʽΪͼƬ��ʽ
		response.setContentType("image/jpeg");
		// ��ֹͼ�񻺴档
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// �Զ�����ߡ������͸����ߵ�����
		IdentifyCode code = new IdentifyCode(248, 50, 4, 10);
		// ����session
		HttpSession session = request.getSession();
		session.setAttribute("myCode", code.getCode());
		// ��ӦͼƬ
		ServletOutputStream out = response.getOutputStream();
		code.write(out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}
}
