package org.example.userauthservice_may2026.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserSession extends BaseModel {
    private String token;
    @ManyToOne
    private User user;
}
//
//1            M
//user        session
//1             1
//
//
//1 : m