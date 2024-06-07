package contenedoresTransporte;

import java.util.Scanner;
import contenedores.Lista1DLinkedL;
import metodoVogel.Destino;

public class ListaDestinos extends Lista1DLinkedL {
	Scanner sc = new Scanner(System.in);

	@Override
	public boolean iguales(Object elementoL, Object elemento) {
		Destino d1 = (Destino)elementoL;
		Destino d2 = (Destino)elemento;
		
		return (d1.equals(d2));
	}
	
	public void cargaDestinos(int tamLista) {
		char letra = 64;
		String nombre = "Destino ";
		
		for(int i = 0; i < tamLista; i++) {
			letra++;
			System.out.print("Ingrese la demanda del Destino " + letra + ": ");
			this.insertar(new Destino(sc.nextDouble(), nombre + letra), i);
		}
	}
	
	public void cargaDestinos(String linea) {
		String[] valores;
		
		char letra = 64;
		String nombre = "Destino ";
		
		valores = linea.split("\t");
        for(int j = 1; j < valores.length; j++) {
        	letra++;
        	this.insertar(new Destino(Double.parseDouble(valores[j]), nombre + letra), j - 1);
        }
	}
	
	public Destino getDestino(int i) {
		return (Destino)devolver(i);
	}
	
	public double getDemandaTotal() {
		double total = 0;
		
		for(int i = 0; i < this.tamanio(); i++) {
			total += ((Destino)this.devolver(i)).getDemanda();
		}
		
		return total;
	}
	
	public void muestraDestinos() {
		for(int i = 0; i < this.tamanio(); i++) {
			System.out.println(this.devolver(i));
		}
	}
}
