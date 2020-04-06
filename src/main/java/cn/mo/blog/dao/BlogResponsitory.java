package cn.mo.blog.dao;

import cn.mo.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface BlogResponsitory extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

//JpaSpecificationExecutor封装了高级查询

    //查询的是推荐的博客
    //自定义查询返回一个List
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);


//    搜索的查询,传递第一个参数

//    select * from t_blog where title like '%内容%';这个地方不会加上%%
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);

}
