package cn.mo.blog.dao;

import cn.mo.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TypeResponsitory  extends JpaRepository<Type,Long> {

//    遵循命名规则查询
    Type findByName(String name);

    //按照分页去查询
//    自定义查询
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
