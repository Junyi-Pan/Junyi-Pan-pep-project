package Service;

import java.util.List;
import DAO.MessageDAO;
import Model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageService {
    private MessageDAO messageDAO;
    Logger logger  = LoggerFactory.getLogger(MessageService.class);

    public MessageService(){
        this.messageDAO = new MessageDAO();
    }
    /**
     * Use MessageDAO to persist an message. The given Message will not have an id provided.
     * 
     * @param message an Message object.
     * @return The persisted message if the persistence is successful.
     */
    public Message addMessage(Message message) {
        try {
            return messageDAO.insertMessage(message);
        } catch (Exception e) {
            logger.info("Error in adding message in messageService");
            return null;
        }  
    }

    /**
     * Use the messageDAO to retrieve all messages.
     * @return all messages.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Use the messageDAO to retrieve a message.
     * @return a message.
     */
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    /**
     * Use the messageDAO to delete a message.
     * @return deleted message.
     */
    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    /**
     * Use the messageDAO to delete a message.
     * @return updated message.
     */
    public Message updateMessageById(int message_id, Message message) {
        return messageDAO.updateMessageById(message_id, message);
    }

    /**
     * Use the messageDAO to retrieve all messages of a specific account.
     * @return all messages from account identified by account_id.
     */
    public List<Message> getMessagesByAccount(int account_id) {
        return messageDAO.getMessagesByAccount(account_id);
    }
}
