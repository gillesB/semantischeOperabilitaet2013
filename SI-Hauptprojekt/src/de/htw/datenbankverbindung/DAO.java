package de.htw.datenbankverbindung;

import java.sql.Connection;

public abstract class DAO<T> {
	
	ConnectionJDBC factory = ConnectionJDBC.getInstance();
	Connection connect = factory.getConnection();
	
	/**
	 * Liefert einen Eintrag in der DB mit dem passenden ID 
	 * @param id
	 * @return
	 */
	public abstract T findById(int id);
	
	//public abstract T getAll();
	
}
