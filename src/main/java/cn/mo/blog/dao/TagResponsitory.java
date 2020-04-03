package cn.mo.blog.dao;

import cn.mo.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TagResponsitory extends JpaRepository<Tag,Long> {

    Tag findByName(String name);
//    遵循命名规则

//
    @Query("select t from Tag t")
    List<Tag> finTop(Pageable pageable);

}
