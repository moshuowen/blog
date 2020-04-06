package cn.mo.blog.service;

import cn.mo.blog.po.Blog;
import cn.mo.blog.vo.blogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);//获取

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, blogQuery blog);//分页查询，查询条件封装成blog对象

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id ,Blog blog);//根据id查找出要修改的对象

    void deleteBlog(Long id);

}
