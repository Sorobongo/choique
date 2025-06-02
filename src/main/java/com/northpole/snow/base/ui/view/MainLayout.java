package com.northpole.snow.base.ui.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.spring.security.AuthenticationContext;

import jakarta.annotation.security.PermitAll;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

import java.util.ResourceBundle;

import com.northpole.snow.autenticacion.AccessControl;
import com.northpole.snow.autenticacion.AccessControlFactory;


@Layout
@PermitAll // When security is enabled, allow all authenticated users
public final class MainLayout extends AppLayout {

    private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("MockDataWords", UI.getCurrent().getLocale());

    private final Button logoutButton;

    private final AuthenticationContext authenticationContext; 



//    public MainLayout(AuthenticationContext authenticationContext) { 
//
//
//        this.authenticationContext = authenticationContext;
//        setPrimarySection(Section.DRAWER);
//        addToDrawer(createHeader(), new Scroller(createSideNav()), createUserMenu());
//    }
//	

	public MainLayout(AuthenticationContext authenticationContext) {
//        setPrimarySection(Section.DRAWER);
        //addToDrawer(createHeader(), new Scroller(createSideNav()), createUserMenu());
        this.authenticationContext = authenticationContext;
        //setPrimarySection(Section.DRAWER);
        addToDrawer(new Scroller(createSideNav()), createUserMenu());
        addToNavbar(createHeader());
        logoutButton = createMenuButton(resourceBundle.getString("logout"), VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(e -> logout());
        logoutButton.getElement().setAttribute("title", resourceBundle.getString("logout") + " (Ctrl+L)");

    }

    private Div createHeader() {
        // TODO Replace with real application logo and name
    
    	var appLogo = VaadinIcon.CUBES.create();
        appLogo.addClassNames(TextColor.PRIMARY, IconSize.LARGE);

        var appName = new Span("Prancer Framework");
        appName.addClassNames(FontWeight.SEMIBOLD, FontSize.LARGE);

        var header = new Div(new DrawerToggle(),appLogo, appName);
        header.addClassNames(Display.FLEX, Padding.MEDIUM, Gap.MEDIUM, AlignItems.CENTER);
        return header;
    }

    private SideNav createSideNav() {
        var nav = new SideNav();
        nav.addClassNames(Margin.Horizontal.MEDIUM);
        MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
        return nav;
    }

    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
        if (menuEntry.icon() != null) {
            return new SideNavItem(menuEntry.title(), menuEntry.path(), new Icon(menuEntry.icon()));
        } else {
            return new SideNavItem(menuEntry.title(), menuEntry.path());
        }
    }

    private Component createUserMenu() {
        // TODO Replace with real user information and actions
        var avatar = new Avatar("John Smith");
        avatar.addThemeVariants(AvatarVariant.LUMO_XSMALL);
        avatar.addClassNames(Margin.Right.SMALL);
        avatar.setColorIndex(5);

        var userMenu = new MenuBar();
        userMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        userMenu.addClassNames(Margin.MEDIUM);

        var userMenuItem = userMenu.addItem(avatar);
        userMenuItem.add("John Smith");
        userMenuItem.getSubMenu().addItem("View Profile").setEnabled(false);
        userMenuItem.getSubMenu().addItem("Manage Settings").setEnabled(false);
        userMenuItem.getSubMenu().addItem("Logout", event -> authenticationContext.logout()).setEnabled(true);

        return userMenu;
    }
  
    private Button createMenuButton(String caption, Icon icon) {
        final Button routerButton = new Button(caption);
        routerButton.setClassName("menu-button");
        routerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        routerButton.setIcon(icon);
        icon.setSize("24px");
        return routerButton;
    }

    private void logout() {
        AccessControlFactory.getInstance().createAccessControl().signOut();
    }

    private void registerAdminViewIfApplicable(AccessControl accessControl) {
        // register the admin view dynamically only for any admin user logged in
        if (accessControl.isUserInRole(AccessControl.ADMIN_ROLE_NAME)
                && !RouteConfiguration.forSessionScope()
                        .isRouteRegistered(MainView.class)) {
            RouteConfiguration.forSessionScope().setRoute("admin",
                    MainView.class, MainLayout.class);
            // as logout will purge the session route registry, no need to
            // unregister the view on logout
        }
    }
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // User can quickly activate logout with Ctrl+L
        attachEvent.getUI().addShortcutListener(() -> logout(), Key.KEY_L,
                KeyModifier.CONTROL);

        // add the admin view menu item if user has admin role
        final AccessControl accessControl = AccessControlFactory.getInstance()
                .createAccessControl();
        if (accessControl.isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {

            // Create extra navigation target for admins
            registerAdminViewIfApplicable(accessControl);

            // The link can only be created now, because the RouterLink checks
            // that the target is valid.
//            addToDrawer(createMenuLink(MainView.class, resourceBundle.getString("admin"),
//                    VaadinIcon.DOCTOR.create()));
        }

        // Finally, add logout button for all users
        addToDrawer(logoutButton);
    }
}
