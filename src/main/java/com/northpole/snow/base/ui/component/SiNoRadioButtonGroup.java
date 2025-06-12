package com.northpole.snow.base.ui.component;

import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

import com.northpole.snow.base.enumerados.EstadoEntidadEnum;
import com.northpole.snow.base.enumerados.SiNoEnum;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.provider.ListDataProvider;

import lombok.Getter;
import lombok.Setter;


public class SiNoRadioButtonGroup extends HorizontalLayout{
    private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("MockDataWords", UI.getCurrent().getLocale());
    private final RadioButtonGroup<SiNoEnum> estado;
    private Boolean selected;
    //private SiNoEnum resultado;

    public SiNoRadioButtonGroup (){
    	estado = new RadioButtonGroup<>();
    	estado.setLabel(resourceBundle.getString("es_agente_retencion"));
    	estado.setThemeName("horizontal");
    	estado.setItems(SiNoEnum.Si, SiNoEnum.No);
    	estado.setDataProvider(new ListDataProvider<SiNoEnum>(Arrays.asList(SiNoEnum.values())));
    	add(estado);
    }


    public void addValueChangeListener() {
   	estado.addValueChangeListener(e-> selected = e.getValue().compareTo(SiNoEnum.Si)==0 ? true : false);
   }

    public void setSelected(SiNoEnum value) {
    	estado.setValue(value);
    }
    
    public Boolean getSelected() {
    	return selected;
    }
}
