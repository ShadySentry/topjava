package ru.javawebinar.topjava.model;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    public static Role getFromString(String text){
        for(Role role: Role.values()){
            if(role.name().equals(text)){
                return role;
            }
        }
        return null;
    }
}