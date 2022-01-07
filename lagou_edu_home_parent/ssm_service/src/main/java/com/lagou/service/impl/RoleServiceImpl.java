package com.lagou.service.impl;

import com.lagou.dao.RoleMapper;
import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;
import com.lagou.domain.Role_menu_relation;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    /*
    查询所有角色&条件查询
     */
    @Override
    public List<Role> findAllRole(Role role) {
        List<Role> allRole = roleMapper.findAllRole(role);
        return allRole;
    }

    /*
    根据角色ID查询该角色关联的菜单信息ID
     */
    @Override
    public List<Integer> findMenuByRoleId(Integer roleId) {
        List<Integer> menuList = roleMapper.findMenuByRoleId(roleId);
        return menuList;
    }

    /*
    为角色分配菜单
     */
    @Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {
        //清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVo.getRoleId());
        //为角色分配菜单
        for (Integer mid : roleMenuVo.getMenuIdList()) {
            Role_menu_relation role_menu_relation = new Role_menu_relation();
            Date date=new Date();
            role_menu_relation.setMenuId(mid);
            role_menu_relation.setRoleId(roleMenuVo.getRoleId());
            role_menu_relation.setCreatedTime(date);
            role_menu_relation.setUpdatedTime(date);
            role_menu_relation.setCreatedBy("system");
            role_menu_relation.setUpdatedby("system");
            roleMapper.roleContextMenu(role_menu_relation);
        }

    }

    /*
   删除角色
    */
    @Override
    public void deleteRole(Integer rid) {
        // 清空中间表
        roleMapper.deleteRoleContextMenu(rid);
        roleMapper.deleteRole(rid);
    }
}
