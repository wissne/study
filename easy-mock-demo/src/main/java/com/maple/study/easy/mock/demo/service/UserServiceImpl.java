package com.maple.study.easy.mock.demo.service;

import com.maple.study.easy.mock.demo.dao.UserDao;
import com.maple.study.easy.mock.demo.dao.entity.User;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserServiceImpl() {
	}

	public UserServiceImpl(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public User login(String userName, String password) {
		if (userName == null)
			throw new RuntimeException("用户名为空");
		if (password == null)
			throw new RuntimeException("密码为空");
		User loginUser = userDao.getBy(userName);
		if (loginUser == null)
			throw new RuntimeException("不存在用户");
		else if (!password.equals(loginUser.getPassword()))
			throw new RuntimeException("密码错误");
		else
			return loginUser;
	}

	@Override
	public void add(User user) {
		if (user == null)
			throw new RuntimeException("用户为空");
		else
			userDao.add(user);
	}

}
