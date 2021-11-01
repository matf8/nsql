package com.tallernosql.RDB;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

public class ReThinkDBFactory {	
	private static ReThinkDBFactory instancia = null;
	
	public static ReThinkDBFactory getInstancia() {
		if (instancia == null)
			instancia = new ReThinkDBFactory();
		return instancia;
	}	
		
	public static Connection createConnection() {       
		try {		      
			return RethinkDB.r.connection().hostname("127.0.0.1").connect();				           
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	/*public static IControladorUsuario getIControladorUSuario() {
		return new ControladorUsuario();
	}*/

}

