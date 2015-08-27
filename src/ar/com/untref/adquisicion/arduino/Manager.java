package ar.com.untref.adquisicion.arduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

public class Manager implements SerialPortEventListener {

	private static final String SEPARATOR_CHAR = ";";
	private OutputStream outputStream;
	private SerialPort serialPort;
	private InputStream inputStream;
	private StringBuilder paqueteDeLectura = new StringBuilder();
	private String[] valoresDeLosSensores;
	private List<PaqueteDeMedicion> paquetesLeidos = new ArrayList<PaqueteDeMedicion>();

	private String lectura;

	public Manager(CommPortIdentifier portId) {

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
			} catch (UnsupportedCommOperationException  e) {
				System.out.println(e);
			}

		} catch (PortInUseException e) {
			System.out.println(e);
		}
	}

	public void start() {
		
		serialPort.notifyOnDataAvailable(true);
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
				lectura = new String(readBuffer).trim();
				paqueteDeLectura.append(lectura);
				
				/*Si recibimos un punto y coma, entonces el paquete de lectura llego 
				 * completo y lo desglosamos*/
				if (!lectura.isEmpty() && 
						String.valueOf(lectura.charAt(lectura.length() - 1)).equals(SEPARATOR_CHAR)) {
					desglosarPaqueteDeLectura();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}


	private synchronized void desglosarPaqueteDeLectura() {
		int cantidadDeRepeticionesDelCaracterDeCorteDeLinea = new StringTokenizer(paqueteDeLectura.toString() , ";").countTokens();
		
		//A veces se desincroniza y envia dos lineas juntas
		if (cantidadDeRepeticionesDelCaracterDeCorteDeLinea == 1) {
			String paqueteDeLecturaProcesado = paqueteDeLectura.toString().replace(";", "");

			valoresDeLosSensores = paqueteDeLecturaProcesado.split("\\|");

			Double inclinacion = new Double(valoresDeLosSensores[0]);
			Integer error = new Integer(valoresDeLosSensores[1]);
			Double aceleracionx = new Double(valoresDeLosSensores[2]);
			Double aceleraciony = new Double(valoresDeLosSensores[3]);
			Double aceleracionz = new Double(valoresDeLosSensores[4]);
			Double temperatura = new Double(valoresDeLosSensores[5]);
			Double girox = new Double(valoresDeLosSensores[6]);
			Double giroy = new Double(valoresDeLosSensores[7]);
			Double giroz = new Double(valoresDeLosSensores[8]);
			
			System.out.println(inclinacion + "|" + error + "|" + aceleracionx + "|" + aceleraciony + "|"
					+ aceleracionz + "|" + temperatura + "|" + girox + "|" + giroy + "|" + giroz + "|");
			
			MedicionMagnetometro medicionMagnetometro = new MedicionMagnetometro(inclinacion, error);
			MedicionAcelerometro medicionAcelerometro = new MedicionAcelerometro(aceleracionx, aceleraciony, 
					aceleracionz, temperatura, girox, giroy, giroz);
			
			PaqueteDeMedicion paqueteDeMedicion = new PaqueteDeMedicion(medicionAcelerometro, medicionMagnetometro);
			paquetesLeidos.add(paqueteDeMedicion);
			
			paqueteDeLecturaProcesado = new String();
			paqueteDeLectura = new StringBuilder();
		}else{
			paqueteDeLectura = new StringBuilder();
		}
	}

}
