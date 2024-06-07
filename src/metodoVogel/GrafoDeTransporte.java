package metodoVogel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import contenedores.ListaDoubleLinkedL;
import contenedores.MatrizGrafo;
import contenedoresTransporte.*;
import grafoDirigido.AbsGrafoD;

public class GrafoDeTransporte extends AbsGrafoD {
	Scanner scanner = new Scanner(System.in);
	private ListaDestinos destinos;	//Lista de demanda
	private ListaOrigenes origenes;	//Lista de oferta
	private ListaCaminos caminos;
	private ListaDoubleLinkedL listaPenalizaciones;
	
	public GrafoDeTransporte(int fila, int columna) {
		super(fila, columna);
		destinos = new ListaDestinos();
		origenes = new ListaOrigenes();
		caminos = new ListaCaminos();
		listaPenalizaciones = new ListaDoubleLinkedL();
		
//		origenes.cargaOrigenes(fila);
//		destinos.cargaDestinos(columna);
	}

	@Override
	public void cargarGrafo() {
		double currCost;
		
		for(int i = 0; i < getOrden(); i++) {
			for(int j = 0; j < getOrden(); j++) {
				System.out.println("Ingrese costo[" + i + "," + j + "] (sino -1)");
				currCost = scanner.nextDouble();
				
				if(currCost != -1){
					this.matrizCosto.actualizar(currCost, i, j);	
				} else {
					this.matrizCosto.actualizar(infinito, i, j);
				}					
			}
		}
	}
	
	public void cargarGrafo(String archivo) {
		int indice = 0; //indice que recorre la matriz por filas
		InputStream fIn;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader buffer = new BufferedReader(isr);

		try {
			fIn = new FileInputStream(archivo);
			isr = new InputStreamReader(fIn);
			buffer = new BufferedReader(isr);
			String linea;
			String[] valores;
			
			indice = 0;
			while ((linea = buffer.readLine()) != null) {
				if(linea.contains("Lista de Ofertas:")) {
					origenes.cargaOrigenes(linea);
				} else {
					if(linea.contains("Lista de Demandas:")) {
						destinos.cargaDestinos(linea);
					} else {
						valores = linea.split("\t");
		                for(int j = 0; j < valores.length; j++) {
		                    this.matrizCosto.actualizar(Double.parseDouble(valores[j]), indice, j);
		                }
		                indice++;
					}
				}
            }
		}catch(IOException e) {
		System.out.println("error");
		}
	}
	
	public ListaDestinos getDestinos() {
		return destinos;
	}

	public ListaOrigenes getOrigenes() {
		return origenes;
	}

	public ListaCaminos getCaminos() {
		return caminos;
	}

	public ListaDoubleLinkedL getListaPenalizaciones() {
		return listaPenalizaciones;
	}
	
	public boolean hayMayorOferta() {
		return this.origenes.getOfertaTotal() > this.destinos.getDemandaTotal();
	}
	
	public boolean hayMayorDemanda() {
		return this.destinos.getDemandaTotal() > this.origenes.getOfertaTotal();
	}
	
	public boolean estaEquilibrado() {
		return this.destinos.getDemandaTotal() == this.origenes.getOfertaTotal();
	}

	private void equilibrar() {
		if(hayMayorOferta()) {
			agregarColumna();
		} else {
			if(hayMayorDemanda()) {
				agregarFila();
			}
		}
	}
	
	private void agregarColumna() {	//Oferta es mayor que demanda
		MatrizGrafo aux = new MatrizGrafo(this.matrizCosto.getNroFilas(), this.matrizCosto.getNroColumnas()+1);
		
		for(int fila = 0; fila < aux.getNroFilas(); fila++) {
			for( int columna = 0; columna < aux.getNroColumnas(); columna++) {
				if(columna == this.matrizCosto.getNroColumnas() ) {
					aux.actualizar(0.0, fila, columna);

				}else {
	
					aux.actualizar(this.matrizCosto.devolver(fila, columna) , fila, columna);
				}
			}
		}
		
		this.destinos.insertar(new Destino(this.origenes.getOfertaTotal() - this.destinos.getDemandaTotal(), "Destino ficticio"), this.destinos.tamanio());
		
		this.columna++;
		this.matrizCosto = aux;
	}
	
	private void agregarFila() {	//Demanda es mayor que oferta
		MatrizGrafo aux = new MatrizGrafo(this.matrizCosto.getNroFilas()+1, this.matrizCosto.getNroColumnas());
		
		for(int fila = 0; fila < aux.getNroFilas(); fila++) {
			for( int columna = 0; columna < aux.getNroColumnas(); columna++) {
				if(fila == this.matrizCosto.getNroFilas() ) {
					aux.actualizar(0.0, fila, columna);
				}else {
					aux.actualizar(this.matrizCosto.devolver(fila, columna) , fila, columna);
				}
			}
		}
		
		this.origenes.insertar(new Origen(this.destinos.getDemandaTotal() - this.origenes.getOfertaTotal(), "Origen ficticio"), this.origenes.tamanio());
		
		this.fila++;
		this.matrizCosto = aux;
	}
	
	public void vogel() {
		equilibrar();
		
		//GUARDO LOS DATOS PARA MOSTRAR POST METODO
		MatrizGrafo aux = new MatrizGrafo(this.matrizCosto.getNroFilas(), this.matrizCosto.getNroColumnas());
		for(int fila = 0; fila < aux.getNroFilas(); fila++) {
			for( int columna = 0; columna < aux.getNroColumnas(); columna++) {
				aux.actualizar(this.matrizCosto.devolver(fila, columna) , fila, columna);
			}
		}
		
		ListaDoubleLinkedL auxOrig = new ListaDoubleLinkedL();
		for(int i = 0; i < this.origenes.tamanio(); i++) {
			auxOrig.insertar(this.origenes.getOrigen(i).getOferta(), auxOrig.tamanio());
		}
		
		ListaDoubleLinkedL auxDest = new ListaDoubleLinkedL();
		for(int i = 0; i < this.destinos.tamanio(); i++) {
			auxDest.insertar(this.destinos.getDestino(i).getDemanda(), auxDest.tamanio());
		}
		
		
		//APLICACION DEL METODO VOGEL
		while(this.destinos.getDemandaTotal() + this.origenes.getOfertaTotal() > 0) {
			cargarPenalizaciones();
			
			int indice = indiceMayorPenalizacion();
			
			if(esFila(indice)) {
				double menor = menorCostoFila(indice);
				int columna = 0;
				
				while((double)matrizCosto.devolver(indice, columna) != menor) {
					columna++;
				}
				
				generarCamino(indice, columna, menor);
			} else {
				double menor = menorCostoColumna(indice);
				int fila = 0;
				
				while((double)matrizCosto.devolver(fila, (indice - this.getFila())) != menor) {
					fila++;
				}
				
				generarCamino(fila, (indice - this.getFila()), menor);
			}
		}
		
		
		//RECUPERACION DE DATOS
		this.matrizCosto = aux;
		for(int i = 0; i < this.origenes.tamanio(); i++) {
			this.origenes.getOrigen(i).setOferta((double)auxOrig.devolver(i));
		}
		for(int i = 0; i < this.destinos.tamanio(); i++) {
			this.destinos.getDestino(i).setDemanda((double)auxDest.devolver(i));
		}
	}
	
	public void muestraVogel() {
		vogel();
		
		System.out.println("\nCOSTO TOTAL DE TRANSPORTE: " + calcularCostoTotal());
		
		System.out.println(caminos);
	}
	
	private void generarCamino(int fila, int columna, double menor) {
		if(this.origenes.getOrigen(fila).getOferta() <= this.destinos.getDestino(columna).getDemanda()) {
			caminos.insertar(new Camino(this.origenes.getOrigen(fila).getNombre(),
					this.destinos.getDestino(columna).getNombre(), this.origenes.getOrigen(fila).getOferta(),
					menor), caminos.tamanio());
			
			this.destinos.getDestino(columna).setDemanda(this.destinos.getDestino(columna).getDemanda()
															- this.origenes.getOrigen(fila).getOferta());
			this.origenes.getOrigen(fila).setOferta(0);
			
			for(int i = 0; i < this.columna; i++) {
				matrizCosto.actualizar(infinito, fila, i);
			}
			
		} else {
			caminos.insertar(new Camino(this.origenes.getOrigen(fila).getNombre(),
					this.destinos.getDestino(columna).getNombre(), this.destinos.getDestino(columna).getDemanda(),
					menor), caminos.tamanio());
			
			this.origenes.getOrigen(fila).setOferta(this.origenes.getOrigen(fila).getOferta()
														- this.destinos.getDestino(columna).getDemanda());
			this.destinos.getDestino(columna).setDemanda(0);
			
			for(int i = 0; i < this.fila; i++) {
				matrizCosto.actualizar(infinito, i, columna);
			}
		}
	}
	
	private void cargarPenalizaciones() {
		double menor1, menor2, aux;
		listaPenalizaciones = new ListaDoubleLinkedL();
		
		for(int fila = 0; fila < this.getFila(); fila++) {
			menor1 = (double)matrizCosto.devolver(fila, 0);	//Inicializo el primer menor en el primer elemento de la fila
			menor2 = (double)matrizCosto.devolver(fila, 1);	//Inicializo el segundo menor en el segundo elemento de la fila
			
			for(int columna = 2; columna < this.columna; columna++) {
				if((double)matrizCosto.devolver(fila, columna) < menor1) {	//Comparo el menor1 con la casilla donde estoy parado
					aux = menor1;	//Guardo el menor1
					menor1 = (double)matrizCosto.devolver(fila, columna);	//Cambio el menor1 por el nuevo menor
					
					if(aux < menor2) {
						menor2 = aux;	//Si el antiguo menor1 es menor que menor2, lo guardo
					}
				} else {
					if((double)matrizCosto.devolver(fila, columna) < menor2) {	//Si la casilla no contiene un costo menor que menor1, la comparo con menor2
						menor2 = (double)matrizCosto.devolver(fila, columna);
					}
				}
			}
			
			if(menor1 <= menor2) {	//Calculo la diferencia de los dos menores costos de la fila
				aux = menor2 - menor1;
			} else {
				aux = menor1 - menor2;
			}
			//Guardo primero las penalizaciones de las filas
			listaPenalizaciones.insertar(aux, listaPenalizaciones.tamanio());
		}
		//Mismo procedimiento, ahora con las columnas
		for(int columna = 0; columna < this.getColumna(); columna++) {
			menor1 = (double)matrizCosto.devolver(0, columna);
			menor2 = (double)matrizCosto.devolver(1, columna);
			
			for(int fila = 2; fila < this.getFila(); fila++) {
				if((double)this.matrizCosto.devolver(fila, columna) < menor1) {
					aux = menor1;
					menor1 = (double)matrizCosto.devolver(fila, columna);
					
					if(aux < menor2) {
						menor2 = aux;
					}
				} else {
					if((double)matrizCosto.devolver(fila, columna) < menor2) {
						menor2 = (double)matrizCosto.devolver(fila, columna);
					}
				}
			}
			
			if(menor1 <= menor2) {
				aux = menor2 - menor1;
			} else {
				aux = menor1 - menor2;
			}
			//Guardo las penalizaciones de las columnas
			listaPenalizaciones.insertar(aux, listaPenalizaciones.tamanio());
		}
	}
	
	private int indiceMayorPenalizacion() {	//Metodo que busca la posicion de la mayor penalizacion de la matriz
		double mayor = 0;
		int indice = 0;
		ListaDoubleLinkedL listaIndices = new ListaDoubleLinkedL();	//Lista que guarda las penalizaciones con el mismo valor
		
		for(int i = 0; i < listaPenalizaciones.tamanio(); i++) {	//Recorro la lista de penalizaciones
			if((double)listaPenalizaciones.devolver(i) > mayor) {
				indice = i;											//Guardo el indice del mayor
				mayor = (double)listaPenalizaciones.devolver(i);	//Guardo el nuevo mayor
				listaIndices.limpiar();								//Reinicio la lista de repetidos
				listaIndices.insertar(i, listaIndices.tamanio());	//Ingreso el nuevo mayor a la lista de repetidos
			} else {
				if((double)listaPenalizaciones.devolver(i) == mayor) {
					listaIndices.insertar(i, listaIndices.tamanio());	//Ingreso la penalizacion repetida a la lista
				}
			}
		}
		
		//Si la lista de repetidos tiene mas de un elemento, busco cual de ellos tiene el menor costo en su fila o columna
		if(listaIndices.tamanio() > 1) {
			double menorCosto;
			indice = (int)listaIndices.devolver(0);	//Guardo el primero de los repetidos
			
			if(esFila((int)listaIndices.devolver(0))) {	//Inicializo menorCosto
				menorCosto = menorCostoFila((int)listaIndices.devolver(0));
			} else {
				menorCosto = menorCostoColumna((int)listaIndices.devolver(0));
			}
			
			for(int i = 1; i < listaIndices.tamanio(); i++) {
				if(esFila((int)listaIndices.devolver(i))) {	//Si el indice en cuestion se refiere a una fila
					if(menorCostoFila((int)listaIndices.devolver(i)) < menorCosto) {	//Si el menor costo de la fila es menor que menorCosto
						indice = (int)listaIndices.devolver(i);					//Guardo el indice del nuevo menor costo
						menorCosto = menorCostoFila((int)listaIndices.devolver(i));	//Actualizo menorCosto
					}
				} else {	//Si el indice en cuestion se refiere a una columna
					if(menorCostoColumna((int)listaIndices.devolver(i)) < menorCosto) {	//Si el menor costo de la columna es menor que menorCosto
						indice = (int)listaIndices.devolver(i);					//Guardo el indice del nuevo menor costo
						menorCosto = menorCostoColumna((int)listaIndices.devolver(i));	//Actualizo menorCosto
					}
				}
			}	//En caso de que los costos menores tambien sean iguales, se retorna el primer indice de los empatados
		}
		
		return indice;
	}
	
	private boolean esFila(int indice) {	//Determina si el indice ingresado hace referencia a una fila
		return indice < this.getFila();
	}
	
	private double menorCostoFila(int fila) {
		double menor = (double)matrizCosto.devolver(fila, 0);
		
		for(int columna = 1; columna < this.getColumna(); columna++) {
			if((double)matrizCosto.devolver(fila, columna) < menor) {
				menor = (double)matrizCosto.devolver(fila, columna);
			}
		}
		
		return menor;
	}
	
	private double menorCostoColumna(int columna) {
		columna = columna - this.getFila();
		double menor = (double)matrizCosto.devolver(0, columna);
		
		for(int fila = 1; fila < this.getFila(); fila++) {
			if((double)matrizCosto.devolver(fila, columna) < menor) {
				menor = (double)matrizCosto.devolver(fila, columna);
			}
		}
		
		return menor;
	}
	
	private double calcularCostoTotal() {
		return caminos.getCostoTotal();
	}
	
	public String muestraGrafoGui(){
		String cadena = new String();
		double currCost;
		
		for (int i = 0; i <= getFila() + 1; i++){
			for (int j = 0; j <= getColumna() + 1;j++){
				if(i == 0 && j == 0) {
					cadena += "Orig\\Dest\t";
				} else {
					if(i == 0) {
						if(j != getColumna() + 1) {
							cadena += destinos.getDestino(j - 1) + "\t";
						} else {
							cadena += "Oferta\n";
						}
					} else {
						if(j == 0) {
							if(i != getFila() + 1) {
								cadena += origenes.getOrigen(i - 1) + "\t";
							} else {
								cadena += "Demanda\t";
							}
						} else {
							if(i == getFila() + 1) {
								if(j != getColumna() + 1) {
									cadena += destinos.getDestino(j - 1).getDemanda() + "\t";
								} else {
									if(destinos.getDemandaTotal() == origenes.getOfertaTotal()) {
										cadena += destinos.getDemandaTotal();
									} else {
										cadena += destinos.getDemandaTotal() + "\\" + origenes.getOfertaTotal();
									}
								}
							} else {
								if(j == getColumna() + 1) {
									if(i != getFila() + 1) {
										cadena += origenes.getOrigen(i - 1).getOferta() + "\n";
									}
								} else {
									currCost = (double)this.matrizCosto.devolver(i - 1, j - 1);
									
									if (currCost != infinito){
										cadena += String.format("%.0f",currCost) + "\t";
									} else {
										cadena += "-\t";
									}
								}
							}
						}
					}
				}
			}			
		}
		return cadena;
	}
	

}
