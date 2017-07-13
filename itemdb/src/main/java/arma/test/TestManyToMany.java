package arma.test;

import javax.persistence.EntityManager;

import org.junit.Test;

import arma.itemdb.Amunicja;
import arma.itemdb.Bronie;
import arma.itemdb.DatabaseHandler;
import junit.framework.TestCase;

public class TestManyToMany extends TestCase {

	@Test
	public static void testBronieAndAmmoAssociation(Bronie bron, Amunicja ammo) {

		EntityManager em = DatabaseHandler.getEntityManager();
		em.getTransaction().begin();
		bron = new Bronie();
		bron.setId(1);
		bron.setIlosc(2);
		bron.setKaliber(12.7);
		bron.setModel_broni("GM6 Lynx");
		em.persist(bron);
		em.flush();
		em.clear();
		em.getTransaction().commit();

		em.getTransaction().begin();
		ammo = new Amunicja();
		ammo.setId(1);
		ammo.setIlosc(2);
		ammo.setKaliber(12.7);
		ammo.setNazwa_amunicji("12.7mm 10Rnd Lynx");
		em.getTransaction().commit();

	}

}
