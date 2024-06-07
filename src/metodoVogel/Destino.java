package metodoVogel;

public class Destino {
	private double demanda;
	private String nombre;
	
	public Destino() {
		this.demanda = 0;
		this.nombre = null;
	}
	
	public Destino(double demanda, String nombre) {
		this.demanda = demanda;
		this.nombre = nombre;
	}
	
	public double getDemanda() {
		return this.demanda;
	}
	
	public void setDemanda(double demanda) {
		this.demanda = demanda;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String toString() {
		String cadena = new String();
		
		cadena += this.nombre;
		
		return cadena;
	}
}
