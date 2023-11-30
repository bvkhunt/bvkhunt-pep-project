package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<Message>();
        try {
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public Message getMessageById(int messageId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, messageId);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message (message_text, time_posted_epoch, posted_by) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, message.message_text);
            preparedStatement.setLong(2, message.time_posted_epoch);
            preparedStatement.setInt(3, message.posted_by);
            
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while(rs.next()){
                int generated_message_id = rs.getInt(1);
                return new Message(generated_message_id, message.posted_by, message.message_text, message.time_posted_epoch);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean deleteMessageById(int messageId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "delete from message where message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, messageId);

            int rs = preparedStatement.executeUpdate();
            
            return rs == 1;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return false;
    }
    
    public List<Message> getMessagesByUser(int userId) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "select * from message where posted_by=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public Message updateMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "update message set message_text=? where message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, message.message_text);
            preparedStatement.setInt(2, message.message_id);
            
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while(rs.next()){
                int generated_message_id = rs.getInt(1);
                return new Message(generated_message_id, message.posted_by, message.message_text, message.time_posted_epoch);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
