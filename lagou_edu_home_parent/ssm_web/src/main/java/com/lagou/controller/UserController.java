package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVo;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /*
    用户分页及多条件组合查询
     */
    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo){
        PageInfo pageInfo = userService.findAllUserByPage(userVo);
        ResponseResult responseResult = new ResponseResult(true,200,"响应成功",pageInfo);
        return responseResult;
    }

    /*
   修改用户状态
    */
    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(@RequestParam int id ,@RequestParam String status){
        if("ENABLE".equalsIgnoreCase(status)){
            status = "DISABLE";
        }else{
            status = "ENABLE";
        }
        userService.updateUserStatus(id,status);
        ResponseResult responseResult = new ResponseResult(true,200,"响应成功",status);
        return responseResult;
    }

    /*
    用户登陆（根据用户名查询具体的用户信息）
     */
    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {
        User login = userService.login(user);
        if(login!=null){
            //保存用户Id和access_token到session中
            HttpSession session = request.getSession();
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("user_id",login.getId());
            session.setAttribute("access_token",access_token);
            //将查询出来的信息响应给前台
            Map<String,Object> map = new HashMap<>();
            map.put("access_token", access_token);
            map.put("user_id",login.getId());
            map.put("user",login);
            return new ResponseResult(true,1,"响应成功",map);
        }else{
            return  new ResponseResult(true,1,"用户名密码错误",null);
        }
    }

    /*
       分配角色（回显）
    */
    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRelationRoleById(Integer id){

        List<Role> roleList = userService.findUserRelationRoleById(id);

        return  new ResponseResult(true,200,"分配角色回显成功",roleList);
    }

    /*
       分配角色
    */
    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVo userVo){
        userService.userContextRole(userVo);
        return new ResponseResult(true,200,"分配角色成功",null);
    }

    /*
        获取用户权限，进行菜单动态展示
     */

    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request){

        // 1.获取请求头中的token
        String header_token = request.getHeader("Authorization");

        // 2.获取session中token
        String session_token = (String) request.getSession().getAttribute("access_token");

        // 3.判断token是否一致
        if(header_token.equals(session_token)){
            // 获取用户id
            Integer user_id = (Integer) request.getSession().getAttribute("user_id");
            // 调用service,进行菜单信息查询
            ResponseResult responseResult = userService.getUserPermissions(user_id);
            return responseResult;
        }else {
            ResponseResult responseResult = new ResponseResult(false, 400, "获取菜单信息失败", null);
            return responseResult;
        }


    }
}
