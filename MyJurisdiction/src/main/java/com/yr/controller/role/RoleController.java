package com.yr.controller.role;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.RolePermission;
import com.yr.service.role.RoleService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/Jurisdictions/role")
public class RoleController {
	@Autowired
	@Qualifier("roleServiceImpl")
	private RoleService roleService;
	
	/**
	 * 分页查询所有数据
	 * @param limit
	 * @param page
	 * @param search
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="/roles", produces = "application/json; charset=utf-8")
    public String query(Integer limit,Integer page,String search){
    	Page<Role> pages = new Page<>();
    	pages.setPageNum(page);
    	pages.setPageSize(limit);
    	pages.setSearch(search);
    	pages = roleService.query(pages);
    	return pages.getJson();
    }
	
	/**
	 * 跳转添加页面
	 * @param map
	 * @return String
	 */
	@RequestMapping(value="/role", method=RequestMethod.GET)
	public String jumpAdd(Map<String, Object> map){
		map.put("role", new Role());
		return "/role/roleAdd";
	}
	
	/**
	 * 保存添加页面
	 * @return String
	 */
	@RequestMapping(value="/role",method=RequestMethod.POST)
	public String saveAdd(Role role){
		roleService.add(role);
		return "redirect:/Jurisdictions/role";
	}
	
	/**
	 * 跳转修改页面
	 * @param map
	 * @return jumpUpdate
	 */
	@RequestMapping(value="/role/{id}", method=RequestMethod.GET)
	public String jumpUpdate(@PathVariable("id") Integer id, Map<String, Object> map){
		Role role = roleService.queryById(id);
		map.put("role", role);
		return "/role/roleAdd";
	}
	
	/**
	 * 保存修改
	 * @return String
	 */
	@RequestMapping(value="/role", method=RequestMethod.PUT)
	public String saveUpdate(Role role){
		roleService.update(role);
		return "redirect:/Jurisdictions/role";
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return String
	 * 必须为responseBody,否则会当成映射的路径
	 */
	@ResponseBody
	@RequestMapping(value="/role", method=RequestMethod.DELETE)
	public void delete(@RequestParam("id") Integer id){
		roleService.delete(id);
	}
	
	/**
	 * 跳转角色赋权
	 * @return String
	 */
	@RequestMapping(value="/jumpRolePer")
	public String jumpRolePer(@RequestParam(value="id") Integer id, Map<String, Object> map){
		map.put("rid", id);
		return "role/rolePermission";
	}
	
	/**
	 * ajax回显下拉框
	 * @param id
	 * @return List<RolePermission>
	 */
	@RequestMapping(value="/getRolePer")
	@ResponseBody
	public List<RolePermission> getRolePer(@RequestParam(value="id") Integer id){
		return roleService.queryRolePer(id);
	}
	
	/**
	 * 给角色赋权限
	 * @param id
	 * @return String
	 */
	@RequestMapping("/empowerment")
	@ResponseBody
	public void empowerment(@RequestParam("id1") Integer rid ,@RequestParam("array") Integer[] pid){
		roleService.rolePermission(rid, pid);//进行角色赋权
	}
}
