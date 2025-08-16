package com.aleftorres.curso.services;

import com.aleftorres.curso.entities.User;
import com.aleftorres.curso.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id){
        Optional<User> obj;
        obj = repository.findById(id);
        return obj.get();
    }
}
