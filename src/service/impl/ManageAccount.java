package service.impl;

import ioFile.IOFile;
import model.Account;
import model.Role;
import service.Manage;
import service.ManageFind;
import service.ManageSort;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static controller.MainRun.result;

public class ManageAccount implements Manage<Account>, IOFile<Account>, ManageFind, ManageSort {
    private final Scanner scanner;
    private List<Account> accountList = new ArrayList<>();
    private final ManageRole manageRole;

    private final String PATH_FILE = "E:\\Case-Study-2\\src\\data\\Accounts.txt";

    public ManageAccount(Scanner scanner, ManageRole manageRole) {
        this.scanner = scanner;
        this.manageRole = manageRole;
//        accPush();
        accountList = read(PATH_FILE);
        checkDefaultIndex();
    }

    private void checkDefaultIndex() {
        if (accountList.isEmpty()) {
            Account.idUp = 0;
        } else {
            Account.idUp = accountList.get(accountList.size() - 1).getId();
        }
    }

    public void accPush() {
        Account account1 = new Account("Admin", "123", "Admin", "nvt.689@gmail.com", "0379452820", "Hà Nội", new Role("Admin"));
        Account account2 = new Account("thai456", "456", "Nguyễn Văn Thái 2", "vtn.689@gmail.com", "0166889988", "Hồ Chí Minh", new Role("User"));
        Account account3 = new Account("thai789", "789", "Nguyễn Văn Thái 3", "tvn.689@gmail.com", "0996868839", "Đà Nẵng", new Role("User"));
        Account account4 = new Account("thainguyen68", "6899", "Nguyễn Văn Thái", "tvn.689@gmail.com", "03789654321", "Đà Nẵng", new Role("User"));
        accountList.add(account1);
        accountList.add(account2);
        accountList.add(account3);
        accountList.add(account4);
        write(accountList, PATH_FILE);
    }

    //------------------------------------------------------------------------------------------------------->

    public Account login(Account account) {
        for (Account acc : accountList) {
            if (acc.getUsername().equals(account.getUsername()) && acc.getPassword().equals(account.getPassword())) {
                return acc;
            }
        }
        return null;
    }

    public void register(Account account) {
        accountList.add(account);
        write(accountList, PATH_FILE);
    }

//------------------------------------------------------------------------------------------------------->


    @Override
    public Account create() {
        int count = 0;

        System.out.println("Create your account ! ");
        System.out.println("Create Username: ");
        String username = scanner.nextLine();
        if (username == null || username.equals("")) {
            return null;
        }

        System.out.println("Create Password: ");
        String password = scanner.nextLine();
        if (password == null || password.equals("")) {
            return null;
        }
        System.out.println("Create FullName: ");
        String fullName = scanner.nextLine();
        if (fullName == null || fullName.equals("")) {
            return null;
        }

        System.out.println("Create Email: ");
        String email;
        boolean checkEmail;
        do {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
            email = scanner.nextLine();
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                checkEmail = true;
                System.out.println("Valid email address !");
            } else {
                checkEmail = false;
                System.err.println("Email address is not valid !");
            }
            if (count == 3) {
                return null;
            }
            count++;
        } while (!checkEmail);


        System.out.println("Create PhoneNumber: ");
        String phoneNumber;
        boolean checkPhoneNumber;
        do {
            Pattern pattern1 = Pattern.compile("^(\\+84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-46-9])[0-9]{7}$");
            phoneNumber = scanner.nextLine();
            Matcher matcher1 = pattern1.matcher(phoneNumber);
            if (matcher1.matches()) {
                checkPhoneNumber = true;
                System.out.println("Valid phone number");
            } else {
                checkPhoneNumber = false;
                System.err.println("invalid phone number ! ");
            }
            if (count == 4) {
                return null;
            }
            count++;
        } while (!checkPhoneNumber);


        System.out.println("Create Address: ");
        String address = scanner.nextLine();
        if (address == null || address.equals("")) {
            return null;
        }

        Role role = userRole(); //Role new Acc : auto user!
        Account account = new Account(username, password, fullName, email, phoneNumber, address, role);
        System.out.println("Congratulations, you have successfully created a new account ! ");
        register(account);
        return new Account();
    }


    private Role userRole() {
        System.out.println("Role: User");
        return manageRole.UserPlayer();
    }

    private Role getRole() {
        System.out.println("Choice role...");
        Role role;
        do {
            role = manageRole.getById();
        } while (role == null);
        return role;
    }

    @Override
    public Account update() {
        System.out.println("update your account !");
        System.out.println("Enter id:");
        int idUpdate = -1;
        try {
            idUpdate = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Please re-enter the number!");
        }

        int Index = 0;
        for (int i = 0; i < accountList.size(); i++) {
            if (idUpdate == accountList.get(i).getId()) {
                Index = i;
                System.out.println("username:");
                String username = scanner.nextLine();
                if (!username.equals("")) {
                    accountList.get(i).setUsername(username);
                }

                System.out.println("Change password:");
                String password = scanner.nextLine();
                if (!username.equals("")) {
                    accountList.get(i).setPassword(password);
                }

                System.out.println("fullName:");
                String fullName = scanner.nextLine();
                if (!fullName.equals("")) {
                    accountList.get(i).setFullName(fullName);
                }

                System.out.println("email:");
                Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
                String email = scanner.nextLine();
                Matcher matcher = pattern.matcher(email);
                boolean flag;
                if (matcher.matches()) {
                    flag = true;
                    System.out.println("Valid email address !");
                } else {
                    flag = false;
                    System.out.println("Email address is not valid !");
                }
                if (flag) {
                    accountList.get(i).setEmail(email);
                }

                System.out.println("phoneNumber:");
                Pattern pattern1 = Pattern.compile("^(\\+84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-46-9])[0-9]{7}$");
                String phoneNumber = scanner.nextLine();
                Matcher matcher1 = pattern1.matcher(phoneNumber);
                boolean flag1;
                if (matcher1.matches()) {
                    flag1 = true;
                    System.out.println("Valid phone number");
                } else {
                    flag1 = false;
                    System.out.println("invalid phone number ! ");
                }
                if (flag1) {
                    accountList.get(i).setPhoneNumber(phoneNumber);
                }

                System.out.println("address:");
                String address = scanner.nextLine();
                if (!address.equals("")) {
                    accountList.get(i).setAddress(address);
                }

                System.out.println("role:");
                accountList.get(i).setRole(getRole());
            }
        }
        System.out.println("Congratulations on your successful update !");
        write(accountList, PATH_FILE);
        return accountList.get(Index);
    }


    public void displayUser() {
        System.out.println("Your profile:....... ");
        for (Account acc : accountList) {
            if (result.getId() == acc.getId()) {
                System.out.printf("%-20s%-20s%-20s%-30s%-30s%-20s%-15s%s",
                        "Id", "Username", "Password", "FullName", "Email", "PhoneNumber", "Address", "Role\n");
                acc.display();
            }
        }
    }

    public Account updateUser() {
        System.out.println("update your account !");
        int Index = 0;
        for (int i = 0; i < accountList.size(); i++) {
            if (result.getRole().getId() == accountList.get(i).getId()) {
                Index = i;
                System.out.println("Change password:");
                String password = scanner.nextLine();
                if (!password.equals("")) {
                    accountList.get(i).setPassword(password);
                }

                System.out.println("fullName:");
                String fullName = scanner.nextLine();
                if (!fullName.equals("")) {
                    accountList.get(i).setFullName(fullName);
                }

                System.out.println("email:");
                Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
                String email = scanner.nextLine();
                Matcher matcher = pattern.matcher(email);
                boolean flag;
                if (matcher.matches()) {
                    flag = true;
                    System.out.println("Valid email address !");
                } else {
                    flag = false;
                    System.out.println("Email address is not valid !");
                }
                if (flag) {
                    accountList.get(i).setEmail(email);
                }

                System.out.println("phoneNumber:");
                Pattern pattern1 = Pattern.compile("^(\\+84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-46-9])[0-9]{7}$");
                String phoneNumber = scanner.nextLine();
                Matcher matcher1 = pattern1.matcher(phoneNumber);
                boolean flag1;
                if (matcher1.matches()) {
                    flag1 = true;
                    System.out.println("Valid phone number");
                } else {
                    flag1 = false;
                    System.out.println("invalid phone number ! ");
                }
                if (flag1) {
                    accountList.get(i).setPhoneNumber(phoneNumber);
                }

                System.out.println("address:");
                String address = scanner.nextLine();
                if (!address.equals("")) {
                    accountList.get(i).setAddress(address);
                }
            }
        }
        System.out.println("Congratulations on your successful update !");
        write(accountList, PATH_FILE);
        return accountList.get(Index);
    }


    @Override
    public Account delete() {
        Account account = getById();
        if (account != null) {
            accountList.remove(account);
        }
        displayOne(account);
        write(accountList, PATH_FILE);
        return account;
    }

    @Override
    public Account getById() {
        displayAll();
        System.out.println("Input id you want: ");
        int id = -1;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please re-enter the number!");
        }
        for (Account account : accountList) {
            if (account.getId() == id) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void displayAll() {
        System.out.printf("%-20s%-20s%-20s%-30s%-30s%-20s%-15s%s",
                "Id", "Username", "Password", "FullName", "Email", "PhoneNumber", "Address", "Role\n");
        for (Account a : accountList) {
            a.display();
        }
    }

    public void displayOne(Account account) {
        if (account != null) {
            account.display();
        } else {
            System.out.println("Not exist this object!");
        }
    }

    public void searchById() {
        System.out.println("Enter id of accounts you want to find: ");
        int searchById = -1;
        try {
            searchById = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Please re-enter the number!");
        }
        boolean check = false;
        for (int i = 0; i < accountList.size(); i++) {
            if (searchById == accountList.get(i).getId()) {
                check = true;
                System.out.printf("%-20s%-20s%-20s%-30s%-30s%-20s%-15s%s",
                        "Id", "Username", "Password", "FullName", "Email", "PhoneNumber", "Address", "Role\n");
                accountList.get(i).display();
            }
        }
        if (!check){
            System.err.println("not found");
        }
    }


    public void searchByName() {
        System.out.println("Enter username of accounts you want to find: ");
        String nameSearch = scanner.nextLine();
        boolean flag = false;
        System.out.printf("%-20s%-20s%-20s%-30s%-30s%-20s%-15s%s",
                "Id", "Username", "Password", "FullName", "Email", "PhoneNumber", "Address", "Role\n");
        for (Account account : accountList) {
            if (account.getUsername().toUpperCase().contains(nameSearch.toUpperCase())) {
                account.display();
                flag = true;
            }
        }
        if (!flag) {
            System.err.println("not found...");
        }
    }

    public void sortByName() {
        accountList.sort(new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });
        displayAll();
    }


    @Override
    public void write(List<Account> e, String path) {
        File file = new File(path);
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Account a : e) {
                bufferedWriter.write(a.toString() + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
    }

    @Override
    public List<Account> read(String path) {
        File file = new File(path);
        List<Account> accountList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                String[] strings = data.split(",");
                Account accountNew = new Account(Integer.parseInt(strings[0]), strings[1], strings[2], strings[3], strings[4], strings[5], strings[6], new Role(strings[7]));
                accountList.add(accountNew);
            }
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
        return accountList;
    }
}
