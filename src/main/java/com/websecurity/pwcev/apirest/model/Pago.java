package com.websecurity.pwcev.apirest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pago")
public class Pago {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idPago;
	
	@Column(name = "ecommerce", nullable = false)
	private Integer ecommerce;
	
	@Column(name = "visa", nullable = false)
	private Integer visa;
	
	@Column(name = "monto_total", nullable = false)
	private Float montoTotal;
	
	@Column(name = "fecha_pago", nullable = false)
	private Date fechaPago;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_pago_usuario"))
	private Usuario usuario;
	
	public int getIdPago() {
		return idPago;
	}

	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}
	
	public int getEcommerce() {
		return ecommerce;
	}

	public void setEcommerce(int ecommerce) {
		this.ecommerce = ecommerce;
	}
	
	public float getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(float montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	public int getVisa() {
		return visa;
	}

	public void setVisa(int visa) {
		this.visa = visa;
	}
	
	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
