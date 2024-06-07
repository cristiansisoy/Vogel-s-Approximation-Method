package testing;

import metodoVogel.GrafoDeTransporte;

public class TestGrafoDeTransporte {

	public static void main(String[] args) {
		GrafoDeTransporte grafo = new GrafoDeTransporte(4, 4);
		
		grafo.cargarGrafo("Datos/mTransporte.txt");
		
		System.out.println(grafo.muestraGrafoGui());
//		grafo.muestraGrafo();
//		System.out.println();
//		grafo.equilibrar();
//		System.out.println();
//		grafo.muestraGrafo();
		
		
		grafo.muestraVogel();
		
		System.out.println("\n" + grafo.muestraGrafoGui());
	}

}
