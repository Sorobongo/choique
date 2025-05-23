package com.northpole.snow.base.domain;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tipo_documento_estado")
@AssociationOverrides({@AssociationOverride(name="pk.tipoDocumento", joinColumns= @JoinColumn(name = "tipoDocumento.id")),
	@AssociationOverride(name="pk.estado", joinColumns = @JoinColumn(name="estado.id"))})
public class TipoDocumentoEstado implements Serializable {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -8085686262559391523L;

	@EmbeddedId
	private TipoDocumentoEstadoId pk = new TipoDocumentoEstadoId();
	private Integer orden;

	public TipoDocumentoEstado() {}

	@Transient
	public TipoDocumento getTipoDocumento() {
		return getPk().getTipoDocumento();
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		 	getPk().setTipoDocumento(tipoDocumento);
	}

	@Transient
	public Estado getEstado() {
		return getPk().getEstado();
	}

	public void setEstado(Estado estado) {
		 	getPk().setEstado(estado);
	}

}
