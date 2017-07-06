package arma.itemdao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import arma.itemdb.Bronie;
import arma.itemdb.DatabaseHandler;

public class BronieDao implements Serializable {

	private static int iloscBroni;

	// dodanie broni do bazy danych. ukonczone!
	public static void registerBron(String model, int ilosc, double kaliber) {
		EntityManager em = DatabaseHandler.getEntityManager();

		em.getTransaction().begin();
		Bronie bron = new Bronie();
		bron.setModel_broni(model);
		bron.setIlosc(ilosc);
		bron.setKaliber(kaliber);
		em.persist(bron);
		em.getTransaction().commit();
	}




	// zwraca liste wszystkich obiektow typu Bronie
	public static List<Bronie> getListaBroni() {

		EntityManager em = DatabaseHandler.getEntityManager();
		String qString = "SELECT b FROM Bronie b";

		TypedQuery<Bronie> q = em.createQuery(qString, Bronie.class);

		List<Bronie> result = q.getResultList();

		return result;

	}




	public int getIdBroni(String model_broni) {


		return 1;
	}

}
