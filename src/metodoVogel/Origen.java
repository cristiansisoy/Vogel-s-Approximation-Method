package metodoVogel;

public class Origen {
	private double oferta;
	private String nombre;
	
	public Origen() {
		this.oferta = 0;
		this.nombre = null;
	}
	
	public Origen(double oferta, String nombre) {
		this.oferta = oferta;
		this.nombre = nombre;
	}
	
	public double getOferta() {
		return this.oferta;
	}
	
	public void setOferta(double oferta) {
		this.oferta = oferta;
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
