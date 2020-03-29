package cn.mo.blog.dao;

import cn.mo.blog.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TypeResponsitory  extends JpaRepository<Type,Long> {

    Type findByName(String name);
//    遵循命名规则

}
