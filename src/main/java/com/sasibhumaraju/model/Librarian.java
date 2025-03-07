package com.sasibhumaraju.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("Librarian")
public class Librarian extends AppUser {

    @OneToMany (mappedBy = "librarian", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
