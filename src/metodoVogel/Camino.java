package metodoVogel;

public class Camino {
	private String origen;
	private String destino;
	private double cantidad;
	private double costo;
	
	public Camino(String origen, String destino, double cantidad, double costo) {
		this.origen = origen;
		this.destino = destino;
		this.cantidad = cantidad;
		this.costo = costo;
	}

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}

	public double getCantidad() {
		return cantidad;
	}

	public double getCosto() {
		return costo;
	}
	
	public String toString() {
		String cadena = new String();
		
		cadena += String.format("%.0f",this.cantidad) + " transportado de " + this.origen + " a " + this.destino;
		
		
		return cadena;
	}
}
