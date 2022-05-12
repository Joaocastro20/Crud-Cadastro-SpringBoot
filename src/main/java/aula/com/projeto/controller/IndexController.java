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
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        List<User> user = userService.findAll().stream().collect(Collectors.toList());
        mv.addObject("user", user);
        return mv;
    }
    @GetMapping("/template")
    public ModelAndView getTemplate(){
        ModelAndView mv = new ModelAndView("templateteste");
        String nome = userService.findById(1).getName();
        String data = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        mv.addObject("nome", nome)
            .addObject("data", data);
        return mv;
    }
}
