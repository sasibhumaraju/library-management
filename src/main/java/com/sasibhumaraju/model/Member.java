package com.sasibhumaraju.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Cacheable
@DiscriminatorValue("Member")
public class Member extends AppUser {

//    @Column(nullable = false)
    int borrowedCount;

    @OneToMany ( mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<BorrowedBook> borrowedBooks;

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }

    @Override
    public String toString() {
        return "Member{" +
                "borrowedCount=" + borrowedCount +
                ", borrowedBooks=" + borrowedBooks +
                "email=" + email +
                "name=" + name +
                '}';
    }


}
