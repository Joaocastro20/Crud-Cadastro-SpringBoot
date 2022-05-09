package aula.com.projeto.controller;


import aula.com.projeto.model.User;
import aula.com.projeto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class IndexController {


    @GetMapping
    public ModelAndView get() {
        return new ModelAndView("index")
            .addObject("datahora", LocalDateTime.now());
    }
}
