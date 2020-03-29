package cn.mo.blog.dao;

import cn.mo.blog.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagResponsitory extends JpaRepository<Tag,Long> {

    Tag findByName(String name);
//    遵循命名规则

}
