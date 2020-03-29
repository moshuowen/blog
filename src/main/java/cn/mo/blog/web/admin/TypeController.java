package cn.mo.blog.web.admin;


import cn.mo.blog.po.Type;
import cn.mo.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 3,sort = {"id"},
            direction = Sort.Direction.DESC)
            Pageable pageable,
            Model model) {
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
//        分类标签页，一页条数大于三就会换页
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
//        给前端传一个type
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";

//        根据id来查询type,@PathVariable 接受id,保证一直
    }


    @PostMapping("/types")
    public String post(@Valid Type type,BindingResult result, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
//            已经存在的话，验证的Type的name
            result.rejectValue("name","nameError","TypeController不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
//            如果新增的页面有错的话，返回到输入页面
        }
//        Type t = typeService.saveType(type);
//        if (t == null ) {
//            attributes.addFlashAttribute("message", "新增失败");
//        } else {
//            attributes.addFlashAttribute("message", "新增成功");
//        }

        Type t = typeService.saveType(type);
        if(t == null ){
            attributes.addFlashAttribute("message", "新增失败");
        } else {
          attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
//新增后端的非空校验，传到前端 @Valid Type type,BindingResult result,
    }



    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
//        更改后的校验，根据id来查询type，前端会根据id来选择调用哪个post
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","editPost不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }


//    根据主键id去删除
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "TypeControllerDelete删除成功");
        return "redirect:/admin/types";
    }
}
