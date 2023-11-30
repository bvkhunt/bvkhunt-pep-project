package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        if(IsValidUsername(account.username) && IsValidPassword(account.password)) {
            if(accountDAO.getUserByUsername(account.username)==null){
                return accountDAO.insertAccount(account);
            }
            return null;
        }
        else{
            return null;
        }
    }
    
    public Account checkAccount(Account account) {
        if(IsValidUsername(account.username) && IsValidPassword(account.password)) {
            return accountDAO.getAccount(account);
        }
        else{
            return null;
        }
    }

    private boolean IsValidUsername(String str){
        if(str == null || str.length() == 0 || str.length() > 255){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean IsValidPassword(String str){
        if(str == null || str.length() < 4 || str.length() > 255){
            return false;
        }
        else{
            return true;
        }
    }
}
