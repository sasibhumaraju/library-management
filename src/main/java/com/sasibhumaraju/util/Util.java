package com.sasibhumaraju.util;

import com.sasibhumaraju.Main;
import com.sasibhumaraju.model.Admin;
import com.sasibhumaraju.model.AppUser;
import com.sasibhumaraju.model.Librarian;
import com.sasibhumaraju.model.Member;

public class Util {
    public static void loginAppUser (AppUser u, int type) {
        switch(type) {
            case 1 : {
                Main.admin = (Admin) u;
                Main.librarian = null;
                Main.member = null;
                break;
            }
            case 2 : {
                Main.librarian = (Librarian) u;
                Main.admin = null;
                Main.member = null;
                break;
            }
            case 3 : {
                Main.member = (Member) u;
                Main.admin = null;
                Main.librarian = null;
                break;
            }
            default:
                System.out.println("provide valid type to login\n 1 -> admin\n 2 -> librarian\n 3 -> member");
        }
    }

    public static void logoffAppUser (AppUser u) {

        Main.admin = null;
        Main.librarian = null;
        Main.member = null;

        System.out.println("\n\n\n "+ u.getName()+" Logged off successfully..\n\n\n");
    }
}
