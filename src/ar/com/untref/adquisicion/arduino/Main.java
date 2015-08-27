package ar.com.untref.adquisicion.arduino;


import java.util.Enumeration;

import javax.comm.CommPortIdentifier;

public class Main {

	@SuppressWarnings("rawtypes")
	private static Enumeration portList;	

	public static void main(String[] args) {

		portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
//				if (portId.getName().equals("/dev/ttyUSB0")) {
					Manager manager = new Manager(portId);
					manager.start();
//				}
			}
		}
	}

}
