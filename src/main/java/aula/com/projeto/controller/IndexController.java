package aula.com.projeto.controller;


import aula.com.projeto.model.User;
import aula.com.projeto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    UserService userService;


    @GetMapping("test/{id}")
    public ModelAndView get(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("index");
        String user = userService.findById(id).getName();
        String useremail = userService.findById(id).getEmail();
        mv.addObject("usuario", user)
            .addObject("email", useremail);
        return mv;

    }
    @GetMapping("/test/test")
    public ModelAndView get(){
        ModelAndView mv = new ModelAndView("teste");
        List<User> user = userService.findAll();
        mv.addObject("user", user);
        return mv;
    }

}
