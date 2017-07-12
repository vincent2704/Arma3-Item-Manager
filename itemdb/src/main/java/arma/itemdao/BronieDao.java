package arma.itemdao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import arma.itemdb.Bronie;
import arma.itemdb.DatabaseHandler;
import arma.misc.DuplicateEntryException;

public class BronieDao implements Serializable {

	private static int iloscBroni;

	// dodanie broni do bazy danych. ukonczone!
	public static void registerBron(String model, int ilosc, double kaliber) throws DuplicateEntryException {
		EntityManager em = DatabaseHandler.getEntityManager();

		try {
		em.getTransaction().begin();
		Bronie bron = new Bronie();
		bron.setModel_broni(model);
		bron.setIlosc(ilosc);
		bron.setKaliber(kaliber);
		em.persist(bron);
		em.getTransaction().commit();
		} catch (RollbackException exc) {
			for (Throwable t = exc.getCause(); t != null; t = t.getCause()) {
				if (t instanceof MySQLIntegrityConstraintViolationException) {
					throw new DuplicateEntryException(t);
				}
			}
		}
	}




	// zwraca liste wszystkich obiektow typu Bronie
	public static List<Bronie> getListaBroni() {

		EntityManager em = DatabaseHandler.getEntityManager();
		String qString = "SELECT b FROM Bronie b";

		TypedQuery<Bronie> q = em.createQuery(qString, Bronie.class);

		List<Bronie> result = q.getResultList();

		return result;

	}


	public static void updateBronie(Bronie bronie) {
		EntityManager em = DatabaseHandler.getEntityManager();

		Bronie a = em.find(Bronie.class, bronie.getId());

		em.getTransaction().begin();
		a.setIlosc(bronie.getIlosc());
		a.setKaliber(bronie.getKaliber());
		a.setModel_broni(bronie.getModel_broni());
		em.getTransaction().commit();
		
	}

	public static void deleteBronie(Bronie bronie) {
		EntityManager em = DatabaseHandler.getEntityManager();

		em.getTransaction().begin();
		em.remove(bronie);
		em.getTransaction().commit();
	}

}
