package aula.com.projeto.service;

import aula.com.projeto.model.User;
import aula.com.projeto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

}
