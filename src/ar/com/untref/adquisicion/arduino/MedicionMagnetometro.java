package ar.com.untref.adquisicion.arduino;

public class MedicionMagnetometro {

	private Double inclinacion;
	private Integer error;
	
	public MedicionMagnetometro(Double inclinacion, Integer error) {
		super();
		this.inclinacion = inclinacion;
		this.error = error;
	}
	
	public Double getInclinacion() {
		return inclinacion;
	}
	public void setInclinacion(Double inclinacion) {
		this.inclinacion = inclinacion;
	}
	public Integer getError() {
		return error;
	}
	public void setError(Integer error) {
		this.error = error;
	}
	
}
