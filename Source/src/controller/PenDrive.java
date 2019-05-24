package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;
public class PenDrive {
	
	private boolean soLinux;
	
	public PenDrive()
	{
		soLinux = System.getProperties().getProperty("os.name").toLowerCase().contains("linux");
	}
	
	public ArrayList<String> listarMedias() {
		ArrayList<String> portas = new ArrayList<String>();
		File[] roots = null;		
		 
		FileSystemView fs = FileSystemView.getFileSystemView();
		
		if (soLinux) {
		     System.out.println("Sou linux");
		    roots = fs.getFiles(new File("/media/pi/"), true);
		     
		    for (File file : roots) {
		    	System.out.println(fs.getSystemDisplayName(file));
		    	if (fs.getSystemDisplayName(file).equals("SETTINGS"))
		    		continue;
		    	System.out.println("Econtrei:" + fs.getSystemDisplayName(file) + ", com o absP: " + file.getAbsolutePath());
		    	portas.add(file.getAbsolutePath());
		    }
		}else
		{
			System.out.println("Sou Windows");
		    roots = File.listRoots();
		     
		    for (File file : roots) {
		        String descricao = fs.getSystemTypeDescription(file);
		        if (descricao != null) {
		            if (descricao.endsWith("Unidade de USB")) {
		            	portas.add(file.getAbsolutePath());
		            }
		        }
		    }  
		}
		return portas;
		
	}
	
	public boolean salvarArquivo(String conteudo, String path) {
		String caminho;
		if (path != null)
		{
			FileWriter arq;
			try {
				if (soLinux)
					caminho = path + "//Backup.txt";
				else
					caminho = path + "\\Backup.txt";
				
				System.out.println(caminho);
				arq = new FileWriter(caminho);
				PrintWriter gravarArq = new PrintWriter(arq);
				 
			    gravarArq.printf(conteudo);

			    arq.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		    
		}
		return true;
	
	}
}
