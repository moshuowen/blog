package cn.mo.blog.web;


import cn.mo.blog.po.Type;
import cn.mo.blog.service.BlogService;
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

import java.util.List;


@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        List<Type> types = typeService.listTypeTop(10000);//查询的条数只要只要指定的够大，就不会查不到全部
        if (id == -1) {//第一次从首页导航点进来是没有id
           id = types.get(0).getId();
        }
        blogQuery blogQuery = new blogQuery();//查询对象
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);//所有的分类
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }//实现分页查询
}
