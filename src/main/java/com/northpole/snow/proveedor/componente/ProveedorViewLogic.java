package com.northpole.snow.proveedor.componente;

import java.io.Serializable;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.northpole.snow.autenticacion.AccessControl;
import com.northpole.snow.autenticacion.AccessControlFactory;
import com.northpole.snow.proveedor.dominio.Proveedor;
import com.northpole.snow.proveedor.service.impl.ProveedorService;
import com.northpole.snow.proveedor.ui.view.ProveedorListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;


/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the product editor form and the data source, including
 * fetching and saving products.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */

public class ProveedorViewLogic implements Serializable {

    @Autowired
    private ProveedorService service;
    private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("MockDataWords", UI.getCurrent().getLocale());
    //private final ProveedorListView view;
    private final ProveedorForm view;
    public ProveedorViewLogic(ProveedorForm simpleCrudView){//ProveedorListView simpleCrudView) {
        view = simpleCrudView;
    }

    /**
     * Does the initialization of the inventory view including disabling the
     * buttons if the user doesn't have access.
     */
//    public void init() {
//        if (!AccessControlFactory.getInstance().createAccessControl()
//                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
//            view.setNewProductEnabled(false);
//        }
//    }

    public void cancelProduct() {
        setFragmentParameter("");
//        view.clearSelection();
    }

    /**
     * Updates the fragment without causing InventoryViewLogic navigator to
     * change view. It actually appends the productId as a parameter to the URL.
     * The parameter is set to keep the view state the same during e.g. a
     * refresh and to enable bookmarking of individual product selections.
     *
     */
    private void setFragmentParameter(String productId) {
        String fragmentParameter;
        if (productId == null || productId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = productId;
        }

        UI.getCurrent().navigate(ProveedorListView.class, fragmentParameter);
    }

    /**
     * Opens the product form and clears its fields to make it ready for
     * entering a new product if productId is null, otherwise loads the product
     * with the given productId and shows its data in the form fields so the
     * user can edit them.
     *
     * 
     * @param productId
     */
    public void enter(String productId) {
        if (productId != null && !productId.isEmpty()) {
            if (productId.equals("new")) {
                newProduct();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    final int pid = Integer.parseInt(productId);
                     final Proveedor proveedor = view.findProveedor(pid);
//                    view.selectRow(product);
                } catch (final NumberFormatException e) {
                }
            }
        } else {
          //  view.showForm(false);
        }
    }

//    private Proveedor findProduct(int proveedorId) {
//    	return service.findById(proveedorId);
//    }

    public void saveProduct(Proveedor proveedor) {
        final boolean newProduct = proveedor.isNewProduct();
        view.updateProduct(proveedor);
        setFragmentParameter("");
        view.showNotification(proveedor.getRazonSocial()
                + (newProduct ? " " + resourceBundle.getString("created") : " " + resourceBundle.getString("updated")));
    }

    public void deleteProduct(Proveedor proveedor) {
        view.removeProduct(proveedor);
        setFragmentParameter("");
        view.showNotification(proveedor.getRazonSocial() + " " + resourceBundle.getString("removed"));
    }

    public void editProduct(Proveedor proveedor) {
        if (proveedor == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(proveedor.getId() + "");
        }
  //      view.editProveedor(proveedor);
    }

    public void newProduct() {
        setFragmentParameter("new");
//        view.editProveedor(new Proveedor());
    }

    public void rowSelected(Proveedor proveedor) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editProduct(proveedor);
        }
    }
    
    public void showMessage(String msj) {
    	Notification.show(msj);
    }
}
