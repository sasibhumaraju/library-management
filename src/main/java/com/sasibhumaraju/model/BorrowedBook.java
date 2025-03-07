package com.sasibhumaraju.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class BorrowedBook {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    String brBookId;

    @Column(nullable = false)
    LocalDate time;

    @ManyToOne
    @JoinColumn ( name = "memberId")
    Member member;

    @ManyToOne
    @JoinColumn ( name = "bookId")
    Book book;

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }


    public String getBrBookId() {
        return brBookId;
    }

    public void setBrBookId(String brBookId) {
        this.brBookId = brBookId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
