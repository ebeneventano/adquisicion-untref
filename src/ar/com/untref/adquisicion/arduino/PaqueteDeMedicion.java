package ar.com.untref.adquisicion.arduino;

public class PaqueteDeMedicion {

	private MedicionAcelerometro medicionAcelerometro;
	private MedicionMagnetometro medicionMagnetometro;
	
	public PaqueteDeMedicion(MedicionAcelerometro medicionAcelerometro,
			MedicionMagnetometro medicionMagnetometro) {
		super();
		this.medicionAcelerometro = medicionAcelerometro;
		this.medicionMagnetometro = medicionMagnetometro;
	
	}
	public MedicionAcelerometro getMedicionAcelerometro() {
		return medicionAcelerometro;
	}
	
	public void setMedicionAcelerometro(MedicionAcelerometro medicionAcelerometro) {
		this.medicionAcelerometro = medicionAcelerometro;
	}
	
	public MedicionMagnetometro getMedicionMagnetometro() {
		return medicionMagnetometro;
	}
	
	public void setMedicionMagnetometro(MedicionMagnetometro medicionMagnetometro) {
		this.medicionMagnetometro = medicionMagnetometro;
	}
	
}
