package com.example.apisecurity.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Token> tokens=new HashSet<>();

    public User(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public boolean removeTokenIf(Predicate<? super Token>predicate){
        return this.tokens.removeIf(predicate);
    }
    public void addToken(Token token){
        token.setUser(this);
        tokens.add(token);
    }

}
