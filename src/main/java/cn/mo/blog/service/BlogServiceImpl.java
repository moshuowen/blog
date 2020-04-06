package cn.mo.blog.service;

import cn.mo.blog.NotFoundException;
import cn.mo.blog.dao.BlogResponsitory;
import cn.mo.blog.po.Blog;
import cn.mo.blog.po.Type;
import cn.mo.blog.util.MarkdownUtils;
import cn.mo.blog.util.MyBeanUtils;
import cn.mo.blog.vo.blogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

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
    public Blog getAndConvert(Long id) {
        Blog blog = blogResponsitory.getOne(id);
        if( blog ==null){
            throw  new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogResponsitory.updateViews(id);
        return b;
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
                }//根据分类来查询
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
    public Page<Blog> listBlog(Pageable pageable) {
        return blogResponsitory.findAll(pageable);
    }//主页分页的数据

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogResponsitory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogResponsitory.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return  blogResponsitory.findTop(pageable);

    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogResponsitory.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogResponsitory.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogResponsitory.count();
    }



    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //如果是第一次的（null）的话就设置，否则就去updatetime
        if(blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);//初始时的浏览次数为0
        }else{
            blog.setUpdateTime(new Date());
        }
        return blogResponsitory.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b =  blogResponsitory.getOne(id);
        if(b == null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
//        进行对象之间属性的赋值，过滤空的数据
        b.setUpdateTime(new Date());
        return blogResponsitory.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogResponsitory.deleteById(id);
    }
}
