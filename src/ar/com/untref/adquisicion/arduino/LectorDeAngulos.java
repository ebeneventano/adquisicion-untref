package ar.com.untref.adquisicion.arduino;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

public class LectorDeAngulos implements SerialPortEventListener {

	private int cantidadDeAngulos;
	private OutputStream outputStream;
	private SerialPort serialPort;
	private InputStream inputStream;
	private StringBuilder paqueteDeLectura = new StringBuilder();
	private String[] valoresDeLosSensores;

	public LectorDeAngulos(int cantidadDeAngulos, CommPortIdentifier portId) {

		this.cantidadDeAngulos = cantidadDeAngulos;

		try {
			serialPort = (SerialPort) portId.open("Simple", 2000);

			try {
				outputStream = serialPort.getOutputStream();
				inputStream = serialPort.getInputStream();
			} catch (IOException e) {
				System.out.println(e);
			}
			try {
				serialPort.addEventListener(this);
			} catch (TooManyListenersException e) {
				System.out.println(e);
			}
			try {
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			} catch (UnsupportedCommOperationException e) {
				System.out.println(e);
			}
			
		} catch (PortInUseException e) {
			System.out.println(e);
		}
	}

	public void startReading() {
		
		try {
			
			serialPort.notifyOnDataAvailable(true);
			outputStream.write(this.cantidadDeAngulos);
			outputStream.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void serialEvent(SerialPortEvent event) {
		
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:

			try {
				byte[] readBuffer = new byte[inputStream.available()];

				while (inputStream.available() > 0) {
					inputStream.read(readBuffer);
				}

				String lectura = new String(readBuffer).trim();
				paqueteDeLectura.append(lectura);
				
				if (lectura.contains("-")) {

					desglosarPaqueteDeLectura();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private void desglosarPaqueteDeLectura() {
		String paqueteDeLecturaProcesado = paqueteDeLectura.toString().replace("-", "");
		setValoresDeLosSensores((paqueteDeLecturaProcesado).split("/"));
		paqueteDeLectura = new StringBuilder();
	}

	public String[] getValoresDeLosSensores() {
		return valoresDeLosSensores;
	}

	public void setValoresDeLosSensores(String[] valoresDeLosSensores) {
		this.valoresDeLosSensores = valoresDeLosSensores;
	}

}
