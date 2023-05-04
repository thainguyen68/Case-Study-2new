package service.impl;

import ioFile.IOFile;
import model.Role;
import service.Manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManageRole implements Manage<Role>, IOFile<Role> {
    private final Scanner scanner;
    private final List<Role> roleList = new ArrayList<>();

    public ManageRole(Scanner scanner) {
        Role role1 = new Role(1, "Admin");
        Role role2 = new Role(2, "User");
        roleList.add(role1);
        roleList.add(role2);
        this.scanner = scanner;
    }


    @Override
    public void write(List<Role> e, String path) {

    }

    @Override
    public List<Role> read(String path) {
        return null;
    }

    @Override
    public Role create() {
        return null;
    }

    @Override
    public Role update() {
        return null;
    }

    @Override
    public Role delete() {
        return null;
    }

    @Override
    public Role getById() {
        displayAll();
        int id ;
        do {
            try {
                System.out.println("Input id you want to find: ");
                id = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Have error, please try again!");
            }

        } while (true);

        for (Role r : roleList) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void displayAll() {
        for (Role role : roleList) {
            System.out.println(role);
        }
    }

    public Role UserPlayer() {
        int id = Integer.parseInt(String.valueOf(2));
        for (Role r : roleList) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }
}
