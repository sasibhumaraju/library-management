package com.sasibhumaraju.service;

import com.sasibhumaraju.config.DataBaseConfig;
import com.sasibhumaraju.model.Librarian;
import com.sasibhumaraju.model.LibraryBranch;
import com.sasibhumaraju.model.Member;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MemberService {

    private MemberService() {}
    private MemberService memberService = null;

    public MemberService getMemberService() {
        memberService = memberService == null? new MemberService() : memberService;
        return memberService;
    }

    public List<Member> getAllMembers () {
        Session session = DataBaseConfig.getSession();

        try {
            Query<Member> q = session.createQuery("FROM AppUser a WHERE TYPE(a) = :member");
            q.setParameter("member",Member.class);
            List<Member> l = q.getResultList();
            session.close();
            return l;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public Member getMemberByEmail(String email) {
        Session session = DataBaseConfig.getSession();

        try {
            Query<Member> q = session.createQuery("FROM AppUser a WHERE TYPE(a) = :member AND email = :email");
            q.setParameter("member",Member.class);
            q.setParameter("email",email);
            Member m = q.getSingleResult();
            session.close();
            return m;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
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
}
