package com.sasibhumaraju.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Cacheable
@DiscriminatorValue("Admin")
public class Admin extends AppUser {

    @OneToMany (mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<LibraryBranch> libraryBranches;

    public List<LibraryBranch> getLibraryBranches() {
        return libraryBranches;
    }

    public void setLibraryBranches(List<LibraryBranch> libraryBranches) {
        this.libraryBranches = libraryBranches;
    }
}
