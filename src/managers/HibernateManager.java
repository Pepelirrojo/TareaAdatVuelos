package managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import general.Funcionalidad;
import general.Vuelo;

public class HibernateManager implements Funcionalidad {

	@Override
	public HashMap<Integer, Vuelo> verTodo(String tabla) {
		HashMap<Integer, Vuelo> misVuelos = new HashMap<>();
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Query q = s.createQuery("from Vuelo");
		List misVuelosList = q.getResultList();
		Iterator misVuelosIterator = misVuelosList.iterator();
		while(misVuelosIterator.hasNext()) {
			Vuelo miVuelo = (Vuelo) misVuelosIterator.next();
			misVuelos.put(miVuelo.getId(), miVuelo);
		}
		return misVuelos;
	}

	@Override
	public boolean insertar(String tabla, Vuelo miVuelo) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.save(miVuelo);
		s.getTransaction().commit();
		return true;
	}

	@Override
	public boolean borrar(String tabla, String id) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Vuelo miVuelo = (Vuelo) s.get(Vuelo.class, Integer.parseInt(id));
		s.beginTransaction();
		s.delete(miVuelo);
		s.getTransaction().commit();
		sf.close();
		return true;
	}

	@Override
	public boolean modificar(String tabla, String id, String campo, String nuevoRegistro) {
		boolean exito = false;
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		Query q = s.createQuery("UPDATE Vuelo SET " + campo + " = '" + nuevoRegistro + "' WHERE ID = " + id);
		q.executeUpdate();
		s.getTransaction().commit();
		return exito = true;
	}

	@Override
	public Vuelo buscar(String tabla, String id) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Vuelo miVuelo = (Vuelo) s.get(Vuelo.class, Integer.parseInt(id));
		return miVuelo;
	}

	@Override
	public boolean migrar(String tabla, String nombreArchivo, HashMap<Integer, Vuelo> misVuelos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNumColumnas(String tabla) {
		// TODO Auto-generated method stub
		return 8;
	}
}