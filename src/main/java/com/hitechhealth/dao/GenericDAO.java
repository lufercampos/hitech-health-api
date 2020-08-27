package com.hitechhealth.dao;

import java.sql.Connection;

public class GenericDAO {
    protected Connection conn = null;

    public GenericDAO(Connection conn) {
        this.conn = conn;
    }
}
