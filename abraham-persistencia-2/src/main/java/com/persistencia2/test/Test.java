package com.persistencia2.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
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
//			serializarActualizacionTramite(session); 	// - 4 - Update de un trámite
//			consultarCriteria_TramitesByTipoFecha(session, "Credito", new Date());
			consultarCriteria_TuplaByTipoFecha(session, "Credito", new Date());
			
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
		
		// Busca y actualiza la fecha para el TRAMITE con ID=1
		Tramite tramite = consultarCriteria_TramitesById(session, 1); 			
		tramite.setTipoTram("Credito modificado " + TiempoUtil.formateaFechaYyyyMMdd(new Date()));	
		
		// Serializa la modificación de TRAMITE.ID=1
		session.beginTransaction();
		session.update(tramite);
//		session.saveOrUpdate(tramite);	// otra opción de modificación: saveOrUpdate
//		session.delete(tramite); 		// opción de borrado: delete
		session.getTransaction().commit();
	}

	// -----------------------------------------------------
	// Consulta HIBERNATE HQL :: Consulta de entidad TRAMITE
	// -----------------------------------------------------
	@SuppressWarnings("unchecked")
	private static void consultarHQL_TramitesByTipoTram(Session session) {
		Query<Tramite> query = session.createQuery("from Tramite where tipoTram = :tipoTram");
		query.setParameter("tipoTram", "Credito");
		List<Tramite> tramites = query.getResultList();
		System.out.println(tramites.toString());
	}

	// -----------------------------------------------------
	// Consulta HIBERNATE CRITERIA :: Consulta de entidad TRAMITE por "tipoTram"
	// -----------------------------------------------------
	@SuppressWarnings("unchecked")
	private static void consultarCriteria_TramitesByTipoTram(Session session) {
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
	
	// -----------------------------------------------------
	// Consulta HIBERNATE CRITERIA :: Consulta de entidad TRAMITE por "id"
	// -----------------------------------------------------
	@SuppressWarnings("unchecked")
	private static Tramite consultarCriteria_TramitesById(Session session, int id) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		
		CriteriaQuery<Tramite> criteria = builder.createQuery( Tramite.class );
		Root<Tramite> root = criteria.from( Tramite.class );
		criteria.select( root );
		
		// Sin generar clase de anotaciones 
		criteria.where( builder.equal(root.get( "idTrami" ), id) );
		
		// Generando clase de anotaciones
//		criteria.where( builder.equal(root.get( Tramite_.idTrami ), id) );
		
		Tramite tramite = session.createQuery( criteria ).getSingleResult();
		System.out.println(tramite.toString());
		
		return tramite;
	}
	// -----------------------------------------------------
	// Consulta HIBERNATE CRITERIA :: Consulta de entidad TRAMITE por "fecha" & "tipo"
	// -----------------------------------------------------
	@SuppressWarnings("unchecked")
	private static List<Tramite> consultarCriteria_TramitesByTipoFecha(Session session, String tipo, Date fecha) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		
		CriteriaQuery<Tramite> criteria = builder.createQuery( Tramite.class );
		Root<Tramite> root = criteria.from( Tramite.class );
		criteria.select( root )
			.where( builder.and(
					builder.like(root.get( "tipoTram" ), tipo) 
					, builder.lessThan(root.get("fhcTram"), fecha)
//					builder.like(root.get( Tramite_.tipoTram ), tipo) 
//					, builder.lessThan( Tramite_.fhcTram ), fecha)
						)
					);
		
		List<Tramite> tramites = session.createQuery( criteria ).getResultList();
		System.out.println(tramites.toString());
		
		return tramites;
	}

	// -----------------------------------------------------
	// Consulta HIBERNATE CRITERIA :: Consulta de JAVAX.PERSISTENCE.TUPLA por "fecha" & "tipo"
	// -----------------------------------------------------
	@SuppressWarnings("unchecked")
	private static List<Tuple> consultarCriteria_TuplaByTipoFecha(Session session, String tipo, Date fecha) {
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = builder.createQuery( Tuple.class );
		
		Root <Tramite> root = criteria.from( Tramite.class );
		
		Path<Integer> idTramPath = root.get( "idTrami" );
		Path<Timestamp> fhcTramPath = root.get( "fhcTram" );
		
		criteria
			.multiselect( idTramPath, fhcTramPath )
			.where( 
				builder.and(
						builder.like(root.get( "tipoTram" ), tipo) 
						, builder.lessThan(root.get("fhcTram"), fecha)
//						builder.like(root.get( Tramite_.tipoTram ), tipo) 
//						, builder.lessThan( Tramite_.fhcTram ), fecha)
					)
				);

		List<Tuple> tuplas = session.createQuery( criteria ).getResultList();
		for( Tuple auxTupla : tuplas ) {
			System.out.println("\n TUPLA: " + auxTupla.get(idTramPath) + " " + auxTupla.get(fhcTramPath) );
// 			Esta instrucción es exactamente igual a la justo anterior
//			System.out.println("\n TUPLA: " + auxTupla.get(0) + " " + auxTupla.get(1) );
		}

		return tuplas;
	}

}