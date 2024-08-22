package Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    Logger logger  = LoggerFactory.getLogger(AccountService.class);
    public AccountService(){
        accountDAO = new AccountDAO();
    }


    /**
     * Use AccountDAO to persist an account. The given Account will not have an id provided.
     * 
     * @param account an Account object.
     * @return The persisted account if the persistence is successful.
     */
    public Account addAccount(Account account) {
        try {
            return accountDAO.insertAccount(account);
        } catch (Exception e) {
            logger.info("Error in adding account in accountservice");
            return null;
        }  
    }

    /**
     * Use the AccountDAO to veryfiy account login information.
     * @return all books.
     */
    public Account verifyAccount(Account account) {
        Account target = accountDAO.getAccount(account);
        if (target == null) {
            logger.info("Account not found when verifying");
            return null;
        }
        if (target.getPassword().equals(account.getPassword())) return target;
        return null;
    }
}
