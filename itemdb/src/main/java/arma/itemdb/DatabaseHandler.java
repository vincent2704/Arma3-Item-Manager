package arma.itemdb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import arma.misc.Konfiguracja;

public class DatabaseHandler 
{

	private static EntityManager em;
	private static EntityManagerFactory emf;


	public static boolean createConnection(Konfiguracja conf) {
		closeConnection();
		try {
			emf = Persistence.createEntityManagerFactory("arma3", conf.getProperties());
		// przejmuje ustawienia z persistence.xml
			em = emf.createEntityManager();
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}

	}


	public static EntityManager getEntityManager() {
		return em;
	}

	public static void closeConnection() {
		if (em != null) {
			em.close();
			// po zamknieciu robi null z em zeby potem nie bylo bledu ze juz jest zamkniety
			// a istnieje
			em = null;
		}
		if (emf != null) {
			emf.close();
			em = null;
		}
	}


}
