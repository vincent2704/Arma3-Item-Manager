package arma.itemdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "amunicja")
public class Amunicja implements Serializable {

	@Id
	@GeneratedValue
	private int id;
	@Column(name = "nazwa_amunicji")
	private String nazwa_amunicji;
	@Column(name = "kaliber")
	private double kaliber;
	@Column(name = "ilosc")
	private int ilosc;

	public Amunicja() {

	}

	public Amunicja(int id, String nazwa_amunicji, double kaliber, int ilosc) {
		super();
		this.id = id;
		this.nazwa_amunicji = nazwa_amunicji;
		this.kaliber = kaliber;
		this.ilosc = ilosc;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazwa_amunicji() {
		return nazwa_amunicji;
	}

	public void setNazwa_amunicji(String nazwa_amunicji) {
		this.nazwa_amunicji = nazwa_amunicji;
	}

	public double getKaliber() {
		return kaliber;
	}

	public void setKaliber(double kaliber) {
		this.kaliber = kaliber;
	}

	public int getIlosc() {
		return ilosc;
	}

	public void setIlosc(int ilosc) {
		this.ilosc = ilosc;
	}
	
	

}
