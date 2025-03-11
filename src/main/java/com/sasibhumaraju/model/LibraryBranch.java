package com.sasibhumaraju.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class LibraryBranch {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    String libraryBranchId;

    @Column(nullable = false)
    String libraryBranchName;

    @Column(nullable = false)
    String city;

    @ManyToOne
    @JoinColumn (name = "adminId")
    Admin admin;

    @OneToMany (mappedBy = "libraryBranch", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Book> books;

    @OneToMany( mappedBy = "libraryBranch", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Librarian> librarians;

    @OneToMany( mappedBy = "libraryBranch", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<BorrowedBook> borrowedBooks;

    public List<Librarian> getLibrarians() {
        return librarians;
    }

    public void setLibrarians(List<Librarian> librarians) {
        this.librarians = librarians;
    }

    public String getLibraryBranchId() {
        return libraryBranchId;
    }

    public void setLibraryBranchId(String libraryBranchId) {
        this.libraryBranchId = libraryBranchId;
    }

    public String getLibraryBranchName() {
        return libraryBranchName;
    }

    public void setLibraryBranchName(String libraryBranchName) {
        this.libraryBranchName = libraryBranchName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
