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

    public boolean loginApp(String token, String pacId);

}
