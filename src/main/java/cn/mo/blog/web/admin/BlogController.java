package cn.mo.blog.web.admin;

import cn.mo.blog.po.Blog;
import cn.mo.blog.po.Type;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
    public String blogs(@PageableDefault(size = 2,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        blogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         blogQuery blog, Model model){
//        model.addAttribute("type",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "/admin/blogs :: blogList";
    }

//model 视图模型
//局部刷新

    @GetMapping("/blogs/input")
    public String input(Model model){
            model.addAttribute("types",typeService.listType());
            model.addAttribute("tags",tagService.listTag());
            model.addAttribute("blog",new Blog());
            return INPUT;

    }

}
