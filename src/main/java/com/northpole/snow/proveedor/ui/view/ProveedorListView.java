package com.northpole.snow.proveedor.ui.view;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.northpole.snow.autenticacion.AccessControl;
import com.northpole.snow.autenticacion.AccessControlFactory;
import com.northpole.snow.base.ui.view.MainLayout;
import com.northpole.snow.proveedor.dominio.Proveedor;
import com.northpole.snow.proveedor.service.impl.ProveedorService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "/proveedor-lista", layout = MainLayout.class)
@PageTitle("Proveedores")
@Menu(order = 1, icon = "vaadin:clipboard-check", title = "Proveedores")
@PermitAll // When security is enabled, allow all authenticated users
public class ProveedorListView extends Main {
	
	private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("MockDataWords", UI.getCurrent().getLocale());

	@Autowired
	private ProveedorService service;

    private TextField filter;
    private String filterText = "";
    final Grid<Proveedor> proveedorGrid;

    public ProveedorListView(Clock clock) {
    	
    	setSizeFull();
    	
        final HorizontalLayout topLayout = createTopBar();
        
        var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());
        proveedorGrid = new Grid<>();
        proveedorGrid.addItemDoubleClickListener(event -> {
        	navegar(event.getItem().getId());	
        });
        proveedorGrid.setItems(query -> service.list(toSpringPageRequest(query)).stream());
        proveedorGrid.addColumn(Proveedor::getId).setKey("id").setVisible(false);
        proveedorGrid.addColumn(Proveedor::getRazonSocial).setHeader("Razón social").setSortable(true).setSortProperty("razonSocial");
        proveedorGrid.addColumn(Proveedor::getCuit).setHeader("Cuit");
        proveedorGrid.addColumn(proveedor -> proveedor.getCondicionIva().getCondicion()).setHeader("Condición IVA");
        proveedorGrid.addColumn(proveedor -> proveedor.getUrl()).setHeader("Web");
        proveedorGrid.addColumn(proveedor -> Optional.ofNullable(proveedor.getFechaAlta()).map(dateFormatter::format).orElse("Never"))
                .setHeader("Fecha Alta");

        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(proveedorGrid);
        barAndGridLayout.setFlexGrow(1, proveedorGrid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(proveedorGrid);

        add(barAndGridLayout);
    }

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setPlaceholder(resourceBundle.getString("filter_placeholder"));
        // Apply the filter to grid's data provider. TextField value is never
//        filter.addValueChangeListener(
//                event -> setFilter(event.getValue()));
        // A shortcut to focus on the textField by pressing ctrl + F
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
//        topLayout.add(newProveedor);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }

//    public void setFilter(String filterText) {
//        Objects.requireNonNull(filterText, "Filter text cannot be null.");
//        if (Objects.equals(this.filterText, filterText.trim())) {
//            return;
//        }
//        this.filterText = filterText.trim().toLowerCase(UI.getCurrent().getLocale());
//
//        setFilter(proveedor -> passesFilter(product.getProductName(), this.filterText)
//                || passesFilter(product.getAvailability(), this.filterText)
//                || passesFilter(product.getCategory(), this.filterText));
//    }


    private void refreshTableData() {

        proveedorGrid.setItems(query -> service.list(toSpringPageRequest(query)).stream());
    }

    
    public void clearSelection() {
        proveedorGrid.getSelectionModel().deselectAll();
    }

    public void navegar(Integer param) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
        	UI.getCurrent().navigate(ProveedorForm.class, param);
        }else {
            showNotification("Sin permisos suficientes para la operación seleccionada");
        }

    }

    public void showNotification(String msg) {
        Notification.show(msg);
    }

//    private boolean passesFilter(Object object, String filterText) {
//        return object != null && object.toString().toLowerCase(UI.getCurrent().getLocale())
//                .contains(filterText);
//    }
}
