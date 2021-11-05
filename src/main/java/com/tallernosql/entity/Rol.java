package com.tallernosql.entity;

import java.util.List;

public class Rol {
	private String correoRol;
	private String passwordRol;
	private String nombreRol;
	private List<String> roles;
		
	public Rol() { }	
	
	public Rol(String correo, String password, String nombreRol) {
		super();
		this.correoRol = correo;
		this.passwordRol = password;
		this.nombreRol = nombreRol;
	}
	
	public Rol(String correo, String password, List<String> r) {
		super();
		this.correoRol = correo;
		this.passwordRol = password;
		this.roles = r;
	}
	
	public String getCorreoRol() {
		return correoRol;
	}
	public void setCorreoRol(String correo) {
		this.correoRol = correo;
	}
	public String getPasswordRol() {
		return passwordRol;
	}
	public void setPasswordRol(String password) {
		this.passwordRol = password;
	}
	public String getNombreRol() {
		return nombreRol;
	}
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	

}
