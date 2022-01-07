package com.lagou.controller;

import com.lagou.domain.Menu;
import com.lagou.domain.ResponseResult;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    /*
    查询所有菜单信息
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu(){
        List<Menu> allMenu = menuService.findAllMenu();
        ResponseResult result = new ResponseResult(true,200,"响应成功",allMenu);
        return result;
    }

    /*
    回显菜单信息
     */
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuById(@RequestParam Integer id){
        //根据id的值判断当前是更新还是添加操作
        if(id==-1){
            //添加，回显信息中不需要查询menu信息
            List<Menu> menuList = menuService.findSubMenuListByPid(-1);
            //封装数据
            Map<String,Object> map = new HashMap<>();
            map.put("menuInfo",null);
            map.put("parentMenuList",menuList);
            ResponseResult result = new ResponseResult(true,200,"响应成功",map);
            return result;
        }else{
            //修改操作 回显所有Menu信息
            Menu menu = menuService.findMenuById(id);
            List<Menu> menuList = menuService.findSubMenuListByPid(-1);
            Map<String,Object> map = new HashMap<>();
            map.put("menuInfo",menu);
            map.put("parentMenuList",menuList);
            ResponseResult result = new ResponseResult(true,200,"响应成功",map);
            return result;
        }

    }
}
