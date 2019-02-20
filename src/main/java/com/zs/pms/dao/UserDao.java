package com.zs.pms.dao;

import java.util.List;

import javax.management.Query;

import com.zs.pms.po.TPermission;
import com.zs.pms.po.TUser;
import com.zs.pms.vo.QueryUser;

public interface UserDao {
	//根据用户id获得权限列表
	public List<TPermission> queryByUid(int id);
	//根据条件查询 
	public List<TUser> queryByCon(QueryUser query);
	//分页查询
	public List<TUser> queryByPage(QueryUser query);
	//获得总条数
	public int queryCount(QueryUser query);
	//新增返回主键
	public int insertUser(TUser user);
	//批量删除
	public void deleteByIds(int [] ids);
	//修改
	public void updateUser(TUser user);	
	//删除
	public void deleteByid(int id);
	//根据主键读取
	public TUser queryById(int id);

}
