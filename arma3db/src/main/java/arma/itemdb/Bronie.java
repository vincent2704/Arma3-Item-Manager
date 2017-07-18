package arma.itemdb;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bronie")
public class Bronie implements Serializable {

	@Id
	@GeneratedValue
	private int id;
	@Column(name = "model_broni", unique = true)
	private String model_broni;
	@Column(name = "ilosc")
	private int ilosc;
	@Column(name = "kaliber")
	private double kaliber;

	@JoinTable(name = "amunicja_bronie", joinColumns = @JoinColumn(name = "bronie_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "amunicja_id", referencedColumnName = "id"))
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Amunicja> amunicja;

	public Bronie() {

	}

	public Bronie(int id, String model_broni, int ilosc, double kaliber) {
		this.id = id;
		this.model_broni = model_broni;
		this.ilosc = ilosc;
		this.kaliber = kaliber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel_broni() {
		return model_broni;
	}

	public void setModel_broni(String model_broni) {
		this.model_broni = model_broni;
	}

	public int getIlosc() {
		return ilosc;
	}

	public void setIlosc(int ilosc) {
		this.ilosc = ilosc;
	}

	public double getKaliber() {
		return kaliber;
	}

	public void setKaliber(double kaliber) {
		this.kaliber = kaliber;
	}

	@Override
	public String toString() {
		return "Bronie [id=" + id + ", model_broni=" + model_broni + ", ilosc=" + ilosc + ", kaliber=" + kaliber + "]";
	}

	public Set<Amunicja> getAmunicja() {
		return amunicja;
	}

	public void setAmunicja(Set<Amunicja> amunicja) {
		this.amunicja = amunicja;
	}

}
