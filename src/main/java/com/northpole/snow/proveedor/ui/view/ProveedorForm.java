package com.northpole.snow.proveedor.ui.view;

import java.time.LocalDateTime;
import java.util.Optional;
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
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataKeyMapper;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.Element;
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
    
    private TextField razonSocial;
    private TextField nombreFantasia;
    private TextField cuit;
    private TextField url;
    private ComboBox<TipoCotizacionEnum> cbTipoCotizacion = new ComboBox<>();
    private ComboBox<CondicionIva> condicionIva = new ComboBox<>();
    private ComboBox<TipoProveedor> cbTipoProveedor = new ComboBox<>();
    
    private EstadoRadioButtonGroup rbEstado;// = new RadioButtonGroup<>();
    private SiNoRadioButtonGroup rbAgenteRetencion;
//    private final ProveedorViewLogic viewLogic  = new ProveedorViewLogic(this);
    private Grid<ProveedorDomicilio> domicilios = new Grid<>();
    
    public ProveedorForm(CondicionIvaService condicionIvaSvc, TipoProveedorService tipoProveedorSvc) {
    	setClassName("proveedor-form");

        this.condicionIvaSvc = condicionIvaSvc;
        this.tipoProveedorSvc = tipoProveedorSvc;
        
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

        final HorizontalLayout radioButtonsLayout = new HorizontalLayout();
 
        rbEstado = new EstadoRadioButtonGroup();
        rbEstado.addValueChangeListener();

        rbAgenteRetencion = new SiNoRadioButtonGroup();
        rbAgenteRetencion.addValueChangeListener();
        
        cbTipoCotizacion.setLabel("Tipo de cotización");
        cbTipoCotizacion.setItems(TipoCotizacionEnum.values());

        radioButtonsLayout.add(cbTipoProveedor, cbTipoCotizacion, rbEstado, rbAgenteRetencion );
        
        content.add(radioButtonsLayout);
        

        final VerticalLayout barAndGridLayout = new VerticalLayout();
        Button nuevoDomicilio = new Button("Nuevo domicilio");

        nuevoDomicilio.addSingleClickListener(event->this.showNotification(event.getSource().getText()));
        
        domicilios.setAllRowsVisible(true);
        barAndGridLayout.add(nuevoDomicilio, domicilios);
        barAndGridLayout.setFlexGrow(1, domicilios);
        barAndGridLayout.setSizeFull();
//        barAndGridLayout.expand(domicilios, nuevoDomicilio);

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
            	update(entidad);
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
        
        domicilios.appendFooterRow();
        domicilios.setItems(entidad.getProveedorDomicilio());
//        domicilios.setItemDetailsRenderer(null); 
        domicilios.addColumn(ProveedorDomicilio::getId).setKey("domicilioId").setVisible(false);
        domicilios.addColumn(ProveedorDomicilio::getCalle).setHeader("Calle").setResizable(true);
        domicilios.addColumn(ProveedorDomicilio::getNumero).setHeader("Altura");
        domicilios.addColumn(ProveedorDomicilio::getPiso).setHeader("Piso");
        domicilios.addColumn(ProveedorDomicilio::getDepartamento).setHeader("Oficina");
        domicilios.addColumn(pd->pd.getCodigoPostal().getLocalidad()).setHeader("Localidad");
        domicilios.addColumn(pd->pd.getCodigoPostal().getProvincia().getNombre()).setHeader("Provincia");
        domicilios.addColumn(pd->pd.getTipoDomicilio().getNombre()).setHeader("Tipo domicilio");
        domicilios.addColumn(ProveedorDomicilio::getPais).setHeader("Pais");

    }

//    public void setNewProductEnabled(boolean enabled) {
//        newButton.setEnabled(enabled);
//    }

//    public Proveedor findProveedor(int proveedorId) {
//    	return service.findConDomicilioById(proveedorId);
//    }


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
			entidad = findById(parameter);
		}
		editProveedor();
	}

	protected void update(Proveedor entidad) {
    	entidad.setActivo(rbEstado.getSelected());
    	entidad.setAgenteRetencion(rbAgenteRetencion.getSelected());
		service.save(entidad);
        final boolean nuevaEntidad = entidad.esNuevaEntidad();
        showNotification(entidad.getRazonSocial()
                + (nuevaEntidad ? " " + resourceBundle.getString("created") : " " + resourceBundle.getString("updated")));
	}

	protected void remove(Proveedor entidad) {
		service.delete(entidad);
        showNotification(entidad.getRazonSocial() + " " + resourceBundle.getString("removed"));

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

	@Override
	protected Proveedor findById(Integer id) {
		// TODO Auto-generated method stub
		return service.findConDomicilioById(id);
	}
}
