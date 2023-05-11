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

import static controller.MainRun.accountPresent;

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
        int count1 = 0;
        int count2 = 0;

        System.out.println("Create your account ! ");
        System.out.println("Create Username: ");
        String username = scanner.nextLine();
        for (Account account: accountList){
            if (username.equals("")) {
                return null;
            }
            if (username.equals(account.getUsername())){
                System.err.println("Account already exists !");
                return null;
            }
        }

        System.out.println("Create Password: ");
        String password;
        do {
            Pattern pattern = Pattern.compile("^[A-Za-z\\d!@#$%^&*]{8,16}$");
            password = scanner.nextLine();
            Matcher matcher = pattern.matcher(password);
            if (matcher.matches()) {
                System.out.println("Valid Password address !");
                break;
            } else {
                System.err.println("Password address is not valid !");
            }
            count++;
            if (count == 3) {
                return null;
            }

        } while (true);

        System.out.println("Create FullName: ");
        String fullName = scanner.nextLine();
        if (fullName == null || fullName.equals("")) {
            return null;
        }

        System.out.println("Create Email: ");
        String email;
        do {
            Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
            email = scanner.nextLine();
            Matcher matcher1 = pattern1.matcher(email);
            if (matcher1.matches()) {
                System.out.println("Valid email address !");
                break;
            } else {
                System.err.println("Email address is not valid !");
            }
            count1++;
            if (count1 == 3) {
                return null;
            }

        } while (true);


        System.out.println("Create PhoneNumber: ");
        String phoneNumber;
        do {
            Pattern pattern2 = Pattern.compile("^(\\+84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-46-9])[0-9]{7}$");
            phoneNumber = scanner.nextLine();
            Matcher matcher2 = pattern2.matcher(phoneNumber);
            if (matcher2.matches()) {
                System.out.println("Valid phone number");
                break;
            } else {
                System.err.println("invalid phone number ! ");
            }
            count2++;
            if (count2 == 3) {
                return null;
            }
        } while (true);


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
        Account account = getById();
            if (account != null) {
                System.out.println("Change username:");
                String username = scanner.nextLine();
                boolean check = false;
                for (Account account1: accountList){
                    if (!username.equals("") && !username.equals(account1.getUsername())) {
                        check = true;
                        account.setUsername(username);

                    }
                    if (!check){
                        System.err.println("Account already exists !");
                        break;
                    }
                }

                System.out.println("Change password:");
                Pattern pattern = Pattern.compile("^[A-Za-z\\d!@#$%^&*]{8,16}$");
                String password = scanner.nextLine();
                Matcher matcher = pattern.matcher(password);
                boolean flag2;
                if (matcher.matches()) {
                    flag2 = true;
                    System.out.println("Valid password number");
                } else {
                    flag2 = false;
                    System.out.println("invalid password number ! ");
                }
                if (flag2) {
                    account.setPassword(password);
                }

                System.out.println("Change fullName:");
                String fullName = scanner.nextLine();
                if (!fullName.equals("")) {
                    account.setFullName(fullName);
                }

                System.out.println("Change email:");
                Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
                String email = scanner.nextLine();
                Matcher matcher1 = pattern1.matcher(email);
                boolean flag;
                if (matcher1.matches()) {
                    flag = true;
                    System.out.println("Valid email address !");
                } else {
                    flag = false;
                    System.out.println("Email address is not valid !");
                }
                if (flag) {
                   account.setEmail(email);
                }

                System.out.println("Change phoneNumber:");
                Pattern pattern2 = Pattern.compile("^(\\+84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-46-9])[0-9]{7}$");
                String phoneNumber = scanner.nextLine();
                Matcher matcher2 = pattern2.matcher(phoneNumber);
                boolean flag1;
                if (matcher2.matches()) {
                    flag1 = true;
                    System.out.println("Valid phone number");
                } else {
                    flag1 = false;
                    System.out.println("invalid phone number ! ");
                }
                if (flag1) {
                    account.setPhoneNumber(phoneNumber);
                }

                System.out.println("Change address:");
                String address = scanner.nextLine();
                if (!address.equals("")) {
                   account.setAddress(address);
                }

                System.out.println("Change role:");
                account.setRole(getRole());
            }

        System.out.println("Congratulations on your successful update !");
        write(accountList, PATH_FILE);
        return account;
    }


    public void displayUser() {
        System.out.println("Your profile:....... ");
        for (Account acc : accountList) {
            if (accountPresent.getId() == acc.getId()) {
                System.out.printf("%-20s%-20s%-20s%-30s%-30s%-20s%-15s%s",
                        "Id", "Username", "Password", "FullName", "Email", "PhoneNumber", "Address", "Role\n");
                acc.display();
            }
        }
    }

    public void updateUser() {
        System.out.println("update your account !");
        for (Account account: accountList) {
            if (accountPresent.getId() == account.getId()) {

                System.out.println("Change password:");
                Pattern pattern = Pattern.compile("^[A-Za-z\\d!@#$%^&*]{8,16}$");
                String password = scanner.nextLine();
                Matcher matcher = pattern.matcher(password);
                boolean flag3;
                if (matcher.matches()) {
                    flag3 = true;
                    System.out.println("Valid password address !");
                } else {
                    flag3 = false;
                    System.out.println("Password is not valid !");
                }
                if (flag3) {
                    account.setPassword(password);
                }


                System.out.println("Change fullName:");
                String fullName = scanner.nextLine();
                if (!fullName.equals("")) {
                    account.setFullName(fullName);
                }

                System.out.println("Change email:");
                Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
                String email = scanner.nextLine();
                Matcher matcher1 = pattern1.matcher(email);
                boolean flag;
                if (matcher1.matches()) {
                    flag = true;
                    System.out.println("Valid email address !");
                } else {
                    flag = false;
                    System.out.println("Email address is not valid !");
                }
                if (flag) {
                    account.setEmail(email);
                }

                System.out.println("Change phoneNumber:");
                Pattern pattern2 = Pattern.compile("^(\\+84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-46-9])[0-9]{7}$");
                String phoneNumber = scanner.nextLine();
                Matcher matcher2 = pattern2.matcher(phoneNumber);
                boolean flag1;
                if (matcher2.matches()) {
                    flag1 = true;
                    System.out.println("Valid phone number");
                } else {
                    flag1 = false;
                    System.out.println("invalid phone number ! ");
                }
                if (flag1) {
                    account.setPhoneNumber(phoneNumber);
                }

                System.out.println("Change address:");
                String address = scanner.nextLine();
                if (!address.equals("")) {
                    account.setAddress(address);
                }
            }
        }
        System.out.println("Congratulations on your successful update !");
        write(accountList, PATH_FILE);
    }

//    public Account deleteUserAccount(){
//        System.out.println("delete your account !");
//        for (Account account: accountList){
//            if (accountPresent.getId()==account.getId()){
//                accountList.remove(account);
//                System.out.println("you have successfully deleted account:" + account.getUsername());
//                break;
//            }
//        }
//        write(accountList, PATH_FILE);
//        return MainRun.loginAccount(scanner, manageAccount, manageProduct, manageColor);
//    }


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
        for (Account account : accountList) {
            if (searchById == account.getId()) {
                check = true;
                System.out.printf("%-20s%-20s%-20s%-30s%-30s%-20s%-15s%s",
                        "Id", "Username", "Password", "FullName", "Email", "PhoneNumber", "Address", "Role\n");
                account.display();
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
        write(accountList, PATH_FILE);
    }

    public void sortById() {
        accountList.sort(new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {

                return o1.getId() - o2.getId();
            }
        });
        displayAll();
        write(accountList, PATH_FILE);
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
