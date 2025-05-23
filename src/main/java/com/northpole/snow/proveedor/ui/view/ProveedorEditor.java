package com.northpole.snow.proveedor.ui.view;

import com.northpole.snow.base.domain.CondicionIva;
import com.northpole.snow.proveedor.dominio.Proveedor;
import com.northpole.snow.proveedor.dominio.repositorio.IProveedorDao;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class ProveedorEditor extends Composite<VerticalLayout> implements KeyNotifier, HasUrlParameter<Proveedor> {

    public interface SaveListener {
        void onSave(Proveedor proveedor);
    }

    public interface DeleteListener {
        void onDelete(Proveedor proveedor);
    }

    public interface CancelListener {
        void onCancel();
    }
    private SaveListener saveListener;
    
    private DeleteListener deleteListener;
    private CancelListener cancelListener;
	
    // private final IProveedorDao repository;

	/**
	 * The currently edited customer
	 */
	private Proveedor proveedor;

	private final Binder<Proveedor> binder = new BeanValidationBinder(Proveedor.class);

	public void setProveedor(Proveedor proveedor) {
	    this.proveedor = proveedor;
	    binder.readBean(proveedor);
	}
	    
	/* Fields to edit properties in Customer entity */
	TextField razonSocial = new TextField("Razón social");
	TextField nombreFantasia = new TextField("Nombre de fantasía");
	TextField cuit = new TextField("Cuit");
	
	ComboBox<CondicionIva> comboBox = new ComboBox<>("Condición IVA");
//	comboBox.setItems(filter, DataService.getPeople());
//	comboBox.setItemLabelGenerator(
//	        person -> person.getFirstName() + " " + person.getLastName());
//	comboBox.setRenderer(createRenderer());
//	comboBox.getStyle().set("--vaadin-combo-box-overlay-width", "16em");
//	add(comboBox);
//
//	/* Action buttons */
//	Button save = new Button("Save", VaadinIcon.CHECK.create());
//	Button cancel = new Button("Cancel");
//	Button delete = new Button("Delete", VaadinIcon.TRASH.create());
//	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
//
//	Binder<Customer> binder = new Binder<>(Customer.class);
//	private ChangeHandler changeHandler;
//
//	@Autowired
//	public CustomerEditor(CustomerRepository repository) {
//		this.repository = repository;
//
//		add(firstName, lastName, actions);
//
//		// bind using naming convention
//		binder.bindInstanceFields(this);
//
//		// Configure and style components
//		setSpacing(true);
//
//		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
//
//		addKeyPressListener(Key.ENTER, e -> save());
//
//		// wire action buttons to save, delete and reset
//		save.addClickListener(e -> save());
//		delete.addClickListener(e -> delete());
//		cancel.addClickListener(e -> editCustomer(customer));
//		setVisible(false);
//	}
//
//	void delete() {
//		repository.delete(customer);
//		changeHandler.onChange();
//	}
//
//	void save() {
//		repository.save(customer);
//		changeHandler.onChange();
//	}
//
//	public interface ChangeHandler {
//		void onChange();
//	}
//
//	public final void editCustomer(Customer c) {
//		if (c == null) {
//			setVisible(false);
//			return;
//		}
//		final boolean persisted = c.getId() != null;
//		if (persisted) {
//			// Find fresh entity for editing
//			// In a more complex app, you might want to load
//			// the entity/DTO with lazy loaded relations for editing
//			customer = repository.findById(c.getId()).get();
//		}
//		else {
//			customer = c;
//		}
//		cancel.setVisible(persisted);
//
//		// Bind customer properties to similarly named fields
//		// Could also use annotation or "manual binding" or programmatically
//		// moving values from fields to entities before saving
//		binder.setBean(customer);
//
//		setVisible(true);
//
//		// Focus first name initially
//		firstName.focus();
//	}

//	public void setChangeHandler(ChangeHandler h) {
//		// ChangeHandler is notified when either save or delete
//		// is clicked
//		changeHandler = h;
//	}

	@Override
	public void setParameter(BeforeEvent event, Proveedor proveedor) {
		// TODO Auto-generated method stub
		this.proveedor = proveedor;
	}

}
