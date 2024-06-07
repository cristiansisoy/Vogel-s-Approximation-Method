package gui;

import metodoVogel.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuGUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private Container contenedor;
	private JScrollPane scrollMatriz, scrollMatrizEq, scrollOferta, scrollDemanda, scrollCaminos, scrollVogel;
	private JLabel etMatriz, etMatrizEq, etOferta, etDemanda, etCaminos, etResultado;
	private JPanel panelTxt, panelListas, panelResultados, panelMatriz, panelMatrizOriginal, panelMatrizEquilibrada;
	private JPanel  panelCaminos, panelVogel, panelOferta, panelDemanda;
	private JTextField resultVogel, txtOferta, txtDemanda;
	private JMenuBar menuBar;
	private JMenuItem mCargar, mVogel;
	private JTextArea txtMatrizCosto, txtMatrizEq, txtCamino;
	private GrafoDeTransporte grafo = null;
	
	public MenuGUI(){
		this.setTitle("Problema de Transporte: Metodo Vogel");
		this.setSize(900,450);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		contenedor = getContentPane();
		contenedor.setLayout(new GridLayout(2,1));
		setLocationRelativeTo(null);

		setVisible(true);

		armarBotones();
		armarPanelTexto();
		armarPanelResultados();
	}
	
	private void armarBotones() {
	    this.mCargar = new JMenuItem("Cargar");
	    this.mCargar.addActionListener(this);
	    this.mCargar.setBackground(Color.LIGHT_GRAY);
	    
	    this.mVogel = new JMenuItem("Metodo Vogel");
	    this.mVogel.addActionListener(this);
	    this.mVogel.setBackground(Color.LIGHT_GRAY);
	    
	    this.menuBar = new JMenuBar();
	    this.menuBar.add(mCargar);
//	    this.menuBar.add(mMostrar);
	    this.menuBar.add(mVogel);
	    
	    setJMenuBar(menuBar);
	}
	
	private void armarPanelTexto() {
		panelTxt = new JPanel();
		panelTxt.setLayout(new BorderLayout());
		
		armarPanelMatriz();
//	    armarPanelListas();
	    
	    contenedor.add(panelTxt);
	}
	
	private void armarPanelMatriz() {
		panelMatriz = new JPanel();
		panelMatriz.setLayout(new BorderLayout());
		
		armarPanelMatrizOriginal();
		
		panelTxt.add(panelMatriz, BorderLayout.CENTER);
	}
	
	private void armarPanelMatrizOriginal() {
		panelMatrizOriginal = new JPanel();
		panelMatrizOriginal.setLayout(new BorderLayout());
		
		etMatriz = new JLabel("Matriz de costos");
		
		txtMatrizCosto = new JTextArea();
		txtMatrizCosto.setEditable(false);
		txtMatrizCosto.setText("Costo");
		
		scrollMatriz = new JScrollPane(txtMatrizCosto);
		
		panelMatrizOriginal.add(etMatriz, BorderLayout.NORTH);
		panelMatrizOriginal.add(scrollMatriz);

		panelMatriz.add(panelMatrizOriginal, BorderLayout.CENTER);
	}
	
	private void armarPanelMatrizEquilibrada() {
		panelMatrizEquilibrada = new JPanel();
		panelMatrizEquilibrada.setLayout(new BorderLayout());
		
		etMatrizEq = new JLabel("Matriz de costos equilibrada");
		
		txtMatrizEq = new JTextArea();
		txtMatrizEq.setEditable(false);
		txtMatrizEq.setText("" + this.grafo.muestraGrafoGui());
		
		scrollMatrizEq = new JScrollPane(txtMatrizEq);
		
		panelMatrizEquilibrada.add(etMatrizEq, BorderLayout.NORTH);
		panelMatrizEquilibrada.add(scrollMatrizEq);
		
		panelMatriz.add(panelMatrizEquilibrada);
	}
	
	private void armarPanelListas() {
		panelListas = new JPanel();
		panelListas.setLayout(new GridLayout(2,1));
		
		armarPanelOferta();
		armarPanelDemanda();
		
		panelTxt.add(panelListas);
	}
	
	private void armarPanelOferta() {
		panelOferta = new JPanel();
		panelOferta.setLayout(new BorderLayout());
		
		etOferta = new JLabel("Oferta");
		txtOferta = new JTextField();	
		txtOferta.setEditable(false);
		txtOferta.setText("Lista de oferta");
		
		scrollOferta = new JScrollPane(txtOferta);
		
		panelOferta.add(etOferta, BorderLayout.PAGE_START);
		panelOferta.add(scrollOferta, BorderLayout.CENTER);
		
		panelListas.add(panelOferta);
	}
	
	private void armarPanelDemanda() {
		panelDemanda = new JPanel();
		panelDemanda.setLayout(new BorderLayout());
		
		etDemanda = new JLabel("Demanda");
		txtDemanda = new JTextField();
		txtDemanda.setEditable(false);
		txtDemanda.setText("Lista demanda");
		
		scrollDemanda = new JScrollPane(txtDemanda);
		
		panelDemanda.add(etDemanda, BorderLayout.PAGE_START);
		panelDemanda.add(scrollDemanda, BorderLayout.CENTER);
		
		panelListas.add(panelDemanda);
	}
	
	private void armarPanelResultados() {
		panelResultados = new JPanel();
		panelResultados.setLayout(new BorderLayout());
		
		armarPanelVogel();
		armarPanelCaminos();
		
		contenedor.add(panelResultados);
	}
	
	private void armarPanelVogel() {
		panelVogel = new JPanel();
		panelVogel.setLayout(new BorderLayout());
		
		etResultado = new JLabel("Costo Total de Transporte");
		
		resultVogel = new JTextField();
		resultVogel.setText("Costo Total");
		resultVogel.setEditable(false);
		resultVogel.setBackground(Color.WHITE);
		
		scrollVogel = new JScrollPane(resultVogel);
		
		panelVogel.add(etResultado, BorderLayout.PAGE_START);
		panelVogel.add(scrollVogel, BorderLayout.CENTER);
		
		panelResultados.add(panelVogel, BorderLayout.PAGE_START);
	}
	
	private void armarPanelCaminos() {
		panelCaminos = new JPanel();
		panelCaminos.setLayout(new BorderLayout());
		
		etCaminos = new JLabel("Caminos");
		
		txtCamino = new JTextArea();
		txtCamino.setEditable(false);
		txtCamino.setText("Lista de caminos");
		
		scrollCaminos = new JScrollPane(txtCamino);
		
		panelCaminos.add(etCaminos, BorderLayout.PAGE_START);
		panelCaminos.add(scrollCaminos, BorderLayout.CENTER);
		
		panelResultados.add(panelCaminos, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == this.mCargar) {
			Cargar();
			
			if(panelMatriz.getLayout() instanceof GridLayout) {
				panelMatriz.remove(panelMatrizEquilibrada);
				panelMatriz.setLayout(new BorderLayout());
				panelMatriz.add(panelMatrizOriginal, BorderLayout.CENTER);
				panelMatriz.revalidate();
			}
			
			this.txtMatrizCosto.setText("" + this.grafo.muestraGrafoGui());  
		}
		
		if(e.getSource() == this.mVogel) {
			if(this.grafo != null) {
				if(!grafo.estaEquilibrado()) {
					if(grafo.hayMayorOferta()) {
						JOptionPane.showMessageDialog(null, "Problema desequilibrado\nSe agrego una columna");
					} else {
						if(grafo.hayMayorDemanda()) {
							JOptionPane.showMessageDialog(null, "Problema desequilibrado\nSe agrego una fila");
						}
					}
					
					grafo.vogel();
					panelMatriz.setLayout(new GridLayout(1,2));
					panelMatriz.revalidate();
					panelMatriz.add(panelMatrizOriginal);
					armarPanelMatrizEquilibrada();
				} else {
					grafo.vogel();
				}
				
				resultVogel.setText("$" + grafo.getCaminos().getCostoTotal());
				txtCamino.setText("" + grafo.getCaminos());
			} else {
				JOptionPane.showMessageDialog(null, "Error. No se cargaron los archivos");
			}
		}
	}
	
	public void Cargar() {
		int contFilas = 0;
		int contColumnas = 0;
		String direccion = "";
		
		Object [] dest;
		Object [] orig;
		
		JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    
	    FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("Archivos de texto", "txt", "rtf"); 
	    fileChooser.setFileFilter(imgFilter);

	    int result = fileChooser.showOpenDialog(this);

	    if (result != JFileChooser.CANCEL_OPTION) {

	        File fileName = fileChooser.getSelectedFile();
	        direccion = fileName.getAbsolutePath();
	        
	        if ((fileName == null) || (fileName.getName().equals(""))) {
	        	txtMatrizCosto.setText("...");
	        } else {
	    		InputStream fIn;
	    		InputStreamReader isr = new InputStreamReader(System.in);
	    		BufferedReader buffer = new BufferedReader(isr);
	    		try {
	    			fIn = new FileInputStream(direccion);
	    			isr = new InputStreamReader(fIn);
	    			buffer = new BufferedReader(isr);
	    			String linea;
	    			String [] valores;
	    			
	    			while (!(linea = buffer.readLine()).contains("Lista de Ofertas:")) {
	    				if(contFilas == 0) {
	    					valores = linea.split("\t");
	    					contColumnas = valores.length;
	    				}
	    				contFilas +=1;
	    			}
	    		} catch (Exception e2) {
	    			
	    		}
	        }
	        this.grafo = new GrafoDeTransporte(contFilas, contColumnas);
			this.grafo.cargarGrafo(direccion);
			
			orig = new Object [this.grafo.getFila()];
			dest = new Object [this.grafo.getColumna()];
			
			for(int i = 0; i < grafo.getFila(); i++) {
				orig [i] = (this.grafo.getOrigenes().getOrigen(i));
			}
			for(int i = 0 ; i < grafo.getColumna(); i++) {
				dest [i] = (this.grafo.getDestinos().getDestino(i));
			}	
	    }
	}
	
}