package com.tallernosql.entity;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String correo;
	private String password;
	private String nombre;	
	private String apellido;
	private List<String> roles = new ArrayList<String>(); 
	
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public void agregarRol(String r) {
		this.roles.add(r);
	}
	
	public void borrarRol(String r) {
		this.roles.remove(r);
	}
	
}
