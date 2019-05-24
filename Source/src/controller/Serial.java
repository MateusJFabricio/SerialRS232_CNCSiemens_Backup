package controller;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class Serial {
	
	private SerialPort serialPort;
    public String recebeSerial = "";
	private InputStream in;
	private OutputStream out;
    
	public Enumeration PortasDisponiveis() {
		return CommPortIdentifier.getPortIdentifiers();
	}

	public void openPort(String portName) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("A porta ja esta em uso");
        } else {
        	System.out.println("Peguei a conexao com a porta");
        	int timeout = 1000;
        	int baudRate = 9600;
            CommPort commPort = portIdentifier.open(this.getClass().getName(), timeout);
  
            if (commPort instanceof SerialPort) {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
                serialPort.addEventListener(new SerialReader(in, this));
                serialPort.notifyOnDataAvailable(true);
            }
        }
    }
    
    public String ReadSerial() throws IOException
    { 	
    	String dado = recebeSerial;
    	recebeSerial = "";
    	return dado;
    }
     
    
    public static class SerialReader implements SerialPortEventListener {
    	  
        private final InputStream in;
        private final byte[] buffer = new byte[200000];
        private Serial serial;
  
        public SerialReader(InputStream in, Serial serial) {
            this.in = in;
            this.serial = serial;
        }
  
        @Override
        public void serialEvent(SerialPortEvent arg0) {
            int data;
            
            try {
                int len = 0;
                System.out.println("Recebendo");
                while (in.available() > 0) {
                	data = in.read();
                	buffer[len++] = (byte) data;
                }
                System.out.println("Finalizado");
                String dado = new String(buffer, 0, len);
                serial.recebeSerial = serial.recebeSerial + dado;
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

    }
    
    public void close() {
    	
    	if (serialPort != null) {
	    	closeSafely(in);
	    	closeSafely(out);
	    	 serialPort.close();
    	}
    }
    
    private void closeSafely(Closeable resource) {
    	try {
    	 resource.close();
    	} catch (IOException ex) {}                
    }
 
}
