package com.sasibhumaraju.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class BorrowedBook {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    String borrowedBookId;

    @Column(nullable = false)
    LocalDate time;

    @ManyToOne
    @JoinColumn ( name = "memberId")
    Member member;

    @ManyToOne
    @JoinColumn ( name = "bookId")
    Book book;

    @ManyToOne
    @JoinColumn(name = "libraryBranchId")
    LibraryBranch libraryBranch;

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }


    public String getBorrowedBookId() {
        return borrowedBookId;
    }

    public void setBorrowedBookId(String borrowedBookId) {
        this.borrowedBookId = borrowedBookId;
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
