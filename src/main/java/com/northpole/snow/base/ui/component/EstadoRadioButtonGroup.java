package com.northpole.snow.base.ui.component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import com.northpole.snow.base.enumerados.EstadoEntidadEnum;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.provider.ListDataProvider;

import lombok.Getter;
import lombok.Setter;


public class EstadoRadioButtonGroup extends HorizontalLayout implements Serializable{
	
    private final RadioButtonGroup<EstadoEntidadEnum> estado;
    private Boolean selected;
    
    public EstadoRadioButtonGroup (){
    	estado = new RadioButtonGroup<>();
    	estado.setLabel("Estado");//resourceBundle.getString("categories"));
    	estado.setThemeName("horizontal");
//    	estado.setItems(EstadoEntidadEnum.Activo, EstadoEntidadEnum.Inactivo);
    	estado.setDataProvider(new ListDataProvider<EstadoEntidadEnum>(Arrays.asList(EstadoEntidadEnum.values())));
    	add(estado);
    }

     public void addValueChangeListener() {
    	estado.addValueChangeListener(e-> selected = e.getValue().compareTo(EstadoEntidadEnum.Activo)==0 ? true : false);
    }

     public void setSelected(EstadoEntidadEnum valor) {
    	 estado.setValue(valor);
     }
     
     public Boolean getSelected() {
    	 return selected;
     }
}
