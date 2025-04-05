package com.sasibhumaraju.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Cacheable
public class Book {

    @Id
    @GeneratedValue ( strategy = GenerationType.UUID )
    String bookId;

    @Column(nullable = false)
    String bookName;

    @Column(nullable = false)
    String category;

    @Column(nullable = false)
    String author;

    @Column(nullable = false)
    int count = 0;

    @Column(nullable = false)
    int lendCount = 0;

    @ManyToOne
    @JoinColumn ( name = "libraryBranchId" )
    LibraryBranch libraryBranch;

    @ManyToOne
    @JoinColumn ( name = "librarianId")
    Librarian librarian;

    @OneToMany ( mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<BorrowedBook> borrowedBooks;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLendCount() {
        return lendCount;
    }

    public void setLendCount(int lendCount) {
        this.lendCount = lendCount;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LibraryBranch getLibraryBranch() {
        return libraryBranch;
    }

    public void setLibraryBranch(LibraryBranch libraryBranch) {
        this.libraryBranch = libraryBranch;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
