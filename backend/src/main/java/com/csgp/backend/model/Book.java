package com.csgp.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private int pages;

    @Column(nullable = false)
    private boolean read;

    @Column(nullable = false)
    private String savedDate;

    // relación manyToOne con User
    // relación con la entidad User para los posts
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    public Book() {
    }

    public Book(String title, String author, int pages, boolean read, String savedDate, User user) {
        super();
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.read = read;
        this.savedDate = savedDate;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setSavedDate(String savedDate) {
        this.savedDate = savedDate;
    }

    public String getSavedDate() {
        return savedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
