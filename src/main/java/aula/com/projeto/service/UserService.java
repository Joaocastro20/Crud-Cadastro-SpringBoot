package aula.com.projeto.service;

import aula.com.projeto.model.User;
import org.springframework.stereotype.Service;


public interface UserService {
    User findById(long id);

}
