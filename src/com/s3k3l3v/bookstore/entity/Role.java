package com.s3k3l3v.bookstore.entity;


public enum Role {
    ADMIN, CLIENT, LIBRARIAN;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }

}
