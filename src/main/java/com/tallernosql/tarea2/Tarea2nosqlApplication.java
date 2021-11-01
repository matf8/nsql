package com.tallernosql.tarea2;

import java.util.List;

import org.springframework.boot.SpringApplication;
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

@SpringBootApplication
@RestController
public class Tarea2nosqlApplication {
		
	ControladorUsuario repositorioU = new ControladorUsuario();
	ControladorError repositorioE = new ControladorError();
		
	@PostMapping("/crearUsuario")
    public ResponseEntity<Object> agregarUsuario(@RequestBody Usuario usuario) {
		return this.repositorioU.create(usuario); 
	}

    @GetMapping("/obtenerUsuario")
    public ResponseEntity<Object> obtenerUsuario(@RequestParam String correo) {
        return repositorioU.find(correo);
    }
    
    @PostMapping("/agregarError")
    public ResponseEntity<Object> agregarError(@RequestBody QError e) {
		return this.repositorioE.insertarError(e); 
	}
    
    @GetMapping("/obtenerErrores")
    public List<QError> obtenerErrores() {
        return repositorioE.getAll();
    }
    
	public static void main(String[] args) {	
		SpringApplication.run(Tarea2nosqlApplication.class, args);
	}

}
