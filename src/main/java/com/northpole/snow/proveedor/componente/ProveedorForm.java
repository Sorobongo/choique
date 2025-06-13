package com.northpole.snow.proveedor.componente;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.northpole.snow.base.domain.CondicionIva;
import com.northpole.snow.base.domain.TipoProveedor;
import com.northpole.snow.base.enumerados.EstadoEntidadEnum;
import com.northpole.snow.base.enumerados.SiNoEnum;
import com.northpole.snow.base.enumerados.TipoCotizacionEnum;
import com.northpole.snow.base.ui.component.EntityForm;
import com.northpole.snow.base.ui.component.EstadoRadioButtonGroup;
import com.northpole.snow.base.ui.component.SiNoRadioButtonGroup;
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
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
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

@Route(value = "/proveedor-form", layout = MainLayout.class)
@PageTitle("Proveedor")
@PermitAll
public class ProveedorForm extends EntityForm<Proveedor> implements HasUrlParameter<Integer>{
	
    private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("MockDataWords", UI.getCurrent().getLocale());

    @Autowired
    private ProveedorService service;
    
    private ICondicionIvaService condicionIvaSvc;

    private ITipoProveedorService tipoProveedorSvc;
    
//    private final VerticalLayout content;
    
//    private Proveedor proveedor;
    private TextField razonSocial;
    private TextField nombreFantasia;
    private TextField cuit;
    private TextField url;
    private ComboBox<TipoCotizacionEnum> cbTipoCotizacion = new ComboBox<>();
    private ComboBox<CondicionIva> condicionIva = new ComboBox<>();
    private ComboBox<TipoProveedor> cbTipoProveedor = new ComboBox<>();
    
//    private EstadoRadioButtonGroup estado = new EstadoRadioButtonGroup();
    private EstadoRadioButtonGroup rbEstado;// = new RadioButtonGroup<>();
    private SiNoRadioButtonGroup rbAgenteRetencion;
    private final ProveedorViewLogic viewLogic  = new ProveedorViewLogic(this);
    private Grid<ProveedorDomicilio> domicilios = new Grid<>(ProveedorDomicilio.class);
    
    public ProveedorForm(CondicionIvaService cis, TipoProveedorService tp) {
    	setClassName("proveedor-form");

        condicionIvaSvc = cis;
        tipoProveedorSvc = tp;
        
        razonSocial = new TextField("Razon social");//resourceBundle.getString("product_name"));
        razonSocial.setWidth(100, Unit.PERCENTAGE);
        razonSocial.setRequired(true);
        razonSocial.setValueChangeMode(ValueChangeMode.EAGER);
        
        nombreFantasia = new TextField("Nombre comercial");//resourceBundle.getString("product_name"));
        nombreFantasia.setWidth("100%");
        nombreFantasia.setRequired(true);
        nombreFantasia.setValueChangeMode(ValueChangeMode.EAGER);

        cuit = new TextField("Cuit");//resourceBundle.getString("cuit"));
        cuit.setWidth("100%");
        cuit.setRequired(true);
        cuit.setValueChangeMode(ValueChangeMode.EAGER);

        condicionIva.setLabel("Condicion IVA");
        condicionIva.setItemLabelGenerator(CondicionIva::getCondicion);
        condicionIva.setItems(condicionIvaSvc.findAll());

        url = new TextField(resourceBundle.getString("url"));
        url.setWidth(100, Unit.PERCENTAGE);
        url.setValueChangeMode(ValueChangeMode.EAGER);
        
        HorizontalLayout nombre = new HorizontalLayout(razonSocial, nombreFantasia, cuit, condicionIva, url);

        content.add(nombre);

//        condicionIva.setItemEnabledProvider(
//                item -> item.isSelected()
//                		);
 //        content.add(condicionIva);

        cbTipoProveedor.setLabel("Tipo proveedor");
        cbTipoProveedor.setItemLabelGenerator(TipoProveedor::getNombre);
        cbTipoProveedor.setItems(tipoProveedorSvc.findAll());
//        condicionIva.setItemEnabledProvider(
//                item -> item.isSelected()
//                		);
        final HorizontalLayout radioButtonsLayout = new HorizontalLayout();
 
        rbEstado = new EstadoRadioButtonGroup();
        rbEstado.addValueChangeListener();

        rbAgenteRetencion = new SiNoRadioButtonGroup();
        rbAgenteRetencion.addValueChangeListener();
        
        cbTipoCotizacion.setLabel("Tipo de cotización");
        cbTipoCotizacion.setItems(TipoCotizacionEnum.values());

        radioButtonsLayout.add(cbTipoProveedor, rbEstado, rbAgenteRetencion, cbTipoCotizacion);
        
        content.add(radioButtonsLayout);
        

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
            	viewLogic.saveProveedor(entidad);
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

//        newButton.addClickListener(click -> viewLogic.newProduct());
        // A shortcut to click the new product button by pressing ALT + N
//        newButton.addClickShortcut(Key.KEY_N, KeyModifier.ALT);

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
            entidad = Proveedor.builder()
            					.activo(false)
            					.agenteRetencion(false)
            					.fechaAlta(LocalDateTime.now())
            					.build();
            
        }
//        delete.setVisible(!proveedor.isNewProduct());
        binder.bindInstanceFields(this);
        binder.readBean(entidad);
        condicionIva.setValue(entidad.getCondicionIva());
        cbTipoProveedor.setValue(entidad.getTipoProveedor());
        cbTipoCotizacion.setValue(entidad.getTipoCotizacion());
        rbEstado.setSelected(entidad.isActivo() ? EstadoEntidadEnum.Activo : EstadoEntidadEnum.Inactivo);
        rbAgenteRetencion.setSelected(entidad.isAgenteRetencion() ? SiNoEnum.Si : SiNoEnum.No);
        domicilios.setItems(entidad.getProveedorDomicilio());
    }

//    public void setNewProductEnabled(boolean enabled) {
//        newButton.setEnabled(enabled);
//    }

    public Proveedor findProveedor(int proveedorId) {
    	return service.findById(proveedorId);
    }


//    public void updateProveedor(Proveedor proveedor) {
//        service.save(proveedor);
//    }

    public void removeProduct(Proveedor proveedor) {
        service.delete(proveedor);
    }
    
//    public void showNotification(String msg) {
//        Notification.show(msg);
//    }
//
	@Override
	public void setParameter(BeforeEvent event, Integer parameter) {
		// TODO Auto-generated method stub
		if(parameter > 0) {
			entidad = findProveedor(parameter);
		}
		editProveedor();
	}

	protected void update(Proveedor entidad) {
    	entidad.setActivo(rbEstado.getSelected());
    	entidad.setAgenteRetencion(rbAgenteRetencion.getSelected());
		service.save(entidad);
	}

	protected void remove(Proveedor entidad) {
		service.delete(entidad);
	}

	@Override
	protected void clear() {
		// TODO Auto-generated method stub
		binder.getFields().forEach(f -> f.clear());	
	}

	@Override
	protected void cancel() {
		// TODO Auto-generated method stub
		binder.getFields().forEach(f -> f.clear());	
		
	}
}
