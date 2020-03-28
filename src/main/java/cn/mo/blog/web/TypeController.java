package cn.mo.blog.web;


import cn.mo.blog.po.Type;
import cn.mo.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model){
        model.addAttribute("page",typeService.listtype(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(){
        return  "admin/types-input";
    }

    @PostMapping("/types")
    public String post(Type type){
        Type t1 = typeService.saveType(type);
        if(t1 == null){
            //没保存成功时
        } else {
            //保存成功时
        }
        return "redirect:/admin/types";
    }
}
//分页处理