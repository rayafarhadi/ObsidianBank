package com.bank.bankingapp.maps;

import com.bank.bankingapp.databasehelper.DatabaseSelectHelper;
import com.bank.bankingapp.generics.Roles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

public class RolesMap extends EnumMap<Roles, RoleValues> {

    /**
     *
     */
    private static final long serialVersionUID = 8892066111087690455L;

    /**
     * Constructor. Initiliazes the RolesMap and updates it to the current database.
     *
     * @throws SQLException When connection to the database is lost
     */
    public RolesMap() throws SQLException {
        super(Roles.class);
        update();
    }

    /**
     * Returns a list of all role ids
     *
     * @return A list of all role ids
     */
    public List<Integer> getIds() {
        Collection<RoleValues> values = values();
        List<Integer> ids = new ArrayList<Integer>();
        for (RoleValues value : values) {
            ids.add(value.getId());
        }
        return ids;
    }

    /**
     * Returns a list of all role names
     *
     * @return A list of all role names
     */
    public List<String> getNames() {
        Collection<RoleValues> values = values();
        List<String> names = new ArrayList<String>();
        for (RoleValues value : values) {
            names.add(value.getName());
        }
        return names;
    }

    /**
     * Goes through the database and pairs all Roles with their corresponding information in the
     * database
     *
     * @throws SQLException When connection with the database is lost
     */
    public void update() throws SQLException {
        int id = 1;

        for (Roles role : Roles.values()) {
            put(role, new RoleValues(id, DatabaseSelectHelper.getRole(id)));
            id++;
        }
    }

}
