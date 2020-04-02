package cn.mo.blog.dao;

import cn.mo.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BlogResponsitory extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {


    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);


}
//JpaSpecificationExecutor封装了高级查询