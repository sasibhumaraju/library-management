package com.sasibhumaraju;

import com.sasibhumaraju.config.DataBaseConfig;
import com.sasibhumaraju.model.*;
import com.sasibhumaraju.service.AdminService;
import com.sasibhumaraju.service.LibrarianService;
import com.sasibhumaraju.service.LoginService;
import com.sasibhumaraju.service.MemberService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;


public class Main {

    public static Admin admin = null;
    public static Librarian librarian = null;
    public static Member member = null;

    public static void main(String[] args) {

        Session session = DataBaseConfig.getSession();
        Scanner sc = new Scanner(System.in);
        LoginService loginService = LoginService.getLoginService();

        List<AppUser> admins = session.createQuery("FROM AppUser", AppUser.class).list();
//        System.out.println(admin1);
        if (admins.isEmpty())
        {
            Admin a = new Admin();
            a.setName("cop");
            a.setEmail("k@gmail.com");
//            a.setUserId("adminId1234");

            LibraryBranch lb = new LibraryBranch();
            lb.setAdmin(a);
            lb.setCity("kurnool");
            lb.setLibraryBranchName("The Great Library");
//            lb.setLibraryBranchId("libraryId1234");
            a.setLibraryBranches(List.of(lb));

            Librarian librarian1 = new Librarian();
            librarian1.setName("sasi");
            librarian1.setEmail("sasi@gmail.com");
            librarian1.setLibraryBranch(lb);
//            librarian1.setUserId("librarianId1234");

            Librarian librarian2 = new Librarian();
            librarian2.setName("laxmi");
            librarian2.setEmail("laxmi@gmail.com");
            librarian2.setLibraryBranch(lb);
//            librarian2.setUserId("librarianId5678");

            lb.setLibrarians(List.of(librarian1, librarian2));

            Transaction t = session.beginTransaction();
            session.persist(a); // ← FIXED: using merge instead of persist
            t.commit();
            System.out.println("\nSample data inserted into database!");
        }

        System.out.println("Hello user! Welcome to Library");

        while(true) {
            System.out.println("Select below one of actions:");

//            System.out.println(Main.admin);
//            System.out.println(Main.librarian);
//            System.out.println(Main.member);

            System.out.println("-----");
            if( admin == null && librarian == null && member == null ) {
                System.out.println("Login as \n1.Admin \n2.Librarian \n3.Member \n4.Exit");
                int option = sc.nextInt();
                if((option < 4) && (option >= 1)) {

                    System.out.println("enter email:");
                    String email = sc.next();
                    System.out.println("enter name");
                    String name = sc.next();

                    switch (option) {
                        case 1:
                            loginService.loginAdmin(email, name);
                            break;
                        case 2:
                            loginService.loginLibrarian(email, name);
                            break;
                        case 3:
                            loginService.loginMember(email, name);
                            break;
                        default:
                            System.out.println("enter valid option..");
                    }
                }
            } else {
                if( member != null) {
                    MemberService memberService = MemberService.getMemberService();
                    memberService.main();
                }
                if( librarian != null ) {
                    LibrarianService librarianService = LibrarianService.getLibrarianService();
                    librarianService.main(librarianService);
                }
                if( admin != null ) {
                    AdminService adminService = AdminService.getAdminService();
                    adminService.main(adminService);
                }
//                sc.next();
            }
        }


    }
}