package com.persistencia2.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.persistencia2.domain.Tramite;
// import annotations.com.persistencia2.domain.Tramite_;
import com.persistencia2.util.HibernateUtil;
import com.persistencia2.util.TiempoUtil;

public class Test {

	public static void main(String[] args) {

		try 
		{
			Session session = HibernateUtil.getSessionFactory().openSession();
//			serializarNuevoTramite(session); 			// - 1 - Creo una instancia de Trámite
//			consultarTodosLosTramites(session);			// - 2 - Listado de todos los trámites (HQL)
//			consultarTodosLosTramitesCriteria(session); // - 3 - Listado de todos los trámites (CRITERIA)
			serializarActualizacionTramite(session); 	// - 4 - Update de un trámite
			
			session.close();
			HibernateUtil.getSessionFactory().close();
		} 
		catch (ExceptionInInitializerError e) 
		{
			System.out.println("Excepción lanzada desde util.HibernateUtil al crear la sesión Hibernate");
		} 
		catch (Exception e) 
		{
			System.out.println("Excepción en com.persistencia2.test.Test: " + e);
		}
		System.out.println("Finalizada la prueba.");
	}

	// -----------------------------------------------------
	// Serialización HIBERNATE :: Alta de entidad TRAMITE
	// -----------------------------------------------------
	private static void serializarNuevoTramite(Session session) {
		session.beginTransaction();

		Tramite tramite = new Tramite("Credito", TiempoUtil.ahora());
		session.save(tramite);

		session.getTransaction().commit();
	}
	
	// -----------------------------------------------------
	// Serialización HIBERNATE :: Update de entidad TRAMITE
	// -----------------------------------------------------
	private static void serializarActualizacionTramite(Session session) {
		session.beginTransaction();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		
		CriteriaQuery<Tramite> criteria = builder.createQuery( Tramite.class );
		Root<Tramite> root = criteria.from( Tramite.class );
		criteria.select( root );
		criteria.where( builder.equal(root.get( "idTrami" ), 1) );
		
		Tramite tramite_1 = session.createQuery(criteria).getSingleResult();
		tramite_1.setTipoTram("Credito modificado" + (new Date()).toString());

		session.update(tramite_1);
		
		session.getTransaction().commit();
	}

	// -----------------------------------------------------
	// Consulta HIBERNATE HQL :: Consulta de entidad TRAMITE
	// -----------------------------------------------------
	@SuppressWarnings("unchecked")
	private static void consultarTodosLosTramites(Session session) {
		Query<Tramite> query = session.createQuery("from Tramite where tipoTram = :tipoTram");
		query.setParameter("tipoTram", "Credito");
		List<Tramite> tramites = query.getResultList();
		System.out.println(tramites.toString());
	}

	// -----------------------------------------------------
	// Consulta HIBERNATE CRITERIA :: Consulta de entidad TRAMITE
	// -----------------------------------------------------
	@SuppressWarnings("unchecked")
	private static void consultarTodosLosTramitesCriteria(Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		
		CriteriaQuery<Tramite> criteria = builder.createQuery( Tramite.class );
		Root<Tramite> root = criteria.from( Tramite.class );
		criteria.select( root );
		
		// Sin generar clase de anotaciones 
		criteria.where( builder.equal(root.get( "tipoTram" ), "Credito") );
		
		// Generando clase de anotaciones
//		criteria.where( builder.equal(root.get( Tramite_.tipoTram ), "Credito") );
		
		List<Tramite> tramites = session.createQuery( criteria ).getResultList();
		System.out.println(tramites.toString());
	}
}
