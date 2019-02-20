import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zs.pms.dao.UserDao;
import com.zs.pms.exception.AppException;
import com.zs.pms.po.TDep;
import com.zs.pms.po.TPermission;
import com.zs.pms.po.TUser;
import com.zs.pms.service.UserService;
import com.zs.pms.vo.QueryUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationcontext.xml")
public class TestUserService {
		@Autowired
		UserService us;
		UserDao dao;
		
		public void testHello() {
			us.hello();
		}
		
		
		public void testlogin() {
			List<TPermission> list1=us.queryByUid(3084);
			for(TPermission per:list1) {
				System.out.println(per.getPname());
			}
			System.out.println("==================================================");
			for(TPermission per1:us.genMenu(list1)) {
				//一级权限
				System.out.println(per1.getPname());
				for(TPermission per2:per1.getChildren()) {
					System.out.println("------"+per2.getPname());
				}
			}
			
			
		}
		
		//查询
		public void testQuery() {
			//创建查询对象
			QueryUser query=new QueryUser();
			query.setLoginname("test1");
			query.setPassword("123");
			query.setSex("男");
			System.out.println(us.queryByCon(query).size());
		}
		
		//批量删除
		public void testDeletes() {
			int [] ids= {3066,3097};
			us.deleteByIds(ids);
		}
		
		//修改
		public void testUpdate() {
			TUser user =new TUser();
			user.setId(3084);
//			TDep dep=new TDep();
//			dep.setId(6);
//			user.setDept(dep);
//			user.setEmail("999@163.com");
//			user.setIsenabled(0);
//			user.setLoginname("update123");
//			user.setPassword("update123");
//			user.setPic("update123");
//			user.setRealname("update123");
//			user.setBirthday(new Date());
			user.setSex("女");
			user.setUpdator(100);
			us.updateUser(user);
			 
		}
		
		@Test
		//新增测试
		public void testInsert() {
			TUser user =new TUser();
			TDep dep=new TDep();
			dep.setId(7);
			user.setDept(dep);
			user.setEmail("999@163.com");
			user.setIsenabled(1);
			user.setLoginname("insert123-trans");
			user.setPassword("insert123");
			user.setPic("insert123");
			user.setRealname("insert123");
			user.setBirthday(new Date());
			user.setSex("男");
			user.setCreator(100);
			try {
				System.out.println(us.insertUser(user));
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//删除
		public void teseDelete() {
			us.delete(3099);
		}
		
		@Test
		
		public void testquery() {
			//创建查询对象
			QueryUser query=new QueryUser();
			for(TUser user:us.queryByPage(2,query)) {
				System.out.println(user.getId()+" "+user.getLoginname());
			}
			System.out.println("共"+us.queryPageCount(query)+"页");
		}
		
}
