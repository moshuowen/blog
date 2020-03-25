package cn.mo.blog.service;

import cn.mo.blog.dao.UserRespository;
import cn.mo.blog.po.User;
import cn.mo.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRespository userRespository;


    @Override
    public User checkUser(String username, String password) {
        User user = userRespository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
//实现接口，查询数据库，查到就返回user
//操作数据库---->dao