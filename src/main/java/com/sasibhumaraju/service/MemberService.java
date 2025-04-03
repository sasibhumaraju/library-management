package com.sasibhumaraju.service;

import com.sasibhumaraju.Main;
import com.sasibhumaraju.config.DataBaseConfig;
import com.sasibhumaraju.model.AppUser;
import com.sasibhumaraju.model.BorrowedBook;
import com.sasibhumaraju.model.Member;
import com.sasibhumaraju.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MemberService {

    private MemberService() {}
    private static MemberService memberService = null;

    public static MemberService getMemberService() {
        memberService = memberService == null? new MemberService() : memberService;
        return memberService;
    }

    public void addNewMember (Member member) {
        Session session = DataBaseConfig.getSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.persist(member);
            transaction.commit();
            session.close();
            System.out.println("Member added successfully..");
        } catch (Exception e) {
            System.out.println("Failed to add new member..");
            session.close();

        }
    }

    public List<Member> getAllMembers () {
        Session session = DataBaseConfig.getSession();

        try {
            Query<AppUser> q = session.createQuery("FROM AppUser a WHERE TYPE(a) = :member",AppUser.class);
            q.setParameter("member",Member.class);
            List<AppUser> l = q.getResultList();
            List<Member> members = new LinkedList<>();
            l.forEach((AppUser u) -> members.add((Member) u));
            session.close();
            return members;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public Member getMemberByEmail(String email) {
        Session session = DataBaseConfig.getSession();

        try {
            Query<AppUser> q = session.createQuery("FROM AppUser a WHERE TYPE(a) = :member AND email = :email",AppUser.class);
            q.setParameter("member",Member.class);
            q.setParameter("email",email);
            Member m = (Member) q.getSingleResult();
            session.close();
            return m;
        } catch (HibernateException e) {
            System.out.println("Fetching member error..");
            session.close();
            return null;
        }
    }

    public Member updateMember(Member m) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.merge(m);
            t.commit();
            session.close();
            return m;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public Member deleteMember(Member m) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.remove(m);
            t.commit();
            session.close();
            return m;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public void main () {
        Scanner sc = new Scanner(System.in);
        Session session = DataBaseConfig.getSession();

        while(Main.member != null) {
            System.out.println("### --- Member Panel --- ###");
            System.out.println("\n1. View Profile \n2. Logoff");
            int option = sc.nextInt();

            switch (option) {

                case 1: {
                    try {
                        Member member = getMemberByEmail(Main.member.getEmail());
                        System.out.println("--- Profile ---");
                        System.out.println("Name: " + member.getName());
                        System.out.println("Email: " + member.getEmail());
                        System.out.println("Borrowed Count: " + member.getBorrowedCount());
                        List<BorrowedBook> borrowedBooks = member.getBorrowedBooks();
                        System.out.println("Borrowed Books:");
                        for (int i = 0; i < borrowedBooks.size(); i++) {
                            BorrowedBook borrowedBook = borrowedBooks.get(i);
                            System.out.println((i+1) + ") Book name:"+ borrowedBook.getBook().getBookName() + ", Due Date: " +
                                    borrowedBook.getDueDate().toString() + ", Library name: " +
                                    borrowedBook.getLibraryBranch().getLibraryBranchName());
                        }
                        System.out.println("\n\n\n");
                    } catch (Exception e) {
                        System.out.println("Failed to show profile..");
                    }

                    break;
                }

                case 2: {

                    Util.logoffAppUser(Main.librarian);

                    break;
                }

            }
        }
    }
}
