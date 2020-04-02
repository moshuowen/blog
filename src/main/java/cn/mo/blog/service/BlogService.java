package cn.mo.blog.service;

import cn.mo.blog.po.Blog;
import cn.mo.blog.vo.blogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Blog getBlog(Long id);//获取

    Page<Blog> listBlog(Pageable pageable, blogQuery blog);//分页查询，查询条件封装成blog对象

    Page<Blog> listBlog(Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id ,Blog blog);//根据id查找出要修改的对象

    void deleteBlog(Long id);

}
