package com.northpole.snow.proveedor.ui.view;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.northpole.snow.base.ui.component.ViewToolbar;
import com.northpole.snow.base.ui.view.MainLayout;
import com.northpole.snow.proveedor.componente.ProveedorViewLogic;
import com.northpole.snow.proveedor.componente.ProveedorForm;
import com.northpole.snow.proveedor.dominio.Proveedor;
import com.northpole.snow.proveedor.service.impl.ProveedorService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;

@Route(value = "proveedor-lista", layout = MainLayout.class)
@PageTitle("Proveedores")
@Menu(order = 1, icon = "vaadin:clipboard-check", title = "Proveedores")
@PermitAll // When security is enabled, allow all authenticated users

public class ProveedorListView extends Main implements HasUrlParameter<String>{
	   private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("MockDataWords", UI.getCurrent().getLocale());

	@Autowired
	private ProveedorService proveedorService;

	private String MAX_WIDTH = "400px";
	private String BUTTON_WIDTH = "123px";
    private final ProveedorForm form;
    private TextField filter;

    private final ProveedorViewLogic viewLogic = new ProveedorViewLogic(this);
    private Button newProveedor;
//    final TextField razonSocial;
//    final TextField cuit;
//    final TextField condicionIva;

    final Grid<Proveedor> proveedorGrid;

    public ProveedorListView(Clock clock) {
        final HorizontalLayout topLayout = createTopBar();
        
        form = new ProveedorForm(viewLogic);
//		razonSocial = new TextField();
//        cuit = new TextField();
//        condicionIva = new TextField();

        newProveedor = new Button(resourceBundle.getString("new_product"));
        // Setting theme variant of new production button to LUMO_PRIMARY that
        // changes its background color to blue and its text color to white
        newProveedor.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newProveedor.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newProveedor.addClickListener(click -> viewLogic.newProduct());
        // A shortcut to click the new product button by pressing ALT + N
        newProveedor.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
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
//        proveedorGrid.asSingleSelect().addValueChangeListener(
//                event -> viewLogic.rowSelected(event.getValue()));
        proveedorGrid.addItemClickListener(item -> {
            viewLogic.rowSelected(item.getItem());
//            Notification.show(String.format("File location: %s", item.getItem().getRazonSocial() ));
//            Notification.show(String.format("File location: %s", item.getItem().getId()));
        });
        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(proveedorGrid);
        barAndGridLayout.setFlexGrow(1, proveedorGrid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(proveedorGrid);

        add(barAndGridLayout);
        
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
        proveedorGrid.addColumn(proveedor -> proveedor.getCondicionIva().getCondicion()).setHeader("Condición IVA");
        proveedorGrid.addColumn(proveedor -> Optional.ofNullable(proveedor.getFechaAlta()).map(dateFormatter::format).orElse("Never"))
                .setHeader("Fecha Alta");
        //proveedorGrid.addColumn(task -> dateTimeFormatter.format(proveedor.getCreationDate())).setHeader("Creation Date");
        proveedorGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

//          add(new ViewToolbar("Task List", ViewToolbar.group(razonSocial, cuit, condicionIva)));
        add(proveedorGrid);
        viewLogic.init();
    }

//    private void verDetalle(Proveedor proveedor) {
//    	UI.getCurrent().navigate(ProveedorEditor.class, proveedor);
//    }
    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setPlaceholder(resourceBundle.getString("filter_placeholder"));
        // Apply the filter to grid's data provider. TextField value is never
//        filter.addValueChangeListener(
//                event -> dataProvider.setFilter(event.getValue()));
        // A shortcut to focus on the textField by pressing ctrl + F
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

        newProveedor = new Button(resourceBundle.getString("new_product"));
        // Setting theme variant of new production button to LUMO_PRIMARY that
        // changes its background color to blue and its text color to white
        newProveedor.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newProveedor.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newProveedor.addClickListener(click -> viewLogic.newProduct());
        // A shortcut to click the new product button by pressing ALT + N
        newProveedor.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(newProveedor);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
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

//    private void clearInputFields() {
//
//        razonSocial.clear();
//        cuit.clear();
//        condicionIva.clear();
//    }
    
    public void clearSelection() {
        proveedorGrid.getSelectionModel().deselectAll();
    }

    public void setNewProductEnabled(boolean enabled) {
        newProveedor.setEnabled(enabled);
    }

    public void selectRow(Proveedor row) {
        proveedorGrid.getSelectionModel().select(row);
    }

    public void removeProduct(Proveedor proveedor) {
        proveedorService.delete(proveedor);
    }

    /**
     * Displays user a form to edit a Product.
     * 
     * @param product
     */
    public void editProveedor(Proveedor product) {
        showForm(product != null);
        form.editProveedor(product);
    }

    /**
     * Shows and hides the new product form
     * 
     * @param show
     */
    public void showForm(boolean show) {
        form.setVisible(show);
        form.setEnabled(show);
    }

    public void updateProduct(Proveedor proveedor) {
        proveedorService.save(proveedor);
    }


    @Override
    public void setParameter(BeforeEvent event,
            @OptionalParameter String parameter) {
        viewLogic.enter(parameter);
    }

    public void showNotification(String msg) {
        Notification.show(msg);
    }

}
