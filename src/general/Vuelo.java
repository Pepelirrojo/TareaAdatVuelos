package general;

import java.util.ArrayList;
import java.util.function.Function;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

public class Vuelo {
	private int id;
	private String codigo_vuelo;
	private String origen;
	private String destino;
	private String fecha;
	private String hora;
	private String plazas_totales;
	private String plazas_disponibles;
	

	public Vuelo() {
	}

	@Override
	public String toString() {
		return "Vuelo [id=" + id + ", codigo_vuelo=" + codigo_vuelo + ", origen=" + origen + ", destino=" + destino
				+ ", fecha=" + fecha + ", hora=" + hora + ", plazas_totales=" + plazas_totales + ", plazas_disponibles="
				+ plazas_disponibles + "]";
	}
	public String toStringFile(String idFile) {
		return "Vuelo [id=" + idFile + ", codigo_vuelo=" + codigo_vuelo + ", origen=" + origen + ", destino=" + destino
				+ ", fecha=" + fecha + ", hora=" + hora + ", plazas_totales=" + plazas_totales + ", plazas_disponibles="
				+ plazas_disponibles + "]";
	}

	public Vuelo(int id, String codigo_vuelo, String origen, String destino, String fecha, String hora,
			String plazas_totales, String plazas_disponibles) {
		this.id = id;
		this.codigo_vuelo = codigo_vuelo;
		this.origen = origen;
		this.destino = destino;
		this.fecha = fecha;
		this.hora = hora;
		this.plazas_totales = plazas_totales;
		this.plazas_disponibles = plazas_disponibles;
	}
	
	public Vuelo(String codigo_vuelo, String origen, String destino, String fecha, String hora,
			String plazas_totales, String plazas_disponibles) {
		this.codigo_vuelo = codigo_vuelo;
		this.origen = origen;
		this.destino = destino;
		this.fecha = fecha;
		this.hora = hora;
		this.plazas_totales = plazas_totales;
		this.plazas_disponibles = plazas_disponibles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo_vuelo() {
		return codigo_vuelo;
	}

	public void setCodigo_vuelo(String codigo_vuelo) {
		this.codigo_vuelo = codigo_vuelo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getPlazas_totales() {
		return plazas_totales;
	}

	public void setPlazas_totales(String plazas_totales) {
		this.plazas_totales = plazas_totales;
	}

	public String getPlazas_disponibles() {
		return plazas_disponibles;
	}

	public void setPlazas_disponibles(String plazas_disponibles) {
		this.plazas_disponibles = plazas_disponibles;
	}	
}