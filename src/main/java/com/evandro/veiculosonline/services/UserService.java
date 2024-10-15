package com.evandro.veiculosonline.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evandro.veiculosonline.models.User;
import com.evandro.veiculosonline.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository ur;

    public void create(User user) {
        this.ur.save(user);
    }
}
