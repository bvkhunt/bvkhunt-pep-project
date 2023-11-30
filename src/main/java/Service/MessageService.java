package Service;

import java.util.ArrayList;
import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        if(IsValidMessage(message.message_text)) {
            if(accountDAO.getAccountById(message.posted_by)!=null){
                return messageDAO.insertMessage(message);
            }
            return null;
        }
        else{
            return null;
        }
    }
    
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id){
        Message message = messageDAO.getMessageById(message_id);
        if(message != null && messageDAO.deleteMessageById(message_id)){
            return message;
        }
        return null;
    }

    public Message updateMessageById(int message_id, String updatedMessage){
        if(IsValidMessage(updatedMessage)) {
            Message message = messageDAO.getMessageById(message_id);
            if(message != null){
                message.setMessage_text(updatedMessage);
                return messageDAO.updateMessage(message);
            }
        }
        return null;
    }

    public List<Message> getMessagesByUser(int account_id){
        List<Message> messages = new ArrayList<>();
        if(accountDAO.getAccountById(account_id)!=null) {
            messages = messageDAO.getMessagesByUser(account_id);
        }
        return messages;
    }

    private boolean IsValidMessage(String str){
        if(str == null || str.length() == 0 || str.length() > 255){
            return false;
        }
        else{
            return true;
        }
    }
}
