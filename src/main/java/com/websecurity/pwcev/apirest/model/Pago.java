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
	private Long ecommerce;
	
	@Column(name = "visa", nullable = false)
	private Long visa;
	
	@Column(name = "monto_total", nullable = false)
	private Float montoTotal;
	
	@Column(name = "fecha_pago", nullable = false)
	private Date fechaPago;
	
	@Column(name = "fecha_fin", nullable = false)
	private Date fechaFin;
	
	@Column(name = "meses", nullable = false)
	private Integer meses;
	
	@Column(name = "id_plan", nullable = false)
	private Integer idPlan; //Plan nuevo
	
	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_pago_usuario"))
	private Usuario usuario;
	
	public int getIdPago() {
		return idPago;
	}

	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}
	
	public Long getEcommerce() {
		return ecommerce;
	}

	public void setEcommerce(Long ecommerce) {
		this.ecommerce = ecommerce;
	}
	
	public float getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(float montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	public Long getVisa() {
		return visa;
	}

	public void setVisa(Long visa) {
		this.visa = visa;
	}
	
	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Integer getMeses() {
		return meses;
	}

	public void setMeses(Integer meses) {
		this.meses = meses;
	}
	
	public Integer getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}
	
}
