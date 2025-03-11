package com.sasibhumaraju;

import com.sasibhumaraju.config.DataBaseConfig;
import com.sasibhumaraju.model.*;
import com.sasibhumaraju.service.LoginService;
import org.hibernate.Session;

import java.util.Scanner;


public class Main {

    public static Admin admin = null;
    public static Librarian librarian = null;
    public static Member member = null;

    public static void main(String[] args) {

        Session session = DataBaseConfig.getSession();
        Scanner sc = new Scanner(System.in);
        LoginService loginService = LoginService.getLoginService();


        System.out.println("Hello user! Welcome to Library");

        while(true) {
            System.out.println("Select below one of actions:");

            if( admin == null && librarian == null && member == null ) {
                System.out.println("Login as \n1.Admin \n2.Librarian \n3.Member \n4.Exit");
                int option = sc.nextInt();
                if(option == 4) break;

                System.out.println("enter email:");
                String email = sc.next();
                System.out.println("enter name");
                String name = sc.next();

                switch (option) {
                    case 1: loginService.loginAdmin(email,name); break;
                    case 2: loginService.loginLibrarian(email,name); break;
                    case 3: loginService.loginMember(email,name); break;
                    default:
                        System.out.println("enter valid option..");
                }

            } else {

            }

            sc.next();


        }

      
    }
}