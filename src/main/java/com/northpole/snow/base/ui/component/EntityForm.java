package com.northpole.snow.base.ui.component;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.northpole.snow.autenticacion.AccessControl;
import com.northpole.snow.autenticacion.AccessControlFactory;
import com.northpole.snow.base.domain.CondicionIva;
import com.northpole.snow.base.domain.TipoProveedor;
import com.northpole.snow.base.service.ICommonService;
import com.northpole.snow.base.ui.view.MainLayout;
import com.northpole.snow.proveedor.dominio.Proveedor;
import com.northpole.snow.proveedor.dominio.ProveedorDomicilio;
import com.northpole.snow.proveedor.service.ICondicionIvaService;
import com.northpole.snow.proveedor.service.ITipoProveedorService;
import com.northpole.snow.proveedor.service.impl.CondicionIvaService;
import com.northpole.snow.proveedor.service.impl.ProveedorService;
import com.northpole.snow.proveedor.service.impl.TipoProveedorService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class EntityForm<T> extends Div {

    private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("MockDataWords", UI.getCurrent().getLocale());
    
    private final VerticalLayout content;
    
    protected Button newButton;
    protected Button save;
    protected Button discard;
    protected Button cancel;
    protected  Button delete;
    protected T entidad;
    protected Binder<T> binder = new Binder<>();
   
    public EntityForm() {
        content = new VerticalLayout();
        content.setWidth("20px");
        content.setSizeUndefined();
        add(content);

        save = new Button("Guardar");//resourceBundle.getString("save"));
        save.setWidth("10%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        save.addClickListener(event -> {
//            if (entidad != null
//                    && binder.writeBeanIfValid(entidad)) {
//  //          	this.save(entidad);
//            }
//        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("descartar");//resourceBundle.getString("discard"));
        discard.setWidth("10%");
//        discard.addClickListener(
//                event -> viewLogic.editProduct(proveedor)
//                );

        cancel = new Button("Cancelar");//resourceBundle.getString("cancel"));
        cancel.setWidth("10%");
//        cancel.addClickListener(event -> viewLogic.cancelProduct());
        cancel.addClickShortcut(Key.ESCAPE);
//        getElement()
//                .addEventListener("keydown", event -> viewLogic.cancelProduct())
//                .setFilter("event.key == 'Escape'");

        delete = new Button("Eliminar");//resourceBundle.getString("delete"));
        delete.setWidth("10%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        newButton = new Button(resourceBundle.getString("new_product"));
        // Setting theme variant of new production button to LUMO_PRIMARY that
        // changes its background color to blue and its text color to white
        newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newButton.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newButton.addClickListener(click -> System.out.println(""));
        // A shortcut to click the new product button by pressing ALT + N
        newButton.addClickShortcut(Key.KEY_N, KeyModifier.ALT);

//        delete.addClickListener(event -> {
//            if (proveedor != null) {
//                viewLogic.deleteProduct(proveedor);
//            }
//        });
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(save, discard, delete, cancel);
        content.add(buttonsLayout);
        init();
    }

    public void init() {
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            newButton.setEnabled(false);;
        }
    }


    public void edit() {
        binder.bindInstanceFields(this);
        binder.readBean(entidad);
    }

    
    public void showNotification(String msg) {
        Notification.show(msg);
    }

}
