package com.northpole.snow.proveedor.ui.view;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import com.northpole.snow.base.ui.component.ViewToolbar;
import com.northpole.snow.proveedor.dominio.Proveedor;
import com.northpole.snow.proveedor.service.impl.ProveedorService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;

@Route("proveedor-lista")
@PageTitle("Proveedores")
@Menu(order = 1, icon = "vaadin:clipboard-check", title = "Proveedores")
@PermitAll // When security is enabled, allow all authenticated users

public class ProveedorListView extends Main {
	
	private String MAX_WIDTH = "400px";
	private String BUTTON_WIDTH = "123px";
    private final ProveedorService proveedorService;
//    private Button newBtn = new Button("New");
//    private Button deleteBtn = new Button("Delete");
//    private Button saveBtn = new Button("Save");
//
//    private HorizontalLayout btnLayout = new HorizontalLayout();
//    private HorizontalLayout fieldsLayout = new HorizontalLayout();
    
    final TextField razonSocial;
    final TextField cuit;
    final TextField condicionIva;

    final Grid<Proveedor> proveedorGrid;

    public ProveedorListView(ProveedorService proveedorService, Clock clock) {
    	
    	
        this.proveedorService = proveedorService;

        razonSocial = new TextField();
        cuit = new TextField();
        condicionIva = new TextField();
        
//        newBtn.setWidth(BUTTON_WIDTH);
//        deleteBtn.setWidth(BUTTON_WIDTH);
//        saveBtn.setWidth(BUTTON_WIDTH);

//        btnLayout.add(newBtn, deleteBtn, saveBtn);
//        btnLayout.setMaxWidth(MAX_WIDTH);
//
//        fieldsLayout.add(razonSocial, cuit, condicionIva);
//
//        add(btnLayout);
//        add(fieldsLayout);

        proveedorGrid = new Grid<>();
        proveedorGrid.addItemClickListener(item -> {
        	verDetalle(item.getItem());
            Notification.show(String.format("File location: %s", item.getItem().getRazonSocial() ));
            Notification.show(String.format("File location: %s", item.getItem().getId()));
        });
        
//        newBtn.addClickListener(click -> {
//
//            clearInputFields();
//            proveedorGrid.select(null);
//        });
//
//        deleteBtn.addClickListener(click -> {
// 
//            proveedorService.delete(proveedorGrid.getSelectedItems().stream().toList().get(0));
//            
//            clearInputFields();
//            refreshTableData();
//        });
//
//        saveBtn.addClickListener(click -> {
//
//            Proveedor proveedor = new Proveedor();
//            proveedor.setRazonSocial(razonSocial.getValue());
//            proveedor.setCuit(cuit.getValue());
////            proveedor.setCondicionIva(condicionIva.getValue());
//
//            proveedorService.save(proveedor);
//
//            clearInputFields();
//            refreshTableData();
//        });
        var dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(clock.getZone())
                .withLocale(getLocale());
        var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());

        proveedorGrid.setItems(query -> proveedorService.list(toSpringPageRequest(query)).stream());
        proveedorGrid.addColumn(Proveedor::getId).setKey("id").setVisible(false);
        proveedorGrid.addColumn(Proveedor::getRazonSocial).setHeader("Razón social").setSortable(true).setSortProperty("razonSocial");
        proveedorGrid.addColumn(Proveedor::getCuit).setHeader("Cuit");
        proveedorGrid.addColumn(proveedor -> proveedor.getCondicionIva().getCondicion());
        proveedorGrid.addColumn(proveedor -> Optional.ofNullable(proveedor.getFechaAlta()).map(dateFormatter::format).orElse("Never"))
                .setHeader("Fecha Alta");
        //proveedorGrid.addColumn(task -> dateTimeFormatter.format(proveedor.getCreationDate())).setHeader("Creation Date");
        proveedorGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

//          add(new ViewToolbar("Task List", ViewToolbar.group(razonSocial, cuit, condicionIva)));
        add(proveedorGrid);
    }

    private void verDetalle(Proveedor proveedor) {
    	UI.getCurrent().navigate(ProveedorEditor.class, proveedor);
    }
    
    private void createTask() {
//        proveedorService.createTask(description.getValue(), dueDate.getValue());
//        taskGrid.getDataProvider().refreshAll();
//        description.clear();
//        dueDate.clear();
//        Notification.show("Task added", 3000, Notification.Position.BOTTOM_END)
//                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void refreshTableData() {

        proveedorGrid.setItems(query -> proveedorService.list(toSpringPageRequest(query)).stream());
    }

    private void clearInputFields() {

        razonSocial.clear();
        cuit.clear();
        condicionIva.clear();
    }
}
