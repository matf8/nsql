package com.tallernosql.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Result;
import com.tallernosql.RDB.ReThinkDBFactory;
import com.tallernosql.entity.QError;

@Repository
public class ControladorError {
	@Autowired private static final ReThinkDBFactory connectionFactory = ReThinkDBFactory.getInstancia();      
	@Autowired private static final Connection con = connectionFactory.createConnection();   
	
	private static final RethinkDB r = RethinkDB.r;	
		
	public List<QError> getAll() {
		List<QError> ret = new ArrayList<QError>();		
		try (Result<QError> result = r.db("t2").table("errores").orderBy().optArg("index", "idE").run(con, QError.class)) {
		    for (QError doc : result) {
				ret.add(doc);
		    }
		}
		return ret;
	}
	
	public QError getError(int id) {
		QError ret = new QError();		
		try (Result<QError> result = r.db("t2").table("errores").getAll(id).optArg("index","idE").run(con, QError.class)) {
		    for (QError doc : result) {
				ret = doc;
		    }
		}
		return ret;
	}
	
	public ResponseEntity<Object> insertarError(QError e) {
    	//r.db("t2").table("errores").delete().run(con);
		try {  	    		
	    	r.db("t2").table("errores").insert
	    	(r.array(r.hashMap("idE", e.getIdE())
	    		         .with("descripcion", e.getDescripcion())))
	    		         .run(con, QError.class);	      
    	} catch (Exception f) {
    		f.printStackTrace();
    	}  	
    	return new ResponseEntity<>(e, HttpStatus.CREATED);

	}
	
}
