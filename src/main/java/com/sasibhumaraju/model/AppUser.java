package com.sasibhumaraju.model;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;


@Entity
@Cacheable
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class AppUser {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    String userId;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String name = "";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

