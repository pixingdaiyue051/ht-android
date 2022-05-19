package com.tequeno.rdm;

public class User {

    private Long _id;
    private String name;

    public Long get_id() {
        return _id;
    }

    public User set_id(Long _id) {
        this._id = _id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                '}';
    }
}
