package com.delarosa.portal.authentication;

import java.util.Map;
import org.zkoss.essentials.services.AuthenticationService;
import org.zkoss.essentials.services.UserCredential;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

/**
 *
 * @author Omar
 */
public class AuthenticationInit implements Initiator {

    private final AuthenticationService authService = new TokenAuthenticationService();

    @Override
    public void doInit(Page page, Map<String, Object> map) throws Exception {
        UserCredential cre = authService.getUserCredential();
        if (cre == null || cre.isAnonymous()) {
            Executions.sendRedirect("/index.zul");
        }
    }

}
