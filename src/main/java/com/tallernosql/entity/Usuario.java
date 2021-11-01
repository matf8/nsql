package com.tallernosql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	private String correo;
	private String password;
	private String nombre;
	private String apellido;
	
	public Usuario() {}
	
	public Usuario(String c, String p, String nombre, String apellido) {
		this.correo = c;
		this.password = p;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String p) {
		this.password = p;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
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
	
}
