package aula.com.projeto.controller;

import aula.com.projeto.User;
import aula.com.projeto.UserRepository;
import aula.com.projeto.exception.NameNullException;
import aula.com.projeto.exception.NameVazioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //Pegar dados no banco
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<User> findAll(){
        return userRepository.findAll();
    }
    //deletar dados do banco
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void findAll(@PathVariable Long id){
        userRepository.deleteById(id);
    }
    //salvar dados no banco
    @RequestMapping(value = "", method = RequestMethod.POST)
    public User save(@RequestBody User user) throws NameVazioException {
        if(user.getName()==null || user.getEmail()==null)
            throw new NameNullException();
        if(user.getName()=="")
            throw new NameVazioException();
        return userRepository.save(user);
    }
    //alterar dados no banco
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public User update(@RequestBody User user){
        return userRepository.save(user);
    }



}
