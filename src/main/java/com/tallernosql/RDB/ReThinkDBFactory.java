package com.tallernosql.RDB;

import org.springframework.context.annotation.Configuration;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

@Configuration
public class ReThinkDBFactory {	
	private static ReThinkDBFactory instancia = null;
	private static final String host = "127.0.0.1";
	//private static final String port = "8090";			
	
	public static ReThinkDBFactory getInstancia() {
		if (instancia == null)
			instancia = new ReThinkDBFactory();
		return instancia;
	}	
		
	public static Connection createConnection() {       
		try {		      
			return RethinkDB.r.connection().hostname(host).connect();				           
        } catch (Exception e) {        	
        		throw new RuntimeException(e);
        }        
	}
}

