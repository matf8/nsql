package com.tallernosql.controller;

import com.rethinkdb.RethinkDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tallernosql.RDB.ReThinkDBFactory;
import com.tallernosql.entity.Rol;
import com.tallernosql.entity.Usuario;

import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Result;

@Repository
public class ControladorUsuario {	
	@Autowired private static final ReThinkDBFactory connectionFactory = ReThinkDBFactory.getInstancia();      
	@Autowired private static final Connection con = connectionFactory.createConnection();    
	@Autowired private static final ControladorError cE = new ControladorError();
	private static final RethinkDB r = RethinkDB.r;	
         
	public ResponseEntity<Object> create(Usuario u) { 
    	//r.db("t2").table("usuarios").delete().run(con);
		ResponseEntity<Object> f = find(u.getCorreo());		
    	if (!f.getStatusCode().equals(HttpStatus.CREATED)) {
	    	try {  	    		
		    	r.db("t2").table("usuarios").insert
		    	(r.array(r.hashMap("correo", u.getCorreo())
		    			.with("password", u.getPassword())		    			
		    		    .with("nombre", u.getNombre())
		    		    .with("apellido", u.getApellido())))
		    			.run(con, Usuario.class);	      
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
		else return new ResponseEntity<>(ret, HttpStatus.OK);
    }

   public ResponseEntity<Object> iniciarSesion(String correo, String password) {
	   boolean i = false;
	   Usuario u = null;
	   try (Result<Usuario> result = r.db("t2").table("usuarios").getAll(correo).optArg("index","correo").run(con, Usuario.class)) {
		   for (Usuario doc: result) 			 	
		    	if (doc.getCorreo().equals(correo) && doc.getPassword().equals(password)) {		    		
		    		i = true;
		    		u = doc;
		    	}		    		
		} 	catch (Exception e) {
			e.printStackTrace();
		}
	   
		if (i == true) 
			return new ResponseEntity<>("Log-in: " + i + "\n Welcome: " + u.getNombre(), HttpStatus.OK);
		else return new ResponseEntity<>(cE.getError(104), HttpStatus.BAD_REQUEST);	
   }
   
   public List<Usuario> getAll() {
		List<Usuario> ret = new ArrayList<Usuario>();		
		try (Result<Usuario> result = r.db("t2").table("usuarios").orderBy().optArg("index", "correo").run(con, Usuario.class)) {
		    for (Usuario doc : result) {
				ret.add(doc);
		    }
		}
		return ret;
	}
   
   public ResponseEntity<Object> agregarRol(Rol rol) { 
	   ResponseEntity<Object> f = find(rol.getCorreoRol());	
	   List<String> lR = new ArrayList<String>();
   	   if (f.getStatusCode().equals(HttpStatus.OK)) {
   		   Usuario u = (Usuario) f.getBody();
   		   if (rol.getPasswordRol().equals(u.getPassword())) {  
   			   Result<Rol> result = r.db("t2").table("roles").getAll(rol.getCorreoRol()).optArg("index","correoRol").run(con, Rol.class);
   			   for (Rol doc: result) 	
   				   lR.add(doc.getNombreRol());   			   
   			  
   			   if (!lR.contains(rol.getNombreRol())) {
   				//   u.agregarRol(rol.getNombreRol());   		  
		  	   	   try {    			
			  	    	r.db("t2").table("roles").insert
			  	    	(r.array(r.hashMap("correoRol", rol.getCorreoRol())
			  	    			.with("passwordRol", rol.getPasswordRol())		    			
			  	    		    .with("nombreRol", rol.getNombreRol())))
			  	    			.run(con, Rol.class);			  	    	
			  	    	return new ResponseEntity<>(rol, HttpStatus.OK);
		   		   }  catch (Exception e) {
		       		 e.printStackTrace();
		   		   } 
   			  }
   		} else { // password incorrecta
   			return new ResponseEntity<>(cE.getError(104), HttpStatus.BAD_REQUEST);
   		}   		 
   	  } else { // usuario no existe
     	  return new ResponseEntity<>(cE.getError(102), HttpStatus.BAD_REQUEST);
   	  } 
   	  return new ResponseEntity<>("El rol ya existía, no hubo modificaciones.",HttpStatus.OK);  
   } 
   
   public ResponseEntity<Object> eliminarRol(Rol rol) { 
	   List<String> lR = rol.getRoles();	   
	   ResponseEntity<Object> f = find(rol.getCorreoRol());		   
   	   if (f.getStatusCode().equals(HttpStatus.OK)) {
   		   Usuario u = (Usuario) f.getBody();
   		   if (rol.getPasswordRol().equals(u.getPassword())) {    
   			   List<String> rolesDB = new ArrayList<>();
   			   Result<Rol> result = r.db("t2").table("roles").getAll(rol.getCorreoRol()).optArg("index", "correoRol").run(con, Rol.class);
 			   for (Rol doc: result) 	
 				   rolesDB.add(doc.getNombreRol());  
 			   if (rolesDB.isEmpty())
 				   return new ResponseEntity<>(cE.getError(103), HttpStatus.BAD_REQUEST);
 			   else {
	 			   for (String s: lR) {
	 				   if (rolesDB.contains(s)) {
	 					  try {  
	 						 r.db("t2").table("roles").filter(r.hashMap("correoRol", rol.getCorreoRol())).delete().run(con, Rol.class);		 			   		
		 			   		 u.borrarRol(rol.getNombreRol());  
		 			   		 return new ResponseEntity<>("Roles " + lR.toString() + " eliminados", HttpStatus.OK); 					     	
				   		  } catch (Exception e) {
					       	 e.printStackTrace();
				   		  } 
	 				   }    			 
	 			   }
 			   }
   		} else { 
   			return new ResponseEntity<>(cE.getError(104), HttpStatus.BAD_REQUEST); 		// password incorrecta
   		}   		 
   	  } else { 
     	  return new ResponseEntity<>(cE.getError(102), HttpStatus.BAD_REQUEST);		// usuario no existe
   	  } 
   	  return new ResponseEntity<>("Sin modificaciones", HttpStatus.OK);  
   }    
   
}

