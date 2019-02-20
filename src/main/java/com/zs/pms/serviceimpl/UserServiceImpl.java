package com.zs.pms.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zs.pms.dao.UserDao;
import com.zs.pms.exception.AppException;
import com.zs.pms.po.TPermission;
import com.zs.pms.po.TUser;
import com.zs.pms.service.UserService;
import com.zs.pms.utils.Constants;
import com.zs.pms.utils.MD5;
import com.zs.pms.vo.QueryUser;
@Service
@Transactional //需要开启事务的业务对象
public class UserServiceImpl implements UserService{
	@Autowired
	UserDao dao;
	
	@Override
	public void hello() {
		
		System.out.println("Hello Spring");
	}

	@Override
	public List<TPermission> queryByUid(int id) {
		
		return dao.queryByUid(id);
	}

	@Override
	public List<TPermission> genMenu(List<TPermission> pers) {
		//创建新容器
		List<TPermission> list=new ArrayList<>();
		//遍历权限列表
		for(TPermission per:pers) {
			//一级菜单
			if (per.getLev()==1) {
				//加载该一级菜单下的二级菜单
				//遍历
				for(TPermission per2:pers) {
					//二级权限的上级id等于以及权限的id
					if (per2.getPid()==per.getId()) {
						//添加子权限
						per.addchild(per2);
					}
				}
				//加到新的集合中
				list.add(per);
			}
		}
		return list;
	}

	@Override
	//按条件查询
	public List<TUser> queryByCon(QueryUser query) {
		return dao.queryByCon(query);
	}

	@Override
	//批量删除
	public void deleteByIds(int[] ids) {
		dao.deleteByIds(ids);
		
	}

	@Override
	//修改
	public void updateUser(TUser user) {
		dao.updateUser(user);
		
	}
	
	//新增
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int insertUser(TUser user)throws AppException {
		//模拟业务异常
		if ("admin".equals(user.getLoginname())) {
			throw new AppException(1003, "登录名不能是admin");
		}
		MD5 md5=new MD5();
		user.setPassword(md5.getMD5ofStr(user.getPassword()));
		//新增
		dao.insertUser(user);
		
		//返回主键
		return user.getId();
	}

	@Override
	//删除
	public void delete(int id) {
		dao.deleteByid(id);	
		
	}

	@Override
	/**
	 * 获得分页记录page：当前页  query：查询条件
	 * @param page
	 * @param query
	 * @return
	 */
	//分页查询
	public List<TUser> queryByPage(int page ,QueryUser query) {
		// 通过当前页数计算起始数和截止数
		//起始数从1开始
		int start =(page-1)*Constants.PAGECONT+1;
		//截止数
		int end=page*Constants.PAGECONT;
		query.setStart(start);
		query.setEnd(end);
		return dao.queryByPage(query);
	}

	@Override
	//总页数
	public int queryPageCount(QueryUser query) {
		//通过总条数计算总页数
		int count =dao.queryCount(query);
		//能整除
		if (count%Constants.PAGECONT==0) {
			return count/Constants.PAGECONT;
		}else {
			return Constants.PAGECONT+1;
		}
		
	}

	@Override
	public TUser queryById(int id) {
		// TODO Auto-generated method stub
		return dao.queryById(id);
	}

	
}
