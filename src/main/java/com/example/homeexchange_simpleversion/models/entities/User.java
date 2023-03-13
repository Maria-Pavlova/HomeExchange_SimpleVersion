package com.example.homeexchange_simpleversion.models.entities;


import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private List<UserRole> roles;

    private String preferredDestinations;
    @OneToMany (mappedBy = "fromUser",fetch = FetchType.EAGER)
    private List<Message> sentMessages = new java.util.ArrayList<>();
    @OneToMany (mappedBy = "toUser",fetch = FetchType.EAGER)
    private List<Message> receivedMessages = new java.util.ArrayList<>();
    @Column
    private LocalDateTime created;
    @Column
    private LocalDateTime modified;
    @OneToMany(mappedBy = "owner")
    private List<Home> homes;


    @PrePersist
    public void prePersist(){
        setCreated(LocalDateTime.now());
        setModified(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(){
        setModified(LocalDateTime.now());
    }


    public LocalDateTime getCreated() {
        return created;
    }


    public User setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }


    public LocalDateTime getModified() {
        return modified;
    }


    public User setModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public User setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
        return this;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public User setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
        return this;
    }

    public User addRole(UserRole userRole){
        this.roles.add(userRole);
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public User setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public String getPreferredDestinations() {
        return preferredDestinations;
    }

    public User setPreferredDestinations(String preferredDestinations) {
        this.preferredDestinations = preferredDestinations;
        return this;
    }

    public List<Home> getHomes() {
        return homes;
    }

    public User setHomes(List<Home> homes) {
        this.homes = homes;
        return this;
    }

}
