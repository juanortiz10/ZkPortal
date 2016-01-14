package com.delarosa.portal.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Executions;

/**
 * Manages cookies
 */
public class CookieController {

    /**
     * Returns the value of the cookie with the given name
     *
     * @param name The name of the cookie
     * @return Returns the value of the cookie
     */
    public static String getCookie(String name) {
        Cookie[] cookies = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName.equals(name)) {
                    String value = cookie.getValue();
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * Sets a new cookie
     *
     * @param name The name of the cookie
     * @param value The value of the cookie
     * @param expire The number of seconds after the cookie should expire
     */
    public static void setCookie(String name, String value, int expire) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expire);
        ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(cookie);
    }
}
