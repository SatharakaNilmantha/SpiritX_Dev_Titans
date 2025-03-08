package com.example.SpiritX_Dev_Titans_01_Server_Side.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Getter
    @javax.persistence.Id
    @Id
    private int id;
    private String username;
    private String password;



}
