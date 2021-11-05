package com.tallernosql;

import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tallernosql.controller.ControladorError;
import com.tallernosql.controller.ControladorUsuario;
import com.tallernosql.entity.QError;
import com.tallernosql.entity.Usuario;
import com.tallernosql.entity.Rol;

@SpringBootApplication
@RestController
public class Tarea2nosqlApplication {
		
	ControladorUsuario repositorioU = new ControladorUsuario();
	ControladorError repositorioE = new ControladorError();

	@GetMapping("/obtenerErrores")
    public List<QError> obtenerErrores() {
        return repositorioE.getAll();
    }

	@PostMapping("/crearUsuario")
    public ResponseEntity<Object> agregarUsuario(@RequestBody Usuario usuario) {
		return repositorioU.create(usuario); 
	}
		
    @GetMapping("/obtenerUsuario")
    public ResponseEntity<Object> obtenerUsuario(@RequestParam String correo) {
        return repositorioU.find(correo);
    }
            
    @PostMapping("/iniciarSesion")
    public ResponseEntity<Object> iniciarSesion(@RequestBody Map<String, String> login) {
    	String correo = login.get("correo");
    	String passwd = login.get("password");
		return repositorioU.iniciarSesion(correo,passwd); 
	}
    
    @PostMapping("/agregarError")
    public ResponseEntity<Object> agregarError(@RequestBody QError e) {
		return repositorioE.insertarError(e); 
	}   
      
    @GetMapping("/obtenerUsuarios")
    public List<Usuario> obtenerUsuarios() {
        return repositorioU.getAll();
    }
    
    @PostMapping("/agregarRol")
    public ResponseEntity<Object> agregarRol(@RequestBody Rol r) {
        return repositorioU.agregarRol(r);
    }
        
    @PostMapping("/eliminarRol")
    public ResponseEntity<Object> eliminarRol(@RequestBody Rol r) {
        return repositorioU.eliminarRol(r);
    }
           
	public static void main(String[] args) {	
		SpringApplication.run(Tarea2nosqlApplication.class, args);
	}

}
