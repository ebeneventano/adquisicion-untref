package ar.com.untref.adquisicion.arduino;

public class MedicionAcelerometro {

	private Double aceleracionx;
	private Double aceleraciony;
	private Double aceleracionz;
	private Double temperatura;
	private Double girox;
	private Double giroy;
	private Double giroz;
	
	public MedicionAcelerometro(Double aceleracionx, Double aceleraciony,
			Double aceleracionz, Double temperatura, Double girox,
			Double giroy, Double giroz) {
		super();
		this.setAceleracionx(aceleracionx);
		this.setAceleraciony(aceleraciony);
		this.setAceleracionz(aceleracionz);
		this.setTemperatura(temperatura);
		this.setGirox(girox);
		this.setGiroy(giroy);
		this.setGiroz(giroz);
	}

	public Double getAceleracionx() {
		return aceleracionx;
	}

	public void setAceleracionx(Double aceleracionx) {
		this.aceleracionx = aceleracionx;
	}

	public Double getAceleraciony() {
		return aceleraciony;
	}

	public void setAceleraciony(Double aceleraciony) {
		this.aceleraciony = aceleraciony;
	}

	public Double getAceleracionz() {
		return aceleracionz;
	}

	public void setAceleracionz(Double aceleracionz) {
		this.aceleracionz = aceleracionz;
	}

	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}

	public Double getGirox() {
		return girox;
	}

	public void setGirox(Double girox) {
		this.girox = girox;
	}

	public Double getGiroy() {
		return giroy;
	}

	public void setGiroy(Double giroy) {
		this.giroy = giroy;
	}

	public Double getGiroz() {
		return giroz;
	}

	public void setGiroz(Double giroz) {
		this.giroz = giroz;
	}
	
	
}
