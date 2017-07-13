package arma.itemdao;

import javax.persistence.EntityManager;

import arma.itemdb.Amunicja;
import arma.itemdb.Bronie;
import arma.itemdb.DatabaseHandler;

public class ManyToMany {

	public static void assocBronAmunicja(Bronie bron, Amunicja ammo) {
		EntityManager em = DatabaseHandler.getEntityManager();
		// bron.setAmunicja(ammo);

	}

}
