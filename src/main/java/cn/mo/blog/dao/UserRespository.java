package cn.mo.blog.dao;

import cn.mo.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);
    //遵循命名规则，根据 user password查询
}



//操作数据库的接口，dao操作的主键是user 使用jpa,增删改查
