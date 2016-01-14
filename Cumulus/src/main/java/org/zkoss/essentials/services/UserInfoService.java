package org.zkoss.essentials.services;

import org.zkoss.essentials.entity.User;

public interface UserInfoService {

    /**
     * find user by account
     *
     * @param account
     * @return
     */
    public User findUser(String account);

    /**
     * update user
     *
     * @param user
     * @return
     */
    public User updateUser(User user);
}
