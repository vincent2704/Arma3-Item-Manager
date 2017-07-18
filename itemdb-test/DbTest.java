package arma.itemdb;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;

import arma.itemdao.AmunicjaDao;
import arma.itemdao.BronieDao;
import arma.misc.Konfiguracja;

public class DbTest {

	@Test
	public void testDbCreation() {
		try {
			connect();
			disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAmmoCreation() {
		try {
			connect();
			AmunicjaDao.registerAmmo("Ak", 7, 1.5);
			disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAmmoGet() {
		try {
			connect();
			AmunicjaDao.registerAmmo("Ak", 7, 1.5);
			List<Amunicja> list = AmunicjaDao.getAmunicja();
			assertTrue(list != null);
			assertTrue(list.size() > 0);
			disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGunCreation() {
		try {
			connect();
			BronieDao.registerBron("Ak", 7, 1.5);
			disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGunGet() {
		try {
			connect();
			BronieDao.registerBron("Ak", 7, 1.5);
			List<Bronie> list = BronieDao.getListaBroni();
			assertTrue(list != null);
			assertTrue(list.size() > 0);
			disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGunAmmoAss() {
		try {
			connect();
			BronieDao.registerBron("Ak", 7, 1.5);
			AmunicjaDao.registerAmmo("asdf", 8, 0.56);
			List<Bronie> bronie = BronieDao.getListaBroni();
			List<Amunicja> ammo = AmunicjaDao.getAmunicja();
			Bronie b = bronie.get(0);
			b.setAmunicja(new HashSet<>());
			b.getAmunicja().add(ammo.get(0));
			BronieDao.updateBronie(b);

			disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGunAmmoAssGet() {
		try {
			connect();
			EntityManager em = DatabaseHandler.getEntityManager();
			BronieDao.registerBron("Ak", 7, 1.5);
			AmunicjaDao.registerAmmo("asdf", 8, 0.56);
			List<Bronie> bronie = BronieDao.getListaBroni();
			List<Amunicja> ammo = AmunicjaDao.getAmunicja();
			Bronie b = bronie.get(0);
			b.setAmunicja(new HashSet<>());
			Amunicja a = ammo.get(0);
			b.getAmunicja().add(a);
			BronieDao.updateBronie(b);

			bronie = BronieDao.getListaBroni();
			b = bronie.get(0);
			assertTrue(b.getAmunicja() != null);
			assertFalse(b.getAmunicja().isEmpty());

			ammo = AmunicjaDao.getAmunicja();
			a = ammo.get(0);
			assertTrue(a.getBronie() != null);
			assertFalse(a.getBronie().isEmpty());

			disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	private void connect() {
		Konfiguracja c = new Konfiguracja();
		c.setIntPort(3306);
		c.setStrBaza("tester");
		c.setStrSerwer("localhost");
		c.setStrUser("root");
		DatabaseHandler.createConnection(c);
	}

	private void disconnect() {
		DatabaseHandler.closeConnection();
	}
		}
