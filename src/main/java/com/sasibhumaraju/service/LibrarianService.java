package com.sasibhumaraju.service;

import com.sasibhumaraju.config.DataBaseConfig;
import com.sasibhumaraju.model.Librarian;
import com.sasibhumaraju.model.LibraryBranch;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LibrarianService {

    private LibrarianService () {}
    private LibrarianService librarianService = null;

    public LibrarianService getLibrarianService() {
        librarianService = librarianService == null? new LibrarianService() : librarianService;
        return librarianService;
    }

    public List<Librarian> getAllLibrarians () {
        Session session = DataBaseConfig.getSession();

        try {
            Query<Librarian> q = session.createQuery("FROM AppUser a WHERE TYPE(a) = :librarian");
            q.setParameter("librarian",Librarian.class);
            List<Librarian> l = q.getResultList();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public List<Librarian> getAllLibrariansByLibraryBranchId (String libraryBranchId) {
        Session session = DataBaseConfig.getSession();

        try {
            Query<Librarian> q = session.createQuery("FROM AppUser a WHERE a.libraryBranchId = :lbi AND TYPE(a) = :librarian");
            q.setParameter("libraryBranchId", libraryBranchId);
            q.setParameter("librarian", Librarian.class);
            List<Librarian> l = q.getResultList();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public Librarian addNewLibrarian(Librarian librarian) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.persist(librarian);
            t.commit();
            session.close();
            return librarian;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public Librarian updateLibrarian(Librarian librarian) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.merge(librarian);
            t.commit();
            session.close();
            return librarian;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public Librarian deleteLibrarian( Librarian librarian) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.remove(librarian);
            t.commit();
            session.close();
            return librarian;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

}
