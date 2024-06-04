package cn.iyque.controller;

import cn.iyque.domain.IYqueUserCode;
import cn.iyque.service.IYqueUserCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private IYqueUserCodeService iYqueUserCodeService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }


    @RequestMapping("/add")
    public String add(){
        return "add";
    }


    @RequestMapping("/edit")
    public String edit(@RequestParam("id") Integer id, Model model){

        IYqueUserCode iYqueUserCode = iYqueUserCodeService.findIYqueUserCodeById(id);
        model.addAttribute("iYqueUserCode", iYqueUserCode);
        return "edit";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login"; // 返回登录页面名称
    }

}
