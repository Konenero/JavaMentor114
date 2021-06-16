package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String myTableName = "CREATE TABLE users ("
                + "id INT NOT NULL AUTO_INCREMENT,"
                + " name VARCHAR(10),"
                + "lastName VARCHAR(10),"
                + " age INT,"
                + " PRIMARY KEY(id));";
        try {
            Connection con = Util.setConnection();
            Statement st = con.createStatement();
            int res = st.executeUpdate(myTableName);
            con.close();
            st.close();
        } catch (SQLException e) {
        }
    }

    public void dropUsersTable() {
        try {
            Connection con = Util.setConnection();
            Statement st = con.createStatement();
            int res = st.executeUpdate("drop table users");
            con.close();
            st.close();
        } catch (SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try {
            Connection con = Util.setConnection();
            Statement st = con.createStatement();
            int res = st.executeUpdate(String.format("INSERT INTO users ("
                            + "name, lastName, age) "
                            + "VALUES ('%s', '%s', '%s');"
                    ,name, lastName, age));
            con.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("User с именем %s добавлен в базу данных", name));
    }

    public void removeUserById(long id) {
        try {
            Connection con = Util.setConnection();
            Statement st = con.prepareStatement(String
                    .format("delete from users where id = %s", id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        try {
            Connection con = Util.setConnection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM users");
            while (res.next()) {
                User user = new User(res.getString("name"),
                        res.getString("lastName"),
                        res.getByte("age"));
                listUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        try {
            Connection con = Util.setConnection();
            Statement st = con.createStatement();
            int res = st.executeUpdate("truncate table users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}