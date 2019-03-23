package com.yr.controller.user;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yr.entity.Addr;
import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.User;
import com.yr.service.user.UserService;
import com.yr.util.DesUtil;
import com.yr.util.FileUtils;

@Controller
@RequestMapping("/Jurisdictions/user")
public class UserController {
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	//�����⵽form���ύ����id,ֱ�ӽ�ֵ����request��
	@ModelAttribute
	public void getAccount(@RequestParam(value="id",required=false) Integer id,Map<String, Object> map){
		if(id !=null && id != 0){
			User user = userService.queryById(id);
			map.put("user", user); 
		}
	}
	
	//��ת��������ҳ��
	@RequestMapping(value="/userInfo/{id}",method=RequestMethod.GET)
	public String queryByName(@PathVariable(required=false) Integer id, Map<String, Object> map, HttpServletRequest request){
		//���Ա��뵽ҳ��
		Map<String, Object> maps = new HashMap<>();
		maps.put("1", "��");
		maps.put("2", "Ů");
		maps.put("3", "����");
		map.put("sexs", maps);
		//����id��ѯ������
		User user = userService.queryById(id);
		map.put("user", user);
		
		return "user/userInfo";
	}
	
	//�����������ҳ��
	@RequestMapping(value="/userInfo",method=RequestMethod.PUT)
	public String Preservation(User user, @RequestParam(value="filesCopy") String filesCopy, HttpServletRequest request){
		userService.update(user);//�޸��ֶ�
		FileUtils.fileCover(user.getHeadUrl(), request.getServletContext().getRealPath("/") + filesCopy.substring(filesCopy.indexOf("\\")+1,filesCopy.length()));//������ͼƬ���ǵ�����֮ǰͼƬɾ����
		return "redirect:/index";//��ת��ҳ
	}
	
	//ͷ���ϴ�
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
    public Map<String,Object>  uploadFile(@RequestParam("files") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long startTime = FileUtils.getTimeStamp();
		String path = request.getServletContext().getRealPath("/") + "photos";//��ȡ������·��
        String fileName = file.getOriginalFilename(); 
        fileName = startTime + fileName.substring(fileName.lastIndexOf("."), fileName.length());
        File filepath=new File(path,fileName);  
        //�ж�Ŀ���ļ����ڵ�Ŀ¼�Ƿ����
        if(!filepath.getParentFile().exists()) {
            //���Ŀ���ļ����ڵ�Ŀ¼�����ڣ��򴴽���Ŀ¼
        	filepath.getParentFile().mkdirs();
        }
        //���ڴ��е�����д�����
        file.transferTo(filepath);
        Map<String,Object> map = new HashMap<>();
        map.put("url", request.getServletContext().getContextPath() + File.separator  + "photos" + File.separator + fileName);
        return map;
    }
	
	/**
     * ͨ��url���󷵻�ͼ����ֽ���
     */
    @RequestMapping("/icon")
    public void getIcon(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");//��sessionȡ������
        byte[] data = FileUtils.getFileFlow(user.getHeadUrl());//���÷�����������
        response.setContentType("image/png");
        OutputStream stream = response.getOutputStream();
        stream.write(data);//��ͼƬ��������ʽ���س�ȥ
        stream.flush();
        stream.close();
    }
    
    /**
     * ͨ��url��ȡid���󷵻�ͼ����ֽ���
     */
    @RequestMapping("/icons/{id}")
    public void getIcons(@PathVariable(value="id") Integer id ,HttpServletRequest request, HttpServletResponse response) throws IOException {
    	User user = userService.queryById(id);
    	byte[] data = FileUtils.getFileFlow(user.getHeadUrl());//���÷�����������
    	response.setContentType("image/png");
    	OutputStream stream = response.getOutputStream();
        stream.write(data);//��ͼƬ��������ʽ���س�ȥ
        stream.flush();
        stream.close();
    }
    
    /**
     * ��ѯ�û����е�����
     * limitÿҳ������
     * page��ǰ����ҳ
     */
    @RequestMapping(value="/users", method= RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String query(int limit,int page,String search){
    	Page<User> pages = new Page<>();
    	pages.setPageNum(page);
    	pages.setPageSize(limit);
    	pages.setSearch(search);
    	pages = userService.query(pages);
    	return pages.getJson();
    }
    
    /**
     * ��ת���ҳ��
     * @return String
     */
    @RequestMapping(value="/user",method=RequestMethod.GET)
    public String jumpAdd(Map<String, Object> map){
    	//���Ա��뵽ҳ��
		Map<String, Object> maps = new HashMap<>();
		maps.put("1", "��");
		maps.put("2", "Ů");
		maps.put("3", "����");
		map.put("sexs", maps);
		//����id��ѯ������
		map.put("user", new User());
    	return "user/userAdd";
    }
    
    /**
     * ���û�������ӵ����ݿ�
     * @param user
     * @return String
     */
    @RequestMapping(value="/user",method=RequestMethod.POST)
    public String saveAdd(User user, @RequestParam(value="filesCopy") String filesCopy, HttpServletRequest request, @RequestParam(value="cmbProvince") String cmbProvince, @RequestParam(value="cmbCity") String cmbCity, @RequestParam(value="cmbArea") String cmbArea){
    	
    	//ͨ��ajax�����ļ��ϴ����ļ��������Ŀ����ʱĿ¼�С�������ɾ��������Ҳ������Ӱ�졣�����Է���·��ֱ����ʾ��
    	//�ѷ��ص�URL���������ؿ��У��ύform��һ���ύ��������Ҫʹ�ñ�����
    	//�õ���ʱ�ļ���·����������ļ����и���һ�ݵ������Լ���ŵ�Ŀ¼����ɾ����ʱ�ļ���
    	
    	String path = "C:/Users/Administrator/Desktop/photo";
    	String phone = String.valueOf(FileUtils.getTimeStamp());
    	File file = new File(path, phone + ".jpg");//��һ���Ǹ����ļ�·�����ڶ������ļ���
    	if(!file.getParentFile().exists()){//�жϸ���·���Ƿ����
    		file.mkdir();//�����ļ���
    	}
    	if(!file.exists()){//����ļ���������������
			try {
				file.createNewFile();//�������ļ�
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	String phoneStr = path + File.separator + phone + ".jpg";//���һ��ͼƬ��·���ַ���
    	//��ȡָ��·�����һ������·��
    	if(!filesCopy.equals("E:/workspace/MyJurisdiction/WebContent/images/587c589d26802.jpg")){
    		filesCopy = request.getServletContext().getRealPath("/") + "photos" + filesCopy.substring(filesCopy.lastIndexOf("\\"),filesCopy.length());
    	}
    	FileUtils.fileCover(phoneStr, filesCopy);//����ȡ�������Ǵ�����ͼƬ
    	user.setHeadUrl(phoneStr);//�滻��ԭ����·��
    	user.setPasswd(DesUtil.encrypt(user.getPasswd(), Charset.forName("gb2312"), "password"));//��des�����滻�����ֵ
    	//��ȡ��ҳ��ĵ�ֵַ,�����������
    	Addr addr = new Addr();
    	addr.setProvince(cmbProvince);
    	addr.setCity(cmbCity);
    	addr.setArea(cmbArea);
    	user.setAddr(addr);//��������ӵ�user��
    	userService.add(user);//���������޸�
    	return "redirect:/Jurisdictions/user";//�޸�������һ�㶼��redirect��ת
    }
    
    /**
     * ��ת�޸�ҳ��
     * @param id
     * @param map
     * @return String
     */
    @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
    public String jumpUpdate(@PathVariable Integer id, Map<String, Object> map){
    	//���Ա��뵽ҳ��
		Map<String, Object> maps = new HashMap<>();
		maps.put("1", "��");
		maps.put("2", "Ů");
		maps.put("3", "����");
		map.put("sexs", maps);
    	User user = userService.queryById(id);
    	map.put("user", user);
    	return "user/userAdd";
    }
    
    /**
     * �����û���Ϣ�����޸�
     * @param user
     * @param filesCopy
     * @param request
     * @return String
     */
    @RequestMapping(value="/user",method=RequestMethod.PUT)
    public String saveUpdate(User user, @RequestParam(value="filesCopy") String filesCopy, HttpServletRequest request){
    	userService.update(user);//�޸��ֶ�
		FileUtils.fileCover(user.getHeadUrl(), request.getServletContext().getRealPath("/") + filesCopy.substring(filesCopy.indexOf("\\")+1,filesCopy.length()));//������ͼƬ���ǵ�����֮ǰͼƬɾ����
		return "redirect:/Jurisdictions/user";//��ת�б�ҳ
    }
    
    /**
     * �޸��û���״̬
     * @return Map<String, Object>
     */
    @RequestMapping(value="/changeState",method=RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> changeState(@RequestParam(value="id",required=false) Integer id, @RequestParam(value="status",required=false) Integer status){
    	userService.changeState(id, status);//�޸��û�״̬
    	Map<String, Object> map = new HashMap<>();
    	map.put("status", "�ɹ�");
    	return map;
    }
    
    /**
     * ����������ɾ���û�
     * @return Map<String, Object>
     */
    @RequestMapping(value="/user", method=RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteByOne(@RequestParam("id") int[] id){
    	for (int i = 0; i < id.length; i++) {//ѭ������
    		User user = userService.queryById(id[i]);//�����û�id��ѯ������
        	FileUtils.delete(user.getHeadUrl());//����û���ͼƬ·��ɾ��
        	userService.delete(id[i]);//ɾ���û�
		}
    	Map<String, Object> map = new HashMap<>();
    	map.put("status", "�ɹ�");
    	return map;
    }
    
    /**
     * ��ʼ���û�����,��ʼ������Ϊ0000
     */
    @ResponseBody
    @RequestMapping("/recovery")
    public void recovery(@RequestParam("id") Integer id){
    	userService.recovery(id);
    }
    
    /**
     * ��ת��ɫ��Ȩҳ��
     * @return String
     */
    @RequestMapping("/jumpRole")
    public String jumpRole(@RequestParam("id") Integer id, Map<String, Object> map){
    	List<Role> list = userService.queryRole();
    	User user = userService.queryById(id);
    	map.put("list", list);
    	map.put("user", user);
    	return "user/userRole";
    }
    
    /**
     * �����û���Ӧ�Ľ�ɫ
     * @return List<Role>
     */
    @ResponseBody
    @RequestMapping("/getUserRole")
    public List<Role> getUserRole(@RequestParam("id") Integer id){
    	return userService.queryRoleByUser(id);//�����û�id���ض����ɫ
    }
    
    /**
     * �ύ�û�����Ľ�ɫ
     */
    @RequestMapping("/saveRole")
    public String saveRole(@RequestParam("id") Integer uid, @RequestParam("role") Integer[] rid){
    	userService.updateRole(uid, rid);
    	return "redirect:/Jurisdictions/user";
    }
    
}
