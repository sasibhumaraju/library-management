package com.sasibhumaraju.service;

import com.sasibhumaraju.config.DataBaseConfig;
import com.sasibhumaraju.model.BorrowedBook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BorrowedBookService {

    private BorrowedBookService () {}
    private BorrowedBookService borrowedBookService = null;

    public BorrowedBookService getBorrowedBookService () {
        borrowedBookService = borrowedBookService == null? new BorrowedBookService() : borrowedBookService;
        return borrowedBookService;
    }

    public List<BorrowedBook> getAllBorrowedBooks () {
        Session session = DataBaseConfig.getSession();

        try {
            Query<BorrowedBook> q = session.createQuery("FROM BorrowedBook");
            List<BorrowedBook> l = q.getResultList();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public List<BorrowedBook> getAllBorrowedBooksByLibraryBranchId (String libraryBranchId) {
        Session session = DataBaseConfig.getSession();

        try {
            Query<BorrowedBook> q = session.createQuery("FROM BorrowedBook WHERE libraryBranchId = :lbi");
            List<BorrowedBook> l = q.getResultList();
            q.setParameter("lbi",libraryBranchId);
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public List<BorrowedBook> getAllBorrowedBooksByMemberId (String memberId) {
        Session session = DataBaseConfig.getSession();

        try {
            Query<BorrowedBook> q = session.createQuery("FROM BorrowedBook WHERE memberId = :mi");
            List<BorrowedBook> l = q.getResultList();
            q.setParameter("mi",memberId);
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public BorrowedBook addNewBorrowedBook(BorrowedBook borrowedBook) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.persist(borrowedBook);
            t.commit();
            session.close();
            return borrowedBook;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public BorrowedBook updateBorrowedBook(BorrowedBook borrowedBook) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.merge(borrowedBook);
            t.commit();
            session.close();
            return borrowedBook;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public BorrowedBook deleteBorrowedBook(BorrowedBook borrowedBook) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.remove(borrowedBook);
            t.commit();
            session.close();
            return borrowedBook;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

}
