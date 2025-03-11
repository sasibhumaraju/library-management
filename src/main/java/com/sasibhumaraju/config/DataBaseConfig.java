package com.sasibhumaraju.config;

import com.sasibhumaraju.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DataBaseConfig {

    private static DataBaseConfig dataBaseConfig = null;
    private static SessionFactory sf = null;

    private DataBaseConfig() {
        sf = new Configuration()
                .addAnnotatedClass(AppUser.class)
                .addAnnotatedClass(Member.class)
                .addAnnotatedClass(Admin.class)
                .addAnnotatedClass(Librarian.class)
                .addAnnotatedClass(LibraryBranch.class)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(BorrowedBook.class)
                .configure()
                .buildSessionFactory();
    }

    public static Session getSession() {
        dataBaseConfig = dataBaseConfig == null? new DataBaseConfig() : dataBaseConfig;
        return  sf.openSession();
    }
}
