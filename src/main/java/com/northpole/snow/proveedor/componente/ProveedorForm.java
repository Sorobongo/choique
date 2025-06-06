package com.northpole.snow.proveedor.componente;

import org.springframework.beans.factory.annotation.Autowired;

import com.northpole.snow.base.domain.CondicionIva;
import com.northpole.snow.base.domain.TipoProveedor;
import com.northpole.snow.base.ui.component.EntityForm;
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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "/proveedor-form", layout = MainLayout.class)
@PageTitle("Proveedor")
@PermitAll
public class ProveedorForm extends EntityForm<Proveedor> implements HasUrlParameter<Integer>{
	
//    private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("MockDataWords", UI.getCurrent().getLocale());

    @Autowired
    private ProveedorService service;
    
    private ICondicionIvaService condicionIvaSvc;

    private ITipoProveedorService tipoProveedorSvc;
    
    private final VerticalLayout content;
    
//    private Proveedor proveedor;
    private TextField razonSocial;
    private TextField nombreFantasia;
    private TextField cuit;
    private Select<CondicionIva> condicionIva = new Select<>();
    private Select<TipoProveedor> tipoProveedor = new Select<>();
    
    private final RadioButtonGroup<Boolean> estado;

    private final ProveedorViewLogic viewLogic  = new ProveedorViewLogic(this);
//    private final Binder<Proveedor> binder;
    private Grid<ProveedorDomicilio> domicilios = new Grid<>(ProveedorDomicilio.class);
    
    public ProveedorForm(CondicionIvaService cis, TipoProveedorService tp) {
    	setClassName("proveedor-form");

        condicionIvaSvc = cis;
        tipoProveedorSvc = tp;
        
        content = new VerticalLayout();
        content.setWidth("20px");
        content.setSizeUndefined();
        content.addClassName("proveedor-form-content");
        add(content);
        
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

        cuit = new TextField("Cuit");//resourceBundle.getString("cuit"));
        cuit.setWidth("100%");
        cuit.setRequired(true);
        cuit.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(cuit);

        condicionIva = new Select<>();
        condicionIva.setLabel("Condicion IVA");
        condicionIva.setItemLabelGenerator(CondicionIva::getCondicion);
        condicionIva.setItems(condicionIvaSvc.findAll());
//        condicionIva.setItemEnabledProvider(
//                item -> item.isSelected()
//                		);
        content.add(condicionIva);

        tipoProveedor = new Select<>();
        tipoProveedor.setLabel("Tipo proveedor");
        tipoProveedor.setItemLabelGenerator(TipoProveedor::getNombre);
        tipoProveedor.setItems(tipoProveedorSvc.findAll());
//        condicionIva.setItemEnabledProvider(
//                item -> item.isSelected()
//                		);
        content.add(tipoProveedor);

        estado = new RadioButtonGroup<>();
        estado.setLabel("Estado");//resourceBundle.getString("categories"));
      
        estado.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        content.add(estado);
        

        final HorizontalLayout barAndGridLayout = new HorizontalLayout();
//        barAndGridLayout.add(topLayout);
        domicilios.setAllRowsVisible(true);
        barAndGridLayout.add(domicilios);
        barAndGridLayout.setFlexGrow(1, domicilios);
//        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(domicilios);

       
        content.add(barAndGridLayout);
        
        binder = new BeanValidationBinder<>(Proveedor.class);

        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            final boolean hasChanges = binder.hasChanges();
//            save.setEnabled(hasChanges && isValid);
//            discard.setEnabled(hasChanges);
        });

        save.addClickListener(event -> {
            if (entidad != null
                    && binder.writeBeanIfValid(entidad)) {
                viewLogic.saveProduct(entidad);
            }
        });

//        discard.addClickListener(
//                event -> viewLogic.editProduct(proveedor)
//                );

//        cancel.addClickListener(event -> viewLogic.cancelProduct());
        cancel.addClickShortcut(Key.ESCAPE);
//        getElement()
//                .addEventListener("keydown", event -> viewLogic.cancelProduct())
//                .setFilter("event.key == 'Escape'");

        newButton.addClickListener(click -> viewLogic.newProduct());
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
    }

//    public void setCategories(Collection<Category> categories) {
//        category.setItems(categories);
//    }

    public void editProveedor() {
        if (entidad == null) {
            entidad = new Proveedor();
        }
//        delete.setVisible(!proveedor.isNewProduct());
        binder.bindInstanceFields(this);
        binder.readBean(entidad);
        condicionIva.setValue(entidad.getCondicionIva());
        tipoProveedor.setValue(entidad.getTipoProveedor());
        estado.setItems(Boolean.TRUE, Boolean.FALSE);
        estado.setValue(entidad.isActivo());
        domicilios.setItems(entidad.getProveedorDomicilio());
    }

//    public void setNewProductEnabled(boolean enabled) {
//        newButton.setEnabled(enabled);
//    }

    public Proveedor findProveedor(int proveedorId) {
    	return service.findById(proveedorId);
    }


    public void updateProduct(Proveedor proveedor) {
        service.save(proveedor);
    }

    public void removeProduct(Proveedor proveedor) {
        service.delete(proveedor);
    }
    
    public void showNotification(String msg) {
        Notification.show(msg);
    }

	@Override
	public void setParameter(BeforeEvent event, Integer parameter) {
		// TODO Auto-generated method stub
		entidad = findProveedor(parameter);
		editProveedor();
	}

}
