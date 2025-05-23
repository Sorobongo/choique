package com.northpole.snow.base.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class FormaPagoMonedaPk  implements Serializable{

	@Column(name = "forma_pago_id")
	private Integer formaPagoId;

	@Column(name = "moneda_id")
	private Integer monedaId;

	public FormaPagoMonedaPk() {}

	@Builder()
	public FormaPagoMonedaPk(Integer formaPagoId, Integer monedaId) {
		this.formaPagoId = formaPagoId;
		this.monedaId = monedaId;
	}


	@Override
	public int hashCode() {
		return Objects.hash(formaPagoId, monedaId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		FormaPagoMonedaPk other = (FormaPagoMonedaPk) obj;
		return Objects.equals(formaPagoId, other.formaPagoId) && Objects.equals(monedaId, other.monedaId);
	}

}
