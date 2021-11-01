package com.tallernosql.controller;

import com.rethinkdb.RethinkDB;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tallernosql.RDB.ReThinkDBFactory;
import com.tallernosql.entity.Usuario;
import com.tallernosql.entity.QError;

import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Result;

@Repository
public class ControladorUsuario {	
	@Autowired private static final ReThinkDBFactory connectionFactory = ReThinkDBFactory.getInstancia();      
	@Autowired private static final Connection con = connectionFactory.createConnection();    
	@Autowired private static final ControladorError cE = new ControladorError();
	private static final RethinkDB r = RethinkDB.r;	
         
	public ResponseEntity<Object> create(Usuario u) throws QError { 
    	//r.db("t2").table("usuarios").delete().run(con);
    	if (find(u.getCorreo()) == null) {
	    	try {  	    		
		    	r.db("t2").table("usuarios").insert
		    	(r.array(r.hashMap("correo", u.getCorreo())
		    			.with("password", u.getPassword())		    			
		    		    .with("nombre", u.getNombre())
		    		    .with("apellido", u.getApellido())))
		    			.run(con, Usuario.class);	      
	    		System.out.println("usuario: " + u.getNombre() + " " + u.getApellido());    	   	   
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}  	
   	   	} else return new ResponseEntity<>(cE.getError(101), HttpStatus.BAD_REQUEST);    		
    	return new ResponseEntity<>(u, HttpStatus.CREATED);
   }
	
   public ResponseEntity<Object> find(String correo) {
    	Usuario ret = null;    	 
		try (Result<Usuario> result = r.db("t2").table("usuarios").getAll(correo).optArg("index","correo").run(con, Usuario.class)) {
		    for (Usuario doc: result) {
		    	if (doc.getCorreo().equals(correo))
		    		ret = doc;
		    }
		}    		     		   	    	   
		catch (Exception e) {
    		e.printStackTrace();    
    	} 
		
		if(ret == null)
			return new ResponseEntity<>(cE.getError(102), HttpStatus.BAD_REQUEST); 
		else return new ResponseEntity<>(ret, HttpStatus.CREATED);
    }

}

