package com.example.marcelo.instagramclone.models;

public class UsersSettings {
    private Users users;
    private UsersAccountSettings usersAccountSettings;

    public UsersSettings(Users users, UsersAccountSettings usersAccountSettings) {
        this.users = users;
        this.usersAccountSettings = usersAccountSettings;
    }

    public UsersSettings() {

    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public UsersAccountSettings getUsersAccountSettings() {
        return usersAccountSettings;
    }

    public void setUsersAccountSettings(UsersAccountSettings usersAccountSettings) {
        this.usersAccountSettings = usersAccountSettings;
    }

    @Override
    public String toString() {
        return "UsersSettings{" +
                "users=" + users +
                ", usersAccountSettings=" + usersAccountSettings +
                '}';
    }
}
