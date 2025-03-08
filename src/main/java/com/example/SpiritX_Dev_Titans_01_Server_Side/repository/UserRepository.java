package com.example.SpiritX_Dev_Titans_01_Server_Side.repository;

import com.example.SpiritX_Dev_Titans_01_Server_Side.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository  extends MongoRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
