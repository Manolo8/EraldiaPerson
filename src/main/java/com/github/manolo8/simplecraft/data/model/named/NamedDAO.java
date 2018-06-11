package com.github.manolo8.simplecraft.data.model.named;

import com.github.manolo8.simplecraft.data.connection.Database;
import com.github.manolo8.simplecraft.data.model.base.BaseDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class NamedDAO<O extends NamedDTO> extends BaseDAO<O> {

    private final String findByNameQuery;

    protected NamedDAO(Database database, String name, Class<O> oClass) throws SQLException {
        super(database, name, oClass);
        findByNameQuery = "SELECT * FROM " + name + " WHERE fastName=?";
    }

    public O findByName(String name) throws SQLException {
        PreparedStatement statement = prepareStatement(findByNameQuery);

        statement.setString(1, name);

        ResultSet result = statement.executeQuery();

        O o = result.next() ? fromResult(result) : null;

        statement.close();

        return o;
    }
}
