package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JOptionPane;

import com.sun.org.apache.xerces.internal.xs.StringList;

import controller.PenDrive;
import controller.Serial;
import gnu.io.CommPortIdentifier;

public class Model {
	private Serial serial = new Serial();
	private PenDrive pendrive = new PenDrive();
	private String resposta;
	public ArrayList<String> ListarPortas()
	{
		ArrayList<String> p = new ArrayList<String>();
		Enumeration portas = serial.PortasDisponiveis();
		
		 while (portas.hasMoreElements()) {
	            CommPortIdentifier portId = (CommPortIdentifier) portas.nextElement();
	            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	                p.add(portId.getName());
	            }
	        }
		 return p;
		
	}

	public void AbrirPorta(String selectedItem) {
		try {
			serial.openPort(selectedItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public String LerPorta()
	{
		resposta = "";
		try {
			resposta = serial.ReadSerial();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resposta;
	}

	public void closePort() {
		serial.close();
	}

	public ArrayList<String> listarPortasUSB() {
		
		 ArrayList<String> portas = pendrive.listarMedias();
		 
		if (portas.size() <= 0)
		{
			JOptionPane.showMessageDialog(null, "Insira um Pendrive na porta USB");
			return null;
		}
		
		return portas;
	}
	
	public boolean salvarBackup(String path)
	{
		return pendrive.salvarArquivo(resposta, path);
	}
}
