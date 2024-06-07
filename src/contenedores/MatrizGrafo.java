package contenedores;

public class MatrizGrafo extends MatrizArr{
	
	public MatrizGrafo(int ordenGrafo){
		super(ordenGrafo, ordenGrafo);
	}
	
	public MatrizGrafo(int fila, int columna) {
		super(fila, columna);
	}
	
	public boolean areConnected(int i, int j){
		boolean response=false;
		if (i>=0 && i<getNroFilas() && j>=0 && j<getNroColumnas()){
			if (this.matriz[i][j]!=null){
				response=true;
			}
		}				
		return response;
	}
	
}
