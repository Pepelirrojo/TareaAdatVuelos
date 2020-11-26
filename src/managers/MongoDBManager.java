package managers;

import java.util.HashMap;
import java.util.Map.Entry;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.*;

import general.Funcionalidad;
import general.Vuelo;

public class MongoDBManager implements Funcionalidad {
	private MongoClient mongo;
	private MongoDatabase db;

	public MongoDBManager() {
		try {
			mongo = new MongoClient("localhost", 27017);
			db = mongo.getDatabase("adat_vuelos");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public HashMap<Integer, Vuelo> verTodo(String tabla) {
		HashMap<Integer, Vuelo> misVuelos = new HashMap<>();
		MongoCollection coleccionVuelos = db.getCollection(tabla);
		FindIterable fi = coleccionVuelos.find();
		MongoCursor cur = fi.cursor();
		while (cur.hasNext()) {
			Document doc = (Document) cur.next();
			String plazasTotales = Integer.toString(doc.getInteger("plazas_totales"));
			String plazasDisponibles = Integer.toString(doc.getInteger("plazas_disponibles"));
			Vuelo miVuelo = new Vuelo(doc.getInteger("id"), doc.getString("codigo"), doc.getString("origen"),
					doc.getString("destino"), doc.getString("fecha"), doc.getString("hora"), plazasTotales,
					plazasDisponibles);
			misVuelos.put(miVuelo.getId(), miVuelo);
		}
		return misVuelos;
	}

	@Override
	public boolean insertar(String tabla, Vuelo miVuelo) {
		boolean exito = false;
		try {
			MongoCollection coleccionVuelos = db.getCollection(tabla);
			int id = getNumColumnas(tabla);
			id++;
			Document doc = new Document("id", id).append("codigo", miVuelo.getCodigo_vuelo())
					.append("origen", miVuelo.getOrigen()).append("destino", miVuelo.getDestino())
					.append("fecha", miVuelo.getFecha()).append("hora", miVuelo.getHora())
					.append("plazas_totales", Integer.parseInt(miVuelo.getPlazas_totales()))
					.append("plazas_disponibles", Integer.parseInt(miVuelo.getPlazas_disponibles()));
			coleccionVuelos.insertOne(doc);
			exito = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return exito;
	}

	@Override
	public boolean borrar(String tabla, String id) {
		boolean exito = false;
		try {
			MongoCollection coleccionVuelos = db.getCollection(tabla);
			Document doc = new Document("id", Integer.parseInt(id));
			coleccionVuelos.deleteOne(doc);
			exito = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return exito;
	}

	@Override
	public boolean modificar(String tabla, String id, String campo, String nuevoRegistro) {
		boolean exito = false;
		try {
			MongoCollection coleccionVuelos = db.getCollection(tabla);
			Document vueloAMod = new Document("id", Integer.parseInt(id));
			Document cambios = new Document(campo, nuevoRegistro);
			Document auxSet = new Document("$set", cambios);
			coleccionVuelos.updateOne(vueloAMod, auxSet);
			exito = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return exito;
	}

	@Override
	public Vuelo buscar(String tabla, String id) {
		Vuelo miVuelo = null;
		MongoCollection coleccionVuelos = db.getCollection(tabla);
		Document doc = new Document("id", Integer.parseInt(id));
		FindIterable<Document> fi = coleccionVuelos.find(doc);
		Document docAux = fi.first();
		String plazasTotales = Integer.toString(docAux.getInteger("plazas_totales"));
		String plazasDisponibles = Integer.toString(docAux.getInteger("plazas_disponibles"));
		miVuelo = new Vuelo(doc.getInteger("id"), docAux.getString("codigo"), docAux.getString("origen"),
				docAux.getString("destino"), docAux.getString("fecha"), docAux.getString("hora"), plazasTotales,
				plazasDisponibles);
		return miVuelo;
	}

	@Override
	public boolean migrar(String tabla, String nombreArchivo, HashMap<Integer, Vuelo> vuelosEmigrar) {
		boolean exito = false;
		try {
			db.createCollection(nombreArchivo);
			for (Entry<Integer, Vuelo> entry : vuelosEmigrar.entrySet()) {
				insertar(nombreArchivo, entry.getValue());
			}
			exito = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return exito;
	}

	@Override
	public int getNumColumnas(String tabla) {
		MongoCollection coleccionVuelos = db.getCollection(tabla);
		FindIterable fi = coleccionVuelos.find();
		MongoCursor cur = fi.cursor();
		int numRegistros = 0;
		while (cur.hasNext()) {
			cur.next();
			numRegistros++;
		}
		return numRegistros;
	}

}
