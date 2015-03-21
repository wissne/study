package com.maple.study.easy.mock.demo.dao;

import java.util.List;

import com.maple.study.easy.mock.demo.dao.entity.User;

public interface UserDao {
	User getBy(String userName);

	void add(User user);
	
	List<User> getAll();
}
