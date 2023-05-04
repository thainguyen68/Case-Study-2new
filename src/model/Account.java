package model;

public class Account {
    static final long serialVersionUID = 1L;
    public static int idUp;
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Role role;

    public Account() {
    }

    public Account(String username, String password, String fullName, String email, String phoneNumber, String address, Role role) {
        this.id = ++idUp;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public Account(int id, String username, String password, String fullName, String email, String phoneNumber, String address, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;

    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static int getIdUp() {
        return idUp;
    }

    public static void setIdUp(int idUp) {
        Account.idUp = idUp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return id +
                "," + username +
                "," + password +
                "," + fullName +
                "," + email +
                "," + phoneNumber +
                "," + address +
                "," + role.getName();
    }

    public void display() {
        System.out.printf("%-20s%-20s%-20s%-30s%-30s%-20s%-15s%s",
                this.id, this.username, this.password, this.fullName, this.email, this.phoneNumber, this.address , this.role.getName() + "\n");
    }
}
