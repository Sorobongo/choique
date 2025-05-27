package com.northpole.snow.application.seguridad;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import com.northpole.snow.application.seguridad.ui.view.LoginView;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private final VaadinServiceInitListener errorHandlerInitializer;

    SecurityConfig(VaadinServiceInitListener errorHandlerInitializer) {
        this.errorHandlerInitializer = errorHandlerInitializer;
    }
	   @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        super.configure(http); 	        
	        setLoginView(http, LoginView.class, "/logged-out.html");
	        // TODO Configure the login view
	    }

	    @Bean
	    public UserDetailsManager userDetailsManager() {
	        LoggerFactory.getLogger(SecurityConfig.class)
	            .warn("NOT FOR PRODUCITON: Using in-memory user details manager!"); 


	        var user = User.withUsername("user")
	                .password("{noop}user")
	                .roles("USER")
	                .build();
	        var admin = User.withUsername("admin")
	                .password("{noop}admin")
	                .roles("ADMIN")
	                .build();
	        return new InMemoryUserDetailsManager(user, admin);
	    }
}
