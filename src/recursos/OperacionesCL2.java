package recursos;

public interface OperacionesCL2 {//operaciones para listas simples y enlazadas
	
	public int buscar(Object elemento);
	public Object devolver(int posicion);
	public void eliminar(int posicion);
	public void limpiar();
	public boolean estaVacia();		
	public int tamanio();
	
}
