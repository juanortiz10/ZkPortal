/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delarosa.portal.ui;

import com.delarosa.portal.authentication.TokenAuthenticationService;

/**
 *
 * @author lama
 */
public class WDiagnosticos extends CodigosCIE {

    public WDiagnosticos() {
        super();
    }

    @Override
    public String getItemName() {
        return "Diagnóstico";
    }

    @Override
    public String getRestResponseURL() {
        return "http://127.0.0.1:8000/pacientes/".concat(TokenAuthenticationService.getCurp().concat("/diagnosticos"));
    }
    
}
