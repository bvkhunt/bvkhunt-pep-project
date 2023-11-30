package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into account (username, password) values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.username);
            preparedStatement.setString(2, account.password);
            
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int generated_user_id = (int) rs.getLong(1);
                return new Account(generated_user_id, account.username, account.password);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account getUserByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account getAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username=? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.username);
            preparedStatement.setString(2, account.password);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    public Account getAccountById(int accountId){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where account_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, accountId);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}