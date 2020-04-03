package cn.mo.blog.dao;

import cn.mo.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TypeResponsitory  extends JpaRepository<Type,Long> {

    Type findByName(String name);
//    遵循命名规则

    //按照分类去查询
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
