package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txt;
    private JButton btn;

    public FileChooser() {
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 110);
        txt = new JTextField(30);
        add(txt);

        btn = new JButton("Buscar archivo");
        btn.addActionListener(this);
        add(btn);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    
	    FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("Archivos de texto", "txt", "rtf"); 
	    fileChooser.setFileFilter(imgFilter);

	    int result = fileChooser.showOpenDialog(this);

	    if (result != JFileChooser.CANCEL_OPTION) {

	        File fileName = fileChooser.getSelectedFile();

	        if ((fileName == null) || (fileName.getName().equals(""))) {
	            txt.setText("...");
	        } else {
	        	//Nueva forma de leer archivos 
	    		InputStream fIn;
	    		InputStreamReader isr = new InputStreamReader(System.in);
	    		BufferedReader buffer = new BufferedReader(isr);
	    		try {
	    			fIn = new FileInputStream(fileName);
	    			isr = new InputStreamReader(fIn);
	    			buffer = new BufferedReader(isr);
	    			
	    			String linea;
	    			
	    			while ((linea = buffer.readLine()) != null) {
	    				System.out.println(linea);
	    			}
	    			
	    			fIn.close();
	    			
	    		} catch(IOException io) {
	    			System.err.println("No se puede abrir el archivo");
	    		}
      	
	            txt.setText(fileName.getAbsolutePath());
	        }
	    }
	}
	
}