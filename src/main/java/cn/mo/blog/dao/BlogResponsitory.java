package cn.mo.blog.dao;

import cn.mo.blog.po.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlogResponsitory extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {


}
//JpaSpecificationExecutor封装了高级查询