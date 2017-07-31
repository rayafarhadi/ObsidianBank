package com.bank.bankingapp.maps;

import java.io.Serializable;

public class RoleValues implements Serializable {
    private static final long serialVersionUID = -4636864195653872385L;
    private int id;
    private String name;

    /**
     * Constructor. Initializes the role id and name.
     *
     * @param id   The role id
     * @param name The role name
     */
    public RoleValues(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the role id
     *
     * @return The role id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the role name
     *
     * @return The role name
     */
    public String getName() {
        return name;
    }

}
