package com.northpole.snow.proveedor.componente;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.northpole.snow.base.domain.DomicilioEntity;
import com.northpole.snow.base.ui.view.MainLayout;
import com.northpole.snow.base.ui.view.MainView;
import com.northpole.snow.proveedor.dominio.Proveedor;
import com.northpole.snow.proveedor.dominio.ProveedorDomicilio;
import com.northpole.snow.proveedor.service.impl.ProveedorService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "/proveedor-form", layout = MainLayout.class)
@PageTitle("Proveedor")
@PermitAll
public class ProveedorForm extends Div implements HasUrlParameter<Integer>{
	
   // private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("MockDataWords", UI.getCurrent().getLocale());

    @Autowired
    private ProveedorService service;
    private final VerticalLayout content;
    
    private Proveedor proveedor;
//    private Integer id;
    private TextField razonSocial;
    private TextField nombreFantasia;
    private TextField cuit;
    private TextField calle;
    private TextField altura;
    private NumberField codigoPostal;
    private TextField localidad;
    
    
//    private final BigDecimalField price;
//    private final IntegerField stockCount;
//    private final Select<Availability> availability;
//    private final CheckboxGroup<Category> category;
    private Button save;
    private Button discard;
    private Button cancel;
    private final Button delete;

//    private final ProveedorViewLogic viewLogic  = new ProveedorViewLogic(this);
    private final Binder<Proveedor> binder;
//    private Proveedor proveedorActual;
    private Grid<ProveedorDomicilio> domicilios = new Grid<>(ProveedorDomicilio.class);
    
//    private static class PriceConverter implements Converter<BigDecimal, BigDecimal> {
//        @Override
//        public Result<BigDecimal> convertToModel(BigDecimal value, ValueContext context) {
//            return Result.ok(value.setScale(2, RoundingMode.HALF_DOWN));
//        }
//
//        @Override
//        public BigDecimal convertToPresentation(BigDecimal value, ValueContext context) {
//            return value.setScale(2, RoundingMode.HALF_DOWN);
//        }
//    }

//    public ProveedorForm(ProveedorViewLogic sampleCrudLogic) {
    public ProveedorForm() {
//        setClassName("proveedor-form");
    
        content = new VerticalLayout();
//        content.setSizeFull();
        content.setSizeUndefined();
//        content.addClassName("proveedor-form-content");
        add(content);

//        viewLogic = sampleCrudLogic;
        
        
        razonSocial = new TextField("Razon social");//resourceBundle.getString("product_name"));
        razonSocial.setWidth("100%");
        razonSocial.setRequired(true);
        razonSocial.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(razonSocial);
        
        nombreFantasia = new TextField("Nombre comercial");//resourceBundle.getString("product_name"));
        nombreFantasia.setWidth("100%");
        nombreFantasia.setRequired(true);
        nombreFantasia.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(nombreFantasia);

//        altura = new TextField(resourceBundle.getString("product_name"));
//        altura.setWidth("100%");
//        altura.setRequired(true);
//        altura.setValueChangeMode(ValueChangeMode.EAGER);
//        content.add(altura);

        cuit = new TextField("pepe");//resourceBundle.getString("cuit"));
        cuit.setWidth("100%");
        cuit.setRequired(true);
        cuit.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(cuit);
/*
        domicilios.addColumn(p -> {
        	String calle = p.getCalle();
            return calle != null ? calle : "(Sin domicilio)";
        }).setHeader("Calle");
        
        domicilios.addColumn(p -> {
        	String altura = p.getNumero();
            return altura != null ? altura : null;
        }).setHeader("Altura");
*/        
        final HorizontalLayout barAndGridLayout = new HorizontalLayout();
//        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(domicilios);
        barAndGridLayout.setFlexGrow(1, domicilios);
//        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(domicilios);

       
        content.add(barAndGridLayout);
        
        binder = new BeanValidationBinder<>(Proveedor.class);

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
//        domicilios.addColumn(proveedor -> {
//            List<ProveedorDomicilio> dom = proveedorActual.getProveedorDomicilio();
//            return dom != null ? dom.get(0).getCalle() : "";
//        }).setHeader("Calle");

        

//
//        binder.forField(calle)
//        .bind(
//            proveedorActual -> {
//                List<ProveedorDomicilio> dom = proveedorActual.getProveedorDomicilio();
//                return dom != null ? dom.getCalle() : "";
//            },
//            (proveedor, calle) -> {
//                if (proveedor.getProveedorDomicilio() == null) {
//                    proveedor.setProveedorDomicilio(new ProveedorDomicilio());
//                }
//                proveedor.getProveedorDomicilio().setCalle(calle);
//            }
//        );

        
        //        binder.forField(calle).bind("proveedorDomicilio.calle");
        
//        binder.forField(price).withConverter(new PriceConverter()).bind("price");
//        binder.forField(stockCount).bind("stockCount");
//        binder.bindInstanceFields(this);

        // enable/disable save button while editing
        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            final boolean hasChanges = binder.hasChanges();
            save.setEnabled(hasChanges && isValid);
            discard.setEnabled(hasChanges);
        });

        save = new Button("save");//resourceBundle.getString("save"));
        save.setWidth("100%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> {
            if (proveedor != null
                    && binder.writeBeanIfValid(proveedor)) {
 //               viewLogic.saveProduct(proveedor);
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("descartar");//resourceBundle.getString("discard"));
        discard.setWidth("100%");
//        discard.addClickListener(
//                event -> viewLogic.editProduct(proveedor)
//                );

        cancel = new Button("Cancelar");//resourceBundle.getString("cancel"));
        cancel.setWidth("100%");
//        cancel.addClickListener(event -> viewLogic.cancelProduct());
        cancel.addClickShortcut(Key.ESCAPE);
//        getElement()
//                .addEventListener("keydown", event -> viewLogic.cancelProduct())
//                .setFilter("event.key == 'Escape'");

        delete = new Button("Eliminar");//resourceBundle.getString("delete"));
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
//        delete.addClickListener(event -> {
//            if (proveedor != null) {
//                viewLogic.deleteProduct(proveedor);
//            }
//        });

        content.add(save, discard, delete, cancel);
        this.setVisible(true);
        this.setEnabled(true);
    }

//    public void setCategories(Collection<Category> categories) {
//        category.setItems(categories);
//    }

    public void editProveedor(Proveedor proveedor) {
        if (proveedor == null) {
            proveedor = new Proveedor();
        }
        delete.setVisible(!proveedor.isNewProduct());
        this.proveedor = proveedor;
        binder.readBean(proveedor);
        domicilios.setItems(this.proveedor.getProveedorDomicilio());
    }

	@Override
	public void setParameter(BeforeEvent event, Integer parameter) {
		// TODO Auto-generated method stub

		proveedor = service.findById(parameter);
		editProveedor(proveedor);
	}

}
