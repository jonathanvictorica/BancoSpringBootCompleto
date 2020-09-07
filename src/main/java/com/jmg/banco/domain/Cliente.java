package com.jmg.banco.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "BC0001_Cliente", indexes = { @Index(name = "IDX_cliente_tipodocumento_nrodocumento", columnList = "tipo_documento,nro_documento", unique = true) })
@Access(value = AccessType.FIELD)
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Long id;

	@Column(name = "tipo_documento", length = 5, nullable = false)
	@Size(min = 2, max = 5, groups = { Cliente.AddValidations.class,Cliente.UpdateValidations.class })
	private String tipoDocumento;

	@Column(name = "nro_documento", columnDefinition = "bigint", nullable = false)
	private Long nroDocumento;

	@Column(name = "nombre", length = 50, nullable = false)
	@Size(min = 2, max = 50, groups = { Cliente.AddValidations.class,Cliente.UpdateValidations.class })
	private String nombre;

	@Column(name = "apellido", length = 30, nullable = false)
	@Size(min = 2, max = 30, groups = { Cliente.AddValidations.class,Cliente.UpdateValidations.class })
	private String apellido;

	@Column(name = "fecha_alta", columnDefinition = "datetime", nullable = false)
	private Date fechaAlta;

	public Cliente() {

	}

	public Cliente(String tipoDocumento, Long nroDocumento, String nombre, String apellido, Date fechaAlta) {
		super();
		this.tipoDocumento = tipoDocumento;
		this.nroDocumento = nroDocumento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaAlta = fechaAlta;
	}

	public Cliente(Long id, String nombre, String apellido) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(Long nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

//	public class ClienteValidator implements Validator {
//
//		@Override
//		public boolean supports(Class<?> clazz) {
//			return Cliente.class.isAssignableFrom(clazz);
//		}
//
//		@Override
//		public void validate(Object target, Errors errors) {
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "nombre.empty", "Campo nombre requerido.");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apellido", "apellido.empty", "Campo apellido requerido.");
//		}
//	}

	public interface AddValidations {
	}

	public interface UpdateValidations {
	}

}
