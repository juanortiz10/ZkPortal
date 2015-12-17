package org.zkoss.essentials.services;

public interface AuthenticationService {

    /**
     * login with account and password
     *
     *
     * @param account
     * @param password
     * @return
     */
    public boolean login(String account, String password);

    /**
     * login with account and password
     *
     *
     * @param account
     * @param password
     * @return
     */
    public boolean loginApp(String account, String password, String pacId);
    
    /**
     * logout current user*
     */
    public void logout();

    /**
     * get current user credential
     *
     *
     * @return
     */
    public UserCredential getUserCredential();

}
