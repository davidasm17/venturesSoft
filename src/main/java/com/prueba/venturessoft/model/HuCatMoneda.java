package com.prueba.venturessoft.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "HU_CAT_MONEDA")
public class HuCatMoneda {
	
	@EmbeddedId
	private HuCatMonedaId id;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "SIMBOLO")
	private String simbolo;
	
	@Column(name = "ESTATUS")
	private String estatus;

}
