package com.parpiiev.time.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name="users")
@Entity
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<UsersActivity> userActivityList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<ActivityRequest> requestList;

    public User() {
    }

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    /**
     * User constructor used for testing with params:
     * @param username describes User name
     * @param login describes User login
     * @param password describes User password
     * @param role describes User role
     */
    public User(String username, String login, String password, String role) {
        this.username = username;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<UsersActivity> getUserActivityList() {
        return userActivityList;
    }

    public void setUserActivityList(List<UsersActivity> userActivityList) {
        this.userActivityList = userActivityList;
    }

    public List<ActivityRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<ActivityRequest> requestList) {
        this.requestList = requestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, login, password, role);
    }

    @Override
    public String toString() {
        return "User {" +
                "userId=" + userId +
                ", name='" + username + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
