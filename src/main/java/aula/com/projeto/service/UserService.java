package aula.com.projeto.service;

import aula.com.projeto.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    User findById(long id);
    List<User> findAll();

}
