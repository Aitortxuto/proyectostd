package server;

public class Sensor {
	
	private String id;
	private String descripcion;
	private String estado;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Sensor(String id, String descripcion, String estado) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.estado = estado;
	}
}
