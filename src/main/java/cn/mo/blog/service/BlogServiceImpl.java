package cn.mo.blog.service;

import cn.mo.blog.NotFoundException;
import cn.mo.blog.dao.BlogResponsitory;
import cn.mo.blog.po.Blog;
import cn.mo.blog.po.Type;
import cn.mo.blog.vo.blogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogResponsitory blogResponsitory;

    @Override
    public Blog getBlog(Long id) {
        return blogResponsitory.getOne(id);
        //查询主键
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, blogQuery blog) {
//        jpa封装高级查询,root要查询的对象，cq条件容器  cb条件比如like具体条件的表达式
        return blogResponsitory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }//搜索查询
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }//分类查询
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }//是否被推荐的查询
                cq.where(predicates.toArray(new Predicate[predicates.size()]));//多表连接查询
                //自动拼接sql,这部分要myBatis重写
                return null;

            }
        },pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogResponsitory.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b =  blogResponsitory.getOne(id);
        if(b == null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b);
//        进行对象之间属性的赋值
        return blogResponsitory.save(b);
    }

    @Override
    public void deleteBlog(Long id) {


    }
}
