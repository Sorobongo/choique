package com.northpole.snow.proveedor.componente;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ResourceBundle;

import com.northpole.snow.proveedor.dominio.Proveedor;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.value.ValueChangeMode;

public class ProveedorForm extends Div {
    private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("MockDataWords", UI.getCurrent().getLocale());

    private final VerticalLayout content;

    private final TextField razonSocial;
    private final TextField cuit;
    
//    private final BigDecimalField price;
//    private final IntegerField stockCount;
//    private final Select<Availability> availability;
//    private final CheckboxGroup<Category> category;
    private Button save;
    private Button discard;
    private Button cancel;
    private final Button delete;

    private final ProveedorViewLogic viewLogic;
    private final Binder<Proveedor> binder;
    private Proveedor proveedorActual;

    private static class PriceConverter implements Converter<BigDecimal, BigDecimal> {
        @Override
        public Result<BigDecimal> convertToModel(BigDecimal value, ValueContext context) {
            return Result.ok(value.setScale(2, RoundingMode.HALF_DOWN));
        }

        @Override
        public BigDecimal convertToPresentation(BigDecimal value, ValueContext context) {
            return value.setScale(2, RoundingMode.HALF_DOWN);
        }
    }

    public ProveedorForm(ProveedorViewLogic sampleCrudLogic) {
        setClassName("proveedor-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        content.addClassName("proveedor-form-content");
        add(content);

        viewLogic = sampleCrudLogic;

        razonSocial = new TextField(resourceBundle.getString("product_name"));
        razonSocial.setWidth("100%");
        razonSocial.setRequired(true);
        razonSocial.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(razonSocial);
        
        cuit = new TextField(resourceBundle.getString("cuit"));
        cuit.setWidth("100%");
        cuit.setRequired(true);
        cuit.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(cuit);

//        price = new BigDecimalField(resourceBundle.getString("price"));
//        price.setSuffixComponent(new Span("€"));
//        price.setValueChangeMode(ValueChangeMode.EAGER);
//
//        stockCount = new IntegerField(resourceBundle.getString("in_stock"));
//        stockCount.setValueChangeMode(ValueChangeMode.EAGER);
//
//        final HorizontalLayout horizontalLayout = new HorizontalLayout(price,
//                stockCount);
//        horizontalLayout.setWidth("100%");
//        horizontalLayout.setFlexGrow(1, price, stockCount);
//        content.add(horizontalLayout);

//        availability = new Select<>();
//        availability.setLabel(resourceBundle.getString("availability"));
//        availability.setWidth("100%");
//        availability.setItems(Availability.values());
//        content.add(availability);

//        category = new CheckboxGroup<>();
//        category.setLabel(resourceBundle.getString("categories"));
//        category.setId("category");
//        category.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
//        content.add(category);

        binder = new BeanValidationBinder<>(Proveedor.class);
//        binder.forField(price).withConverter(new PriceConverter()).bind("price");
//        binder.forField(stockCount).bind("stockCount");
        binder.bindInstanceFields(this);

        // enable/disable save button while editing
        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            final boolean hasChanges = binder.hasChanges();
            save.setEnabled(hasChanges && isValid);
            discard.setEnabled(hasChanges);
        });

        save = new Button(resourceBundle.getString("save"));
        save.setWidth("100%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> {
            if (proveedorActual != null
                    && binder.writeBeanIfValid(proveedorActual)) {
                viewLogic.saveProduct(proveedorActual);
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button(resourceBundle.getString("discard"));
        discard.setWidth("100%");
        discard.addClickListener(
                event -> viewLogic.editProduct(proveedorActual));

        cancel = new Button(resourceBundle.getString("cancel"));
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelProduct());
        cancel.addClickShortcut(Key.ESCAPE);
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelProduct())
                .setFilter("event.key == 'Escape'");

        delete = new Button(resourceBundle.getString("delete"));
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (proveedorActual != null) {
                viewLogic.deleteProduct(proveedorActual);
            }
        });

        content.add(save, discard, delete, cancel);
    }

//    public void setCategories(Collection<Category> categories) {
//        category.setItems(categories);
//    }

    public void editProveedor(Proveedor proveedor) {
        if (proveedor == null) {
            proveedor = new Proveedor();
        }
        delete.setVisible(!proveedor.isNewProduct());
        proveedorActual = proveedor;
        binder.readBean(proveedor);
    }

}
