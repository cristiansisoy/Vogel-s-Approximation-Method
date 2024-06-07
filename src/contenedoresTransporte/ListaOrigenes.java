package contenedoresTransporte;

import java.util.Scanner;
import contenedores.Lista1DLinkedL;
import metodoVogel.Destino;
import metodoVogel.Origen;

public class ListaOrigenes extends Lista1DLinkedL {
	Scanner sc = new Scanner(System.in);

	@Override
	public boolean iguales(Object elementoL, Object elemento) {
		Origen o1 = (Origen)elementoL;
		Origen o2 = (Origen)elemento;
		
		return (o1.equals(o2));
	}
	
	public void cargaOrigenes(int tamLista) {
		String nombre = "Origen ";
		
		for(int i = 0; i < tamLista; i++) {
			System.out.print("Ingrese la oferta del Origen " + (i + 1) + ": ");
			this.insertar(new Origen(sc.nextDouble(), nombre + (i + 1)), i);
		}
	}
	
	public void cargaOrigenes(String linea) {
		String[] valores;
		
		String nombre = "Origen ";
		
		valores = linea.split("\t");
        for(int j = 1; j < valores.length; j++) {
        	this.insertar(new Origen(Double.parseDouble(valores[j]), nombre + j), j - 1);
        }
	}
	
	public Origen getOrigen(int i) {
		return (Origen)devolver(i);
	}
	
	public double getOfertaTotal() {
		double total = 0;
		
		for(int i = 0; i < this.tamanio(); i++) {
			total += ((Origen)this.devolver(i)).getOferta();
		}
		
		return total;
	}
	
	public void muestraOrigenes() {
		for(int i = 0; i < this.tamanio(); i++) {
			System.out.println(this.devolver(i));
		}
	}
}
