package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
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

    @Override
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

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        System.out.println(String.format("User с именем %s добавлен в базу данных", name));
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory();
        session.beginTransaction();
        session.createQuery(String.format("delete User where id = %s", id)).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory();
        session.beginTransaction();
        List<User> list = session.createQuery("from User").list();
        session.getTransaction().commit();
        return list;

    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory();
        session.beginTransaction();
        session.createQuery(String.format("delete User")).executeUpdate();
        session.getTransaction().commit();
    }
}
