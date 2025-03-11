package com.sasibhumaraju.service;


import com.sasibhumaraju.Main;
import com.sasibhumaraju.config.DataBaseConfig;
import com.sasibhumaraju.model.Admin;
import com.sasibhumaraju.model.Librarian;
import com.sasibhumaraju.model.Member;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;



public class LoginService {

    private LoginService() {}
    private static LoginService service = null;

    public static LoginService getLoginService () {
        service = service == null? new LoginService() : service;
        return service;
    }

    public void loginMember(String email, String name) {
        Session session = DataBaseConfig.getSession();

        try {
            Query q = session.createQuery("FROM AppUser a WHERE a.email = :email AND TYPE(a) = :role");
            q.setParameter("email", email);
            q.setParameter("role", Member.class); // Correct usage

            Member m = (Member) q.getSingleResult();
            Main.member = m;
            System.out.println(m);
            System.out.println("Hey! " + m.getName() + "welcome..");
            session.close();
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
        }
    }

    public void loginAdmin(String email, String name) {
        Session session = DataBaseConfig.getSession();

        try {
            Query q = session.createQuery("FROM AppUser a WHERE :email = a.email AND TYPE(a) = :role");
            q.setParameter("email",email);
            q.setParameter("role", Admin.class);
            Admin m = (Admin) q.getSingleResult();
            Main.admin = m;
            System.out.println("Hey! " + m.getName() + " welcome..");
        } catch (Exception e) {
            System.out.println("Some error happened! try again...");
        }
        session.close();
    }

    public void loginLibrarian(String email, String name) {
        Session session = DataBaseConfig.getSession();

        try {
            Query q = session.createQuery("FROM AppUser a WHERE :email = a.email AND TYPE(a) = :role");
            q.setParameter("email",email);
            q.setParameter("role", Librarian.class);
            Librarian m = (Librarian) q.getSingleResult();
            Main.librarian = m;
            System.out.println("Hey! " + m.getName() + " welcome..");
        } catch (Exception e) {
            System.out.println("Some error happened! try again...");
        }
        session.close();
    }

}
