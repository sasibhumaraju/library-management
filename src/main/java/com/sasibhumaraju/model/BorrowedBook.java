package com.sasibhumaraju.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class BorrowedBook {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    String borrowedBookId;

    @Column(nullable = false)
    boolean isCleared = false;

    @Column(nullable = false)
    LocalDate dueDate;

    @ManyToOne
    @JoinColumn ( name = "memberId")
    Member member;

    @ManyToOne
    @JoinColumn ( name = "bookId")
    Book book;

    @ManyToOne
    @JoinColumn(name = "libraryBranchId")
    LibraryBranch libraryBranch;

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

    public boolean isCleared() {
        return isCleared;
    }

    public void setCleared(boolean cleared) {
        isCleared = cleared;
    }

    public LibraryBranch getLibraryBranch() {
        return libraryBranch;
    }

    public void setLibraryBranch(LibraryBranch libraryBranch) {
        this.libraryBranch = libraryBranch;
    }
}
