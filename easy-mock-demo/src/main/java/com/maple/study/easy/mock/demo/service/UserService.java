package com.maple.study.easy.mock.demo.service;

import com.maple.study.easy.mock.demo.dao.entity.User;

public interface UserService {

	public void add(User user);

	public User login(String userName, String password);

}
