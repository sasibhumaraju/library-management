package com.sasibhumaraju.service;

import com.sasibhumaraju.config.DataBaseConfig;
import com.sasibhumaraju.model.LibraryBranch;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LibraryService {

    private LibraryService () {}
    private static LibraryService libraryService = null;

    public static LibraryService getLibraryService() {
        libraryService = libraryService == null? new LibraryService() : libraryService;
        return libraryService;
    }

    public List<LibraryBranch> getAllLibraries() {
        Session session = DataBaseConfig.getSession();

        try {
            Query<LibraryBranch> q = session.createQuery("FROM LibraryBranch", LibraryBranch.class);
            List<LibraryBranch> l = q.getResultList();
            session.close();
            return l;

        } catch (Exception e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public List<LibraryBranch> getAllLibrariesByAdminId(String adminId) {
        Session session = DataBaseConfig.getSession();

        try {
            Query<LibraryBranch> q = session.createQuery("FROM LibraryBranch WHERE adminId = :id", LibraryBranch.class);
            q.setParameter("id", adminId);
            List<LibraryBranch> l = q.getResultList();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public List<LibraryBranch> getAllLibrariesByCity(String city) {
        Session session = DataBaseConfig.getSession();

        try {
            Query<LibraryBranch> q = session.createQuery("FROM LibraryBranch WHERE city = :c", LibraryBranch.class);
            q.setParameter("c", city);
            List<LibraryBranch> l = q.getResultList();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public LibraryBranch getLibraryByName(String name) {
        Session session = DataBaseConfig.getSession();

        try {
            Query<LibraryBranch> q = session.createQuery("FROM LibraryBranch WHERE name = :n", LibraryBranch.class);
            q.setParameter("n", name);
            LibraryBranch l = (LibraryBranch) q.getSingleResult();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public LibraryBranch createLibraryBranch(LibraryBranch l) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.persist(l);
            t.commit();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public LibraryBranch updateLibraryBranch(LibraryBranch l) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.merge(l);
            t.commit();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public LibraryBranch deleteLibraryBranch(LibraryBranch l) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.remove(l);
            t.commit();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

}
