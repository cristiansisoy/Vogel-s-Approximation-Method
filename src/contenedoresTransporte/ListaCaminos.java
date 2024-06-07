package contenedoresTransporte;

import contenedores.Lista1DLinkedL;
import metodoVogel.Camino;

public class ListaCaminos extends Lista1DLinkedL {

	@Override
	public boolean iguales(Object elementoL, Object elemento) {
		Camino c1 = (Camino)elementoL;
		Camino c2 = (Camino)elemento;
		
		return c1.equals(c2);
	}
	
	public Camino getCamino(int i) {
		return (Camino)devolver(i);
	}
	
	public double getCostoTotal() {
		double total = 0;
		
		for(int i = 0; i < this.tamanio(); i++) {
			total += ((Camino)this.devolver(i)).getCosto() * ((Camino)this.devolver(i)).getCantidad();
		}
		
		return total;
	}
	
	public void muestraCaminos() {
		for(int i = 0; i < this.tamanio(); i++) {
			System.out.println(this.devolver(i));
		}
	}
	
	public String toString() {
		String cadena = new String();
		
		for(int i = 0; i < this.tamanio(); i++) {
			cadena += this.devolver(i) + "\n";
		}
		
		return cadena;
	}
}
