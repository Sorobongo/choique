package com.northpole.snow.application.seguridad.ui.view;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;

import jakarta.annotation.security.PermitAll;

@Route("logout")
//@Menu(order = 10, icon = "vaadin:clipboard-check", title = "Logout")
@PermitAll 
public class LogoutView extends Main {

    public LogoutView(AuthenticationContext authenticationContext) { 

        add(new Button("Logout", event -> authenticationContext.logout()));
    }
}