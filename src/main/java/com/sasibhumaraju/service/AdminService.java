package com.sasibhumaraju.service;

import com.sasibhumaraju.Main;
import com.sasibhumaraju.config.DataBaseConfig;
import com.sasibhumaraju.model.Admin;
import com.sasibhumaraju.model.AppUser;
import com.sasibhumaraju.model.Librarian;
import com.sasibhumaraju.model.LibraryBranch;
import com.sasibhumaraju.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AdminService {

    private static AdminService adminService = null;
    private AdminService() {}

    public static AdminService getAdminService() {
        adminService = adminService == null? new AdminService() : adminService;
        return adminService;
    }

    public List<Admin> getAllAdmins () {
        Session session = DataBaseConfig.getSession();

        try {
            Query<AppUser> q = session.createQuery("FROM AppUser a WHERE Type(a) = :admin",AppUser.class);
            q.setParameter("admin",Admin.class);
            List<AppUser> l = q.getResultList();
            List<Admin> admins = new LinkedList<>();
            l.forEach((AppUser appUser) -> {
                admins.add((Admin) appUser);
            });
            session.close();
            return admins;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }


    public Admin getAdminByEmail(String email) {
        Session session = DataBaseConfig.getSession();

        try {
            Query<AppUser> q = session.createQuery("FROM AppUser a WHERE TYPE(a) = :admin AND email = :email", AppUser.class);
            q.setParameter("admin",Admin.class);
            q.setParameter("email",email);
            Admin m = (Admin) q.getSingleResult();
            session.close();
            return m;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public Admin updateAdmin(Admin a) {
        Session session = DataBaseConfig.getSession();

        try {
            Transaction t = session.beginTransaction();
            session.merge(a);
            t.commit();
            session.close();
            return a;
        } catch (HibernateException e) {
            System.out.println("Some error happened! try again...");
            session.close();
            return null;
        }
    }

    public Admin deleteAdmin(Admin m) {
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

    public void main (AdminService adminService) {

        Scanner sc = new Scanner(System.in);
        Session session = DataBaseConfig.getSession();

        while(Main.admin != null) {
            System.out.println("####-- ADMIN PANEL --####");
            System.out.println("\n\n--- Display ---\n1. Profile \n2. admins list");
            System.out.println("\n\n--- Add New---\n3. Library \n4. Librarian");
            System.out.println("\n\n--- Delete ---\n5. My Library\n6. My Librarian");
            System.out.println("\n--- Logout ---\n7. Logout");
            int option = sc.nextInt();

            switch (option) {
                case 1: {
                    try {

                        Admin a = adminService.getAdminByEmail(Main.admin.getEmail());

                        System.out.println("\n\n\n--- Admin Profile ---\nName: \t\t" + a.getName() + "\nEmail: \t\t" + a.getEmail());
                        List<LibraryBranch> l = a.getLibraryBranches();
                        System.out.println("Owned Libraries:");
                        for(int i = 0; i < l.size(); i++) {
                            System.out.println(i + 1 + ") " + l.get(i).getLibraryBranchName() + ", " + l.get(i).getCity());
                            List<Librarian> lb = l.get(i).getLibrarians();
                            System.out.println("\tLibrarians:");
                            for (int j = 0; j < lb.size(); j++ ) {
                                System.out.println("\t" + (j+1) + ") " + lb.get(j).getName() + ", " + lb.get(j).getEmail());
                            }
                        }
                        System.out.println("\n\n\n");

                    } catch (Exception e) {
                        System.out.println("Profile loading error...");
                    }
                    break;
                }


                case 2: {
                    try {
                        List<Admin> admins = adminService.getAllAdmins();
                        System.out.println("\n\n\n--- All Admins List ---");
                        admins.forEach((Admin admin)->{
                            System.out.println("Name: " + admin.getName() + ", Email: " + admin.getEmail());
                        });
                        System.out.println("\n\n\n");
                    } catch (Exception e) {
                        System.out.println("Admin list failed to load...");
                    }
                    break;
                }

                case 3: {

                    try {
                        sc.nextLine();
                        System.out.print("\n\n\nEnter library name: ");
                        String libraryName = sc.nextLine();
                        System.out.println("Enter city name: ");
                        String city = sc.nextLine();

                        LibraryBranch libraryBranch = new LibraryBranch();
                        libraryBranch.setAdmin(Main.admin);
                        libraryBranch.setLibraryBranchName(libraryName);
                        libraryBranch.setCity(city);

                        Transaction t = session.beginTransaction();
                        session.persist(libraryBranch);
                        t.commit();

                        System.out.println(libraryBranch.getLibraryBranchName() + " : New library branch added...\n\n\n");
                    } catch (Exception e) {
                        System.out.println("\nFailed to add new library branch...\n\n\n");
                    }
                    break;
                }

                case 4: {

                    try {
                        sc.nextLine();
                        System.out.println("\n\n\nEnter librarian name:");
                        String librarianName = sc.nextLine();
                        System.out.println("Enter email:");
                        String email = sc.next();

                        Admin a = adminService.getAdminByEmail(Main.admin.getEmail());
                        List<LibraryBranch> l = a.getLibraryBranches();
                        System.out.println("Your libraries:");
                        for(int i = 0; i<l.size(); i++) {
                            System.out.println((i+1) +") "+ l.get(i).getLibraryBranchName());
                        }

                        System.out.println("Assign a owned library for admin (enter option number):");
                        int libraryNum = sc.nextInt();

                        Librarian librarian = new Librarian();
                        librarian.setName(librarianName);
                        librarian.setEmail(email);
                        librarian.setLibraryBranch(l.get(libraryNum-1));

                        Transaction t = session.beginTransaction();
                        session.persist(librarian);
                        t.commit();
                        System.out.println("\nLibrarian added successfully..\n\n\n");
                    } catch (Exception e) {
                        System.out.println("\nFailed to add new librarian...\n\n\n");
                    }
                    break;
                }

                case 5: {

                    try {
                        sc.nextLine();
                        Admin a = adminService.getAdminByEmail(Main.admin.getEmail());
                        List<LibraryBranch> l = a.getLibraryBranches();
                        System.out.println("\n\n\nYour libraries:");
                        for(int i = 0; i<l.size(); i++) {
                            System.out.println((i+1) +") "+ l.get(i).getLibraryBranchName());
                        }
                        System.out.println("Enter library number to delete:");
                        int libraryNum = sc.nextInt();

                        Transaction t = session.beginTransaction();
                        session.remove(l.get(libraryNum-1));
                        t.commit();

                        System.out.println(l.get(libraryNum-1).getLibraryBranchName() + " - Library deleted..");
                    } catch (Exception e) {
                        System.out.println("\nFailed to delete library...\n\n\n\n");
                    }

                    break;
                }

                case 6: {

                    try {
                        sc.nextLine();
                        Admin a = adminService.getAdminByEmail(Main.admin.getEmail());
                        List<LibraryBranch> l = a.getLibraryBranches();
                        List<Librarian> libList = new LinkedList<>();
                        System.out.println("\n\n\nYour librarians:");
                        for (LibraryBranch libraryBranch : l) {
                            List<Librarian> librarians = libraryBranch.getLibrarians();
                            libList.addAll(librarians);
                            for (int j = 0; j < librarians.size(); j++) {
                                System.out.println((j + 1) + ") " + librarians.get(j).getName() + ", " + librarians.get(j).getEmail());
                            }
                        }
                        System.out.println("Enter librarian number to delete:");
                        int librarianNum = sc.nextInt();

                        Transaction t = session.beginTransaction();
                        session.remove(libList.get(librarianNum-1));
                        t.commit();

                        System.out.println(libList.get(librarianNum-1).getName() + " - Librarian deleted..");
                    } catch (Exception e) {
                        System.out.println("\nFailed to delete library...\n\n\n\n");
                    }

                    break;
                }

                case 7: {
                    Util.logoffAppUser(Main.admin);
                    break;
                }

                default: {
                    System.out.println("\n\n\n Enter valid option.. \n\n\n");
                }


            }

        }

    }

}
