package com.sasibhumaraju;

import com.sasibhumaraju.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        SessionFactory sf = new Configuration()
                .addAnnotatedClass(AppUser.class)
                .addAnnotatedClass(Member.class)
                .addAnnotatedClass(Librarian.class)
                .addAnnotatedClass(Admin.class)
                .addAnnotatedClass(LibraryBranch.class)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(BorrowedBook.class)
                .configure()
                .buildSessionFactory();

        Session session = sf.openSession();

        System.out.println("Hello user! Welcome to Library");
        System.out.println("Select below one action:");
        System.out.println("1. Login");

        sf.close();
    }
}