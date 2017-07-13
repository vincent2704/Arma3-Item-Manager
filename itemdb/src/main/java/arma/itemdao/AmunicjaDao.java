package arma.itemdao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import arma.itemdb.Amunicja;
import arma.itemdb.DatabaseHandler;
import arma.misc.DuplicateEntryException;

public class AmunicjaDao {

	// pobieranie listy obiektów
	public static List<Amunicja> getAmunicja() {

		EntityManager em = DatabaseHandler.getEntityManager();

		String qString = "SELECT a FROM Amunicja a";

		TypedQuery<Amunicja> q = em.createQuery(qString, Amunicja.class);

		List<Amunicja> lista = q.getResultList();

		return lista;

	}

	public static List<String> getNazwaAmmo() {
		EntityManager em = DatabaseHandler.getEntityManager();

		String qString = "SELECT a.nazwa_amunicji FROM Amunicja a";

		TypedQuery<String> q = em.createQuery(qString, String.class);

		List<String> nazwaAmmo = q.getResultList();

		return nazwaAmmo;
	}

	// do uzupelniania comboboxa po pasujacym kalibrze w opcjach "wiecej" w
	// BronieOkno
	public static List<Amunicja> getAmunicjaPoKalibrzeAmmo(double kaliber) {
		EntityManager em = DatabaseHandler.getEntityManager();

		String qString = "SELECT a FROM Amunicja a WHERE kaliber = :kaliber";

		TypedQuery<Amunicja> q = em.createQuery(qString, Amunicja.class);
		q.setParameter("kaliber", kaliber);

		List<Amunicja> listaPoKalibrzeAmmo = q.getResultList();

		return listaPoKalibrzeAmmo;
	}

	public static Amunicja getAmunicjaPoNazwie(String nazwa) {
		EntityManager em = DatabaseHandler.getEntityManager();

		String qString = "SELECT a.nazwa_amunicji FROM Amunicja a WHERE nazwa_amunicji = :nazwa";
		TypedQuery<Amunicja> q = em.createQuery(qString, Amunicja.class);
		q.setParameter("nazwa_amunicji", nazwa);

		Amunicja ammo = q.getSingleResult();

		return ammo;
	}

	public static void registerAmmo(String nazwa, int ilosc, double kaliber) throws DuplicateEntryException {
		EntityManager em = DatabaseHandler.getEntityManager();

		try {
		em.getTransaction().begin();
		Amunicja ammo = new Amunicja();
		ammo.setNazwa_amunicji(nazwa);
		ammo.setIlosc(ilosc);
		ammo.setKaliber(kaliber);
		em.persist(ammo);
		em.getTransaction().commit();
			// lapie rollbackexception (bazowa klasa mysqlintegrity)
		} catch (RollbackException exc) {
			// w petli wyszukuje w gore mysqlintegrity
			for (Throwable t = exc.getCause(); t != null; t = t.getCause()) {
				// jezeli exc jest instancja mysqlintegrity (jezeli znalazlo ten wyjatek w stack
				// trace)
				if (t instanceof MySQLIntegrityConstraintViolationException) {
					// wyrzuca wyjatek duplicateentry
					throw new DuplicateEntryException(t);
				}
			}
		}

	}

	public static void updateAmmo(Amunicja ammo) {
		EntityManager em = DatabaseHandler.getEntityManager();

		em.getTransaction().begin();
		em.merge(ammo);
		em.flush();
		em.clear();
		em.getTransaction().commit();

	}

	public static void deleteAmunicja(Amunicja ammo) {
		EntityManager em = DatabaseHandler.getEntityManager();

		em.getTransaction().begin();
		em.remove(ammo);
		em.getTransaction().commit();
	}

}
