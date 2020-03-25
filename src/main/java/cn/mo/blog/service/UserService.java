package cn.mo.blog.service;

import cn.mo.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);
}


//检查用户名和密码的接口