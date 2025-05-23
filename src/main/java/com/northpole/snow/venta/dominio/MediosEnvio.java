package com.northpole.snow.venta.dominio;

import java.util.ArrayList;
import java.util.List;

public class MediosEnvio {

	private List<MedioEnvio> mediosEnvio =new ArrayList<>();

	public MediosEnvio() {}

	public MediosEnvio(List<MedioEnvio> mediosEnvio) {
		this.mediosEnvio= mediosEnvio;
	}

	public List<MedioEnvio> getMediosEnvio() {
		return mediosEnvio;
	}

	public void setMediosEnvio(List<MedioEnvio> mediosEnvio) {
		this.mediosEnvio = mediosEnvio;
	}

	public MedioEnvio getDefault() {
		return mediosEnvio.stream().filter(me->me.isDefault()).findFirst().orElseGet(null);
	}

}
