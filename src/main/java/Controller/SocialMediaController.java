package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //Register
        app.post("/register", this::registerHandoler);
        //Login
        app.post("/login", this::loginHandoler);
        //New message
        app.post("/messages", this::postMessageHandoler);
        //List all message
        app.get("/messages", this::getAllMessagesHandler);
        //Specific message based on id
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        //Delete message
        app.delete("/messages/{message_id}", this::deletMessageHandler);
        //Update message
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        //List all message of an account
        app.get("accounts/{account_id}/messages", this::getMessagesByAccountHandler);
        return app;
    }
    /**
     * Handler to post a new account.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into an Account object.
     * If AccountService returns a null account (meaning posting an Account was unsuccessful), the API will return a 400
     * message (Client error).
     * @param context the context object handles information HTTP requests and generates responses within Javalin.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void registerHandoler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
        }else{
            context.status(400);
        }
    }

    /**
     * Handler to verify an account.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into an Account object.
     * If AccountService returns a null account (verification was unsuccessful), the API will return a 401
     * message (Unauthorized).
     * @param context the context object handles information HTTP requests and generates responses within Javalin.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void loginHandoler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account verifiedAccount = accountService.verifyAccount(account);
        if(verifiedAccount!=null){
            context.json(mapper.writeValueAsString(verifiedAccount));
        }else{
            context.status(401);
        }
    }

    /**
     * Handler to post a message.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into an Message object.
     * If MessageService returns a null message (meaning posting an Message was unsuccessful), the API will return a 400
     * message (Client error).
     * @param context the context object handles information HTTP requests and generates responses within Javalin.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postMessageHandoler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            context.json(mapper.writeValueAsString(addedMessage));
        }else{
            context.status(400);
        }
    }

    /**
     * Handler to retrieve all messages.
     * Response body will be empty if messages do not exist.
     * @param context the context object handles information HTTP requests and generates responses within Javalin.
     */
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    /**
     * Handler to retrieve the message with the message_id in path parameter.
     * Reponse body will be empty if message does not exist.
     * @param context the context object handles information HTTP requests and generates responses within Javalin.
     */
    private void getMessageByIdHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        if (message != null) context.json(message);
        else context.json("");
    }

    /**
     * Handler to delete the message with the message_id in path parameter.
     * Reponse body will be empty if message does not exist.
     * @param context the context object handles information HTTP requests and generates responses within Javalin.
     */
    private void deletMessageHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessageById(message_id);
        if (message != null) context.json(message);
        else context.json("");
    }

    /**
     * Handler to update the message with the message_id in path parameter.
     * If MessageService returns a null message (meaning posting an Message was unsuccessful), the API will return a 400
     * message (Client error).
     * @param context the context object handles information HTTP requests and generates responses within Javalin.
     */
    private void updateMessageHandler(Context context) throws JsonProcessingException {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message updatedMessage = messageService.updateMessageById(message_id, message);
        if(updatedMessage!=null){
            context.json(mapper.writeValueAsString(updatedMessage));
        }else{
            context.status(400);
        }
    }

    /**
     * Handler to retrive all the message from the account based on the account_id in path parameter.
     * Reponse body will be empty if message does not exist.
     * @param context the context object handles information HTTP requests and generates responses within Javalin.
     */
    private void getMessagesByAccountHandler(Context context) throws JsonProcessingException {
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccount(account_id);
        context.json(messages);
    }
}