package com.sasibhumaraju.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Cacheable
@DiscriminatorValue("Librarian")
public class Librarian extends AppUser {

    @ManyToOne
    @JoinColumn( name = "libraryBranchId")
    LibraryBranch libraryBranch;

    @OneToMany (mappedBy = "librarian", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public LibraryBranch getLibraryBranch() {
        return libraryBranch;
    }

    public void setLibraryBranch(LibraryBranch libraryBranch) {
        this.libraryBranch = libraryBranch;
    }
}
