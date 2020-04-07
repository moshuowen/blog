package cn.mo.blog.web.admin;

import cn.mo.blog.po.Blog;
import cn.mo.blog.po.User;
import cn.mo.blog.service.BlogService;
import cn.mo.blog.service.TagService;
import cn.mo.blog.service.TypeService;
import cn.mo.blog.vo.blogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 6,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        blogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         blogQuery blog, Model model){
//          model.addAttribute("type",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
        //model 视图模型
        //局部刷新
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypesAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
//博客的新增
    }

    private void setTypesAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
//        重复利用模板
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
            setTypesAndTag(model);
            Blog blog = blogService.getBlog(id);
            blog.init();//在Blog中将tagIds初始化成字符
            model.addAttribute("blog",blog);
            return INPUT;
//            blog的修改
    }


//    @PostMapping("/blogs")
//    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
//        blog.setUser((User) session.getAttribute("user"));//拿到当前的登录用户
//        blog.setType(typeService.getType(blog.getType().getId()));
//        blog.setTags(tagService.listTag(blog.getTagIds()));
//        Blog b ;
//        if (blog.getId() == null){
//            b =  blogService.saveBlog(blog);
//        } else  {
//            b = blogService.updateBlog(blog.getId(),blog);
//        }
//
//        if (b == null ) {
//            attributes.addFlashAttribute("message", "BlogController操作失败");
//        } else {
//            attributes.addFlashAttribute("message", "BlogController操作成功");
//        }
//        return REDIRECT_LIST;
//    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "BlogController删除成功");
        return REDIRECT_LIST;
    }//blog的删除功能

}
