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
	
	//如果检测到form表单提交带有id,直接将值存入request中
	@ModelAttribute
	public void getAccount(@RequestParam(value="id",required=false) Integer id,Map<String, Object> map){
		if(id !=null && id != 0){
			User user = userService.queryById(id);
			map.put("user", user); 
		}
	}
	
	//跳转个人资料页面
	@RequestMapping(value="/userInfo/{id}",method=RequestMethod.GET)
	public String queryByName(@PathVariable(required=false) Integer id, Map<String, Object> map, HttpServletRequest request){
		//将性别传入到页面
		Map<String, Object> maps = new HashMap<>();
		maps.put("1", "男");
		maps.put("2", "女");
		maps.put("3", "保密");
		map.put("sexs", maps);
		//根据id查询出数据
		User user = userService.queryById(id);
		map.put("user", user);
		
		return "user/userInfo";
	}
	
	//保存个人资料页面
	@RequestMapping(value="/userInfo",method=RequestMethod.PUT)
	public String Preservation(User user, @RequestParam(value="filesCopy") String filesCopy, HttpServletRequest request){
		userService.update(user);//修改字段
		FileUtils.fileCover(user.getHeadUrl(), request.getServletContext().getRealPath("/") + filesCopy.substring(filesCopy.indexOf("\\")+1,filesCopy.length()));//用流将图片覆盖掉并将之前图片删除掉
		return "redirect:/index";//跳转主页
	}
	
	//头像上传
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
    public Map<String,Object>  uploadFile(@RequestParam("files") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long startTime = FileUtils.getTimeStamp();
		String path = request.getServletContext().getRealPath("/") + "photos";//获取服务器路径
        String fileName = file.getOriginalFilename(); 
        fileName = startTime + fileName.substring(fileName.lastIndexOf("."), fileName.length());
        File filepath=new File(path,fileName);  
        //判断目标文件所在的目录是否存在
        if(!filepath.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
        	filepath.getParentFile().mkdirs();
        }
        //将内存中的数据写入磁盘
        file.transferTo(filepath);
        Map<String,Object> map = new HashMap<>();
        map.put("url", request.getServletContext().getContextPath() + File.separator  + "photos" + File.separator + fileName);
        return map;
    }
	
	/**
     * 通过url请求返回图像的字节流
     */
    @RequestMapping("/icon")
    public void getIcon(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");//从session取出对象
        byte[] data = FileUtils.getFileFlow(user.getHeadUrl());//调用方法将流传出
        response.setContentType("image/png");
        OutputStream stream = response.getOutputStream();
        stream.write(data);//将图片以流的形式返回出去
        stream.flush();
        stream.close();
    }
    
    /**
     * 通过url获取id请求返回图像的字节流
     */
    @RequestMapping("/icons/{id}")
    public void getIcons(@PathVariable(value="id") Integer id ,HttpServletRequest request, HttpServletResponse response) throws IOException {
    	User user = userService.queryById(id);
    	byte[] data = FileUtils.getFileFlow(user.getHeadUrl());//调用方法将流传出
    	response.setContentType("image/png");
    	OutputStream stream = response.getOutputStream();
        stream.write(data);//将图片以流的形式返回出去
        stream.flush();
        stream.close();
    }
    
    /**
     * 查询用户所有的数据
     * limit每页多少条
     * page当前多少页
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
     * 跳转添加页面
     * @return String
     */
    @RequestMapping(value="/user",method=RequestMethod.GET)
    public String jumpAdd(Map<String, Object> map){
    	//将性别传入到页面
		Map<String, Object> maps = new HashMap<>();
		maps.put("1", "男");
		maps.put("2", "女");
		maps.put("3", "保密");
		map.put("sexs", maps);
		//根据id查询出数据
		map.put("user", new User());
    	return "user/userAdd";
    }
    
    /**
     * 将用户数据添加到数据库
     * @param user
     * @return String
     */
    @RequestMapping(value="/user",method=RequestMethod.POST)
    public String saveAdd(User user, @RequestParam(value="filesCopy") String filesCopy, HttpServletRequest request, @RequestParam(value="cmbProvince") String cmbProvince, @RequestParam(value="cmbCity") String cmbCity, @RequestParam(value="cmbArea") String cmbArea){
    	
    	//通过ajax进行文件上传，文件存放在项目的临时目录中。（可以删除，覆盖也不会有影响。还可以返回路径直接显示）
    	//把返回的URL设置在隐藏框中，提交form表单一起提交。（不需要使用表单流）
    	//得到临时文件的路径。把这个文件进行复制一份到我们自己存放的目录，并删除临时文件。
    	
    	String path = "C:/Users/Administrator/Desktop/photo";
    	String phone = String.valueOf(FileUtils.getTimeStamp());
    	File file = new File(path, phone + ".jpg");//第一个是父级文件路径，第二个是文件名
    	if(!file.getParentFile().exists()){//判断父级路径是否存在
    		file.mkdir();//创建文件夹
    	}
    	if(!file.exists()){//如果文件不存在满足条件
			try {
				file.createNewFile();//创建该文件
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	String phoneStr = path + File.separator + phone + ".jpg";//组成一个图片的路径字符串
    	//截取指定路径组成一个本地路径
    	if(!filesCopy.equals("E:/workspace/MyJurisdiction/WebContent/images/587c589d26802.jpg")){
    		filesCopy = request.getServletContext().getRealPath("/") + "photos" + filesCopy.substring(filesCopy.lastIndexOf("\\"),filesCopy.length());
    	}
    	FileUtils.fileCover(phoneStr, filesCopy);//将读取的流覆盖创建的图片
    	user.setHeadUrl(phoneStr);//替换掉原本的路径
    	user.setPasswd(DesUtil.encrypt(user.getPasswd(), Charset.forName("gb2312"), "password"));//用des加密替换密码的值
    	//获取到页面的地址值,设置入对象中
    	Addr addr = new Addr();
    	addr.setProvince(cmbProvince);
    	addr.setCity(cmbCity);
    	addr.setArea(cmbArea);
    	user.setAddr(addr);//将对象添加到user中
    	userService.add(user);//进行数据修改
    	return "redirect:/Jurisdictions/user";//修改完数据一般都用redirect跳转
    }
    
    /**
     * 跳转修改页面
     * @param id
     * @param map
     * @return String
     */
    @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
    public String jumpUpdate(@PathVariable Integer id, Map<String, Object> map){
    	//将性别传入到页面
		Map<String, Object> maps = new HashMap<>();
		maps.put("1", "男");
		maps.put("2", "女");
		maps.put("3", "保密");
		map.put("sexs", maps);
    	User user = userService.queryById(id);
    	map.put("user", user);
    	return "user/userAdd";
    }
    
    /**
     * 接收用户信息进行修改
     * @param user
     * @param filesCopy
     * @param request
     * @return String
     */
    @RequestMapping(value="/user",method=RequestMethod.PUT)
    public String saveUpdate(User user, @RequestParam(value="filesCopy") String filesCopy, HttpServletRequest request){
    	userService.update(user);//修改字段
		FileUtils.fileCover(user.getHeadUrl(), request.getServletContext().getRealPath("/") + filesCopy.substring(filesCopy.indexOf("\\")+1,filesCopy.length()));//用流将图片覆盖掉并将之前图片删除掉
		return "redirect:/Jurisdictions/user";//跳转列表页
    }
    
    /**
     * 修改用户的状态
     * @return Map<String, Object>
     */
    @RequestMapping(value="/changeState",method=RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> changeState(@RequestParam(value="id",required=false) Integer id, @RequestParam(value="status",required=false) Integer status){
    	userService.changeState(id, status);//修改用户状态
    	Map<String, Object> map = new HashMap<>();
    	map.put("status", "成功");
    	return map;
    }
    
    /**
     * 单个或批量删除用户
     * @return Map<String, Object>
     */
    @RequestMapping(value="/user", method=RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteByOne(@RequestParam("id") int[] id){
    	for (int i = 0; i < id.length; i++) {//循环数组
    		User user = userService.queryById(id[i]);//根据用户id查询出对象
        	FileUtils.delete(user.getHeadUrl());//获得用户的图片路径删除
        	userService.delete(id[i]);//删除用户
		}
    	Map<String, Object> map = new HashMap<>();
    	map.put("status", "成功");
    	return map;
    }
    
    /**
     * 初始化用户密码,初始化密码为0000
     */
    @ResponseBody
    @RequestMapping("/recovery")
    public void recovery(@RequestParam("id") Integer id){
    	userService.recovery(id);
    }
    
    /**
     * 跳转角色赋权页面
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
     * 返回用户对应的角色
     * @return List<Role>
     */
    @ResponseBody
    @RequestMapping("/getUserRole")
    public List<Role> getUserRole(@RequestParam("id") Integer id){
    	return userService.queryRoleByUser(id);//根据用户id返回多个角色
    }
    
    /**
     * 提交用户分配的角色
     */
    @RequestMapping("/saveRole")
    public String saveRole(@RequestParam("id") Integer uid, @RequestParam("role") Integer[] rid){
    	userService.updateRole(uid, rid);
    	return "redirect:/Jurisdictions/user";
    }
    
}
