package com.sasibhumaraju.service;

import com.sasibhumaraju.Main;
import com.sasibhumaraju.config.DataBaseConfig;
import com.sasibhumaraju.model.*;
import com.sasibhumaraju.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LibrarianService {

    private LibrarianService () {}
    private static LibrarianService librarianService = null;

    public static LibrarianService getLibrarianService() {
        librarianService = librarianService == null? new LibrarianService() : librarianService;
        return librarianService;
    }

    public Librarian getLibrarianByEmail(String email) {

        Session session = DataBaseConfig.getSession();

        try {
            Query<AppUser> q = session.createQuery("FROM AppUser a WHERE TYPE(a) = :librarian AND email = :email", AppUser.class);
            q.setParameter("email",email);
            q.setParameter("librarian",Librarian.class);
            Librarian librarian = (Librarian) q.getSingleResult();
            session.close();
            return librarian;
        } catch (HibernateException e) {
            System.out.println("Fetching librarian failed...");
            session.close();
            return null;
        }
    }

    public List<Librarian> getAllLibrarians () {
        Session session = DataBaseConfig.getSession();

        try {
            Query<AppUser> q = session.createQuery("FROM AppUser a WHERE TYPE(a) = :librarian",AppUser.class);
            q.setParameter("librarian",Librarian.class);
            List<AppUser> l = q.getResultList();
            List<Librarian> librarians = new LinkedList<>();
            l.forEach((AppUser a) -> {
                librarians.add((Librarian) a);
            });
            session.close();
            return librarians;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public List<Librarian> getAllLibrariansByLibraryBranchId (String libraryBranchId) {
        Session session = DataBaseConfig.getSession();

        try {
            Query<AppUser> q = session.createQuery("FROM AppUser a WHERE a.libraryBranchId = :lbi AND TYPE(a) = :librarian",AppUser.class);
            q.setParameter("libraryBranchId", libraryBranchId);
            q.setParameter("librarian", Librarian.class);
            List<AppUser> l = q.getResultList();
            List<Librarian> librarians = new LinkedList<>();
            l.forEach((AppUser a) -> {
                librarians.add((Librarian) a);
            });
            session.close();
            return librarians;
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

    public void main (LibrarianService librarianService) {

        Scanner sc = new Scanner(System.in);
        Session session = DataBaseConfig.getSession();

        while(Main.librarian != null) {

            System.out.println("####-- LIBRARIAN PANEL --####");
            System.out.println("\n\n--- Display ---\n1. Profile \n2. All members list \n3. Member profile \n4. Books in the library \n5. Active b   orrowed Books in library \n6. Due Borrows \n7. Borrowed books history");
            System.out.println("\n\n--- Add New---\n8. Member \n9. Book");
            System.out.println("\n\n--- Update ---\n10. Update book count\n11. Take book from member \n12. Give book to member");
            System.out.println("\n--- Logout ---\n13. Logout");
            int option = sc.nextInt();

            switch(option) {

                case 1: {

                    try {
                        Librarian librarian = librarianService.getLibrarianByEmail(Main.librarian.getEmail());
                        System.out.println("\n\n\n--- Librarian Profile ---\nname: \t\t" + librarian.getName() + "\nemail: \t\t" + librarian.getEmail() + "\nWorking in library branch: \t\t" + librarian.getLibraryBranch().getLibraryBranchName());

                        List<Book> books = librarian.getBooks();
                        System.out.printf("\nYou added below books to library: (count = %d)\n",books.size());
                        for( int i = 0; i < books.size(); i++) {
                            System.out.println((i+1) + ") " + books.get(i).getBookName());
                        }
                        System.out.println("\n\n\n");
                    } catch (Exception e) {
                        System.out.println("Profile fetching error...\n\n\n");
                    }
                    break;
                }

                case 2: {

                    try {
                        MemberService memberService = MemberService.getMemberService();
                        List<Member> members = memberService.getAllMembers();
                        System.out.printf("\n\n\nMembers List (count = %d)",members.size());
                        for(int i = 0; i<members.size(); i++) {
                            System.out.println((i+1) + ") Name: " + members.get(i).getName() + ", Email: " + members.get(i).getEmail() + ", Borrowed Count: " + members.get(i).getBorrowedCount() );
                        }
                        System.out.println("\n\n\n");
                    } catch (Exception e) {
                        System.out.println("Fetching members failed...\n\n\n");
                    }
                    break;
                }

                case 3: {

                    try {
                        System.out.println("\n\n\nEnter member email id: ");
                        String email = sc.next();
                        MemberService memberService = MemberService.getMemberService();
                        Member member = memberService.getMemberByEmail(email);
                        BorrowedBookService borrowedBookService = BorrowedBookService.getBorrowedBookService();
                        List<BorrowedBook> borrowedBooks = borrowedBookService.getAllBorrowedBooksByMemberId(member.getUserId());
                        System.out.println("Name: " + member.getName() + ", Email: " + member.getEmail() + ", Borrowed Count: " + member.getBorrowedCount());
                        System.out.printf("\nList of borrowed books: (count = %d)\n",borrowedBooks.size());
                        for(int i = 0; i<borrowedBooks.size(); i++) {
                            System.out.println((i+1) + ") " + "Book Name: " + borrowedBooks.get(i).getBook().getBookName() + ", Date: " + borrowedBooks.get(i).getDueDate().toString() + ", Is Cleared: " + borrowedBooks.get(i).isCleared());
                        }
                        System.out.println("\n\n\n");
                    } catch (Exception e) {
                        System.out.println("Fetching member data failed...\n\n\n");
                    }

                    break;
                }

                case 4: {

                    try {
                        BookService bookService = BookService.getBookService();
                        List<Book> books = bookService.getAllBooksByLibraryBranch(Main.librarian.getLibraryBranch().getLibraryBranchId());
                        System.out.println("\n\n\nBooks in the Library " + Main.librarian.getLibraryBranch().getLibraryBranchName() + " :");
                        for(int i = 0; i < books.size(); i++)
                            System.out.println((i+1) + ") Name: " + books.get(i).getBookName() + ", Author: " + books.get(i).getAuthor() + ", Count: "  + books.get(i).getCount() + ", Lend Count: " + books.get(i).getLendCount() + ", Librarian: " + books.get(i).getLibrarian());
                        System.out.println("\n\n\n");
                    } catch (Exception e) {
                        System.out.println("Failed to fetch books data...\n\n\n");
                    }

                    break;
                }

                case 5: {

                    try {
                        BorrowedBookService borrowedBookService = BorrowedBookService.getBorrowedBookService();
                        List<BorrowedBook> borrowedBooks = borrowedBookService.getActiveBorrowedBooksByLibraryBranchId(Main.librarian.getLibraryBranch().getLibraryBranchId());
                        System.out.printf("\n\n\nActive borrowed books in the library " + Main.librarian.getLibraryBranch().getLibraryBranchName() + " (count = %d) :", borrowedBooks.size());
                        for(int i = 0; i<borrowedBooks.size(); i++) {
                            System.out.println((i+1) + ") " + "Book Name: " + borrowedBooks.get(i).getBook().getBookName() + ", Date: " + borrowedBooks.get(i).getDueDate().toString() + ", Member Name: " + borrowedBooks.get(i).getMember().getName() + ", Member Email: " + borrowedBooks.get(i).getMember().getEmail());
                        }
                        System.out.println("\n\n\n");
                    } catch (Exception e) {
                        System.out.println("Failed to fetch borrowed books...\n\n\n");
                    }
                    break;
                }

                case 6: {

                    try {
                        BorrowedBookService borrowedBookService = BorrowedBookService.getBorrowedBookService();
                        List<BorrowedBook> borrowedBooks = borrowedBookService.getActiveBorrowedBooksByLibraryBranchId(Main.librarian.getLibraryBranch().getLibraryBranchId());
                        System.out.println("\n\n\nAll Due Borrows in Library " + Main.librarian.getLibraryBranch().getLibraryBranchName() + ": ");
                        List<BorrowedBook> dueBorrowedBooks = new LinkedList<>();
                        for (BorrowedBook borrowedBook : borrowedBooks) {
                            if (borrowedBook.getDueDate().isBefore(LocalDate.now())) dueBorrowedBooks.add(borrowedBook);
                        }
                        for (int i = 0; i<dueBorrowedBooks.size(); i++)
                            System.out.println((i+1) + ") " + "Book Name: " + dueBorrowedBooks.get(i).getBook().getBookName() + ", Date: " + dueBorrowedBooks.get(i).getDueDate().toString() + ", Member Name: " + dueBorrowedBooks.get(i).getMember().getName() + ", Member Email: " + dueBorrowedBooks.get(i).getMember().getEmail());
                        System.out.println("\n\n\n");
                    } catch (Exception e) {
                        System.out.println("Failed to fetch due borrowed books..\n\n\n");
                    }
                    break;
                }

                case 7: {
                    try {
                        BorrowedBookService borrowedBookService = BorrowedBookService.getBorrowedBookService();
                        List<BorrowedBook> borrowedBooks = borrowedBookService.getAllBorrowedBooksByLibraryBranchId(Main.librarian.getLibraryBranch().getLibraryBranchId());
                        System.out.printf("\n\n\nAll Borrowed Books History (count = %d",borrowedBooks.size());
                        for(int i = 0; i<borrowedBooks.size(); i++) {
                            BorrowedBook b = borrowedBooks.get(i);
                            System.out.printf("\n%d) Book Name: %s, Author: %s, Member Name: %s, Is Cleared: %b, Due Date: %s\n", (i+1), b.getBook().getBookName(), b.getBook().getAuthor(), b.getMember().getName(),b.isCleared(), b.getDueDate().toString());
                        }
                        System.out.println("\n\n\n");
                    } catch (Exception e) {
                        System.out.println("Failed to fetch borrowed books history..\n\n\n");
                    }
                    break;
                }

                case 8: {
                    try {
                        sc.nextLine();
                        System.out.println("\n\n\n To Add New Member Give Following Input..");
                        System.out.println("Enter Member Name:");
                        String name = sc.nextLine();
                        System.out.println("Enter Email: ");
                        String email = sc.next();
                        Member member = new Member();
                        member.setBorrowedCount(0);
                        member.setName(name);
                        member.setEmail(email);
                        MemberService memberService = MemberService.getMemberService();
                        memberService.addNewMember(member);
                        System.out.println("\n\n\n");
                    } catch (Exception e) {
                        System.out.println("Failed to add new member..\n\n\n");
                    }
                    break;
                }

                case 9: {

                    try {
                        sc.nextLine();
                        System.out.println("\n\n\n To Add New Book Give Following Input...");
                        System.out.println("Enter Book Name: ");
                        String bookName = sc.nextLine();
                        System.out.println("Enter Book Author: ");
                        String author = sc.nextLine();
                        System.out.println("Enter Book Category: ");
                        String category = sc.nextLine();
                        System.out.println("Enter Number of Books adding: ");
                        int count = sc.nextInt();
                        int lendCount = 0;
                        Book book = new Book();
                        book.setBookName(bookName);
                        book.setAuthor(author);
                        book.setCategory(category);
                        book.setCount(count);
                        book.setLendCount(0);
                        book.setLibrarian(Main.librarian);
                        book.setLibraryBranch(Main.librarian.getLibraryBranch());
                        BookService bookService = BookService.getBookService();
                        Book b = bookService.addNewBook(book);
                        System.out.println(b.getBookName() + " added..\n\n\n");
                    } catch (Exception e) {
                        System.out.println("Failed to add new book..\n\n\n");
                    }
                    break;
                }

                case 10: {
                    sc.nextLine();

                    try {
                        System.out.println("Enter book name which to be updated: ");
                        Book b = BookService.getBookService().getBookByName(sc.nextLine());
                        System.out.println("Old book count: "+b.getCount());
                        System.out.println("\nEnter new book count: ");
                        int c = sc.nextInt();
                        if(c < b.getLendCount()) System.out.println("sorry you not possible to update count which less than lend count");
                        else {
                            b.setCount(c);
                            BookService.getBookService().updateBook(b);
                            System.out.println("updated...\n\n\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Failed to update...\n\n\n");
                    }

                    break;
                }

                case 11: {
                    sc.nextLine();

                    try {
                        System.out.println("First enter member email to give book:");
                        String memberEmail = sc.nextLine();
                        MemberService memberService = MemberService.getMemberService();
                        Member member = memberService.getMemberByEmail(memberEmail);
                        if(member == null) System.out.println("Member not found..");
                        else if( member.getBorrowedCount() == 3) System.out.println(member.getName() + " has already taken 3 books (limit is 3) not possible to give new book..");
                        else {
                            System.out.println("Enter book name to give to member");
                            String bookName = sc.nextLine();
                            BookService bookService = BookService.getBookService();
                            Book book = bookService.getBookByName(bookName);
                            if(book == null) System.out.println("Book not found..");
                            else if (book.getLendCount() >= book.getCount()) System.out.println("No enough books to give..");
                            else {
                                List<BorrowedBook> borrowedBooks = member.getBorrowedBooks();
                                BorrowedBook borrowedBook = new BorrowedBook();
                                borrowedBook.setBook(book);
                                borrowedBook.setCleared(false);
                                borrowedBook.setLibraryBranch(Main.librarian.getLibraryBranch());
                                borrowedBook.setMember(member);
                                LocalDate now = LocalDate.now();
                                LocalDate dueDate = now.plusDays(15);
                                borrowedBook.setDueDate(dueDate);
                                BorrowedBookService borrowedBookService = BorrowedBookService.getBorrowedBookService();
                                BorrowedBook borrowedBook1 = borrowedBookService.addNewBorrowedBook(borrowedBook);
                                System.out.println(borrowedBook1.getBook().getBookName() + "Book given to member Due date is: " + dueDate.toString());
                                member.setBorrowedCount(member.getBorrowedCount()+1);
                                Member m = memberService.updateMember(member);
                                System.out.println(m.getName() + " updated...\n\n\n");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Failed to give book to member..\n\n\n");
                    }
                    break;
                }

                case 12: {
                    sc.nextLine();

                    try {
                        System.out.println("First enter member email to give book:");
                        String memberEmail = sc.next();
                        MemberService memberService = MemberService.getMemberService();
                        Member member = memberService.getMemberByEmail(memberEmail);
                        if(member == null) System.out.println("Member not found..");
                        else {
                            List<BorrowedBook> borrowedBooks = member.getBorrowedBooks();
                            for(int i = 0; i<borrowedBooks.size(); i++) {
                                BorrowedBook b = borrowedBooks.get(i);
                                LocalDate now = LocalDate.now();
                                System.out.println((i+1) + ") " + b.getBook().getBookName() + " Due: " + Period.between(now,b.getDueDate()).getDays() + " remaining..");
                            }
                            System.out.println("Enter book number to take:");
                            int num = sc.nextInt();

                            Book book = borrowedBooks.get(num).getBook();
                            book.setLendCount(book.getLendCount()-1);
                            borrowedBooks.remove(num);

                            member.setBorrowedCount(member.getBorrowedCount()-1);
                            memberService.updateMember(member);

                            BookService bookService = BookService.getBookService();
                            Book b = bookService.updateBook(book);
                            System.out.println(b.getBookName() + "updated..");

                            Member m = memberService.updateMember(member);
                            System.out.println(m.getName() + "updated..\n\n\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Failed to take book from member..\n\n\n");
                    }
                    break;
                }

                case 13: {
                    sc.nextLine();

                    Util.logoffAppUser(Main.librarian);

                    break;
                }

            }

        }
    }

}
