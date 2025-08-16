package com.aleftorres.curso.services;

import com.aleftorres.curso.entities.Order;
import com.aleftorres.curso.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(Long id){
        Optional<Order> obj;
        obj = repository.findById(id);
        return obj.get();
    }
}
