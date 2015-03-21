package com.maple.study.easy.mock.demo;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.maple.study.easy.mock.demo.dao.UserDao;
import com.maple.study.easy.mock.demo.dao.entity.User;
import com.maple.study.easy.mock.demo.service.UserService;
import com.maple.study.easy.mock.demo.service.UserServiceImpl;

public class UserServiceTest {

	private User admin;

	private UserDao userDao;

	private UserService userService;

	@Before
	public void setUp() {
		// 初始化一个用户
		admin = new User(1, "Ray", "123");
		// 创建UserDao的Mock对象
		userDao = EasyMock.createMock(UserDao.class);
		userService = new UserServiceImpl(userDao);
	}

	@Test
	public void testLogin() {
		/*
		 * 以下开始进入record阶段
		 */

		// 此时说明当在Dao中调用get方式时并且参数为Ray的时候应该返回1个用户对象给我
		EasyMock.expect(userDao.getBy("Ray")).andReturn(admin);

		/*
		 * 以下进入replay阶段
		 */

		// 将使用userDao创建的Mock对象传入
		EasyMock.replay(userDao);

		// 直接调用 接口的方法
		User loginUser = userService.login("Ray", "123");

		// 使用断言判断是否为空
		Assert.assertNotNull(loginUser);

		/*
		 * 进入verify阶段
		 */
		EasyMock.verify(userDao);
	}

	@Test
	public void testAdd() {

		/*
		 * 首先理清测试add方法的思路,先通过UserDao插入一条数据 然后通过UserDao的get方法来获取,验证是否成功插入
		 */

		/*
		 * 在EasyMock最新版本中,已经不推荐使用EasyMock.cerateMock()
		 * 的方式创建Mock对象,而是采用以下方式,以下创建方法和testGet的 效果完全一样,但注意此时创建的是StrictControl
		 */
		IMocksControl mc = EasyMock.createStrictControl();

		userDao = mc.createMock(UserDao.class);

		/*
		 * 当userDao的Mock对象是返回值,例如get(),getAll()的时候可以
		 * 使用EasyMock.expect(),但当该方法没有返回值,例如add()的时候, 应该要如何编写呢?请留意以下代码
		 */
		userDao.add(admin);
		EasyMock.expectLastCall();

		/*
		 * 为userDao注册两次事件,注意是必须的,有多少次交互,就需要有 多少次注册,每次注册都必须对应
		 */
		EasyMock.expect(userDao.getBy("Ray")).andReturn(admin);

		EasyMock.replay(userDao);

		userDao.add(admin);
		User addUser = userDao.getBy("Ray");
		Assert.assertNotNull(addUser);

		EasyMock.verify(userDao);

		/*
		 * reset可以将Control进行重置,方便MockControl的重用, 我们可以在@After中使用
		 */
		mc.reset();
	}
	
	@Test
	public void testAdd2() {
		/*
	     * 注意此处为createNiceControl,和createMockControl和createStrictMock
	     * 的分别为,在使用NiceControl时若然调用的方法没有注册,仍然可以成功调用
	     * ,不过会返回0,null,false的友好值,但不建议使用此种方式
	     */
	    IMocksControl mc = EasyMock.createNiceControl();

	    userDao = mc.createMock(UserDao.class);

	    // times代表注册2次getBy('Ray')的方法
	    EasyMock.expect(userDao.getBy("Ray")).andReturn(admin) .times(2);

	    // times代表注册2次或以上 5次或以下的方法
	    EasyMock.expect(userDao.getBy("Admin")).andReturn(admin).times(2, 5);

	    // 有时当运行某个方法需要抛出异常时,可以使用andThrow()
	    EasyMock.expect(userDao.getAll()).andThrow(
	        new NullPointerException("test"));
	    
	    EasyMock.replay(userDao);
	    
	    User addUser = userDao.getBy("Ray");
		Assert.assertNotNull(addUser);
		userDao.getBy("Ray");
		userDao.getBy("Admin");
		userDao.getBy("Admin");
		userDao.getBy("Admin");
		try {
			userDao.getAll();
		} catch (Exception e) {
			Assert.assertTrue(e instanceof NullPointerException);
		}
		EasyMock.verify(userDao);
		
		mc.reset();

	}
}
