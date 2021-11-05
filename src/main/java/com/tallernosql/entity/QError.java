package com.tallernosql.entity;

public class QError {		
	private int idE;
	private String descripcion;
	
	public QError() { }

	public QError(int id, String descripcion) {
		super();
		this.idE = id;
		this.descripcion = descripcion;
	}

	public int getIdE() {
		return idE;
	}

	public void setIdE(int idE) {
		this.idE = idE;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
		

}
