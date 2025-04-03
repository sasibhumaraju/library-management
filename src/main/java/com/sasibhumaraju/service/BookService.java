package com.sasibhumaraju.service;

import com.sasibhumaraju.config.DataBaseConfig;
import com.sasibhumaraju.model.Book;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BookService {
    private BookService () {}
    private static BookService bookService = null;

    public static BookService getBookService() {
        bookService = bookService == null? new BookService() : bookService;
        return bookService;
    }

    public List<Book> getAllBooks () {
        Session session = DataBaseConfig.getSession();

        try {
            Query<Book> q = session.createQuery("FROM Book");
            List<Book> l = q.getResultList();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public List<Book> getAllBooksByLibraryBranch (String libraryBranch) {
        Session session = DataBaseConfig.getSession();

        try {
            Query<Book> q = session.createQuery("FROM Book WHERE libraryBranchId = :lbi",Book.class);
            q.setParameter("lid",libraryBranch);
            List<Book> l = q.getResultList();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public Book getBookByName(String name) {

        Session session = DataBaseConfig.getSession();
        try {
            Query<Book> q = session.createQuery("FROM Book where bookName = :name",Book.class);
            q.setParameter("name", name);
            Book book = q.getSingleResult();
            session.close();
            return book;
        } catch (HibernateException e) {
            System.out.println("Failed to fetch book data..");
            session.close();
            return null;
        }

    }

    public Book addNewBook(Book book) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            session.close();
            return book;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public Book updateBook(Book book) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction transaction = session.beginTransaction();
            session.merge(book);
            transaction.commit();
            session.close();
            return book;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public Book deleteBook(Book book) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction transaction = session.beginTransaction();
            session.remove(book);
            transaction.commit();
            session.close();
            return book;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }
}
