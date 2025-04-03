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
    private static BorrowedBookService borrowedBookService = null;

    public static BorrowedBookService getBorrowedBookService () {
        borrowedBookService = borrowedBookService == null? new BorrowedBookService() : borrowedBookService;
        return borrowedBookService;
    }

    public List<BorrowedBook> getAllBorrowedBooks () {
        Session session = DataBaseConfig.getSession();

        try {
            Query<BorrowedBook> q = session.createQuery("FROM BorrowedBook", BorrowedBook.class);
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
            Query<BorrowedBook> q = session.createQuery("FROM BorrowedBook WHERE libraryBranchId = :lbi", BorrowedBook.class);
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
            Query<BorrowedBook> q = session.createQuery("FROM BorrowedBook WHERE memberId = :mi", BorrowedBook.class);
            q.setParameter("mi",memberId);
            List<BorrowedBook> l = q.getResultList();
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

    public List<BorrowedBook> getActiveBorrowedBooksByLibraryBranchId(String libraryBranchId) {

        Session session = DataBaseConfig.getSession();
        try {
            Query<BorrowedBook> q = session.createQuery("FROM BorrowedBook WHERE isCleared = :ic AND libraryBranchId = :lid", BorrowedBook.class);
            q.setParameter("ic", false);
            q.setParameter("lid",libraryBranchId);
            List<BorrowedBook> borrowedBooks = q.getResultList();
            session.close();
            return borrowedBooks;
        } catch (HibernateException e) {
            session.close();
            System.out.println("Failed to fetch active borrowed books...");
            return null;
        }
    }



}
