package com.persistencia1.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;

import com.persistencia1.domain.Tramite;
import com.persistencia1.util.HibernateUtil;

public class Test {

	public static void main(String[] args) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		// ------------------------------
		// - 1 - Creo una instancia de Trámite
		// ------------------------------
		Tramite tramite = new Tramite("Credito", new Timestamp((new Date()).getTime()));
		
		// Persisto la entidad Trámite
		session.save(tramite);
		
		session.getTransaction().commit();
		
		session.close();
		HibernateUtil.getSessionFactory().close();
	}

}
