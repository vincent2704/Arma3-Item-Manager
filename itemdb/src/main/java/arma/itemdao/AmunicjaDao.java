package arma.itemdao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import arma.itemdb.Amunicja;
import arma.itemdb.DatabaseHandler;

public class AmunicjaDao {

	// pobieranie listy obiektów
	public static List<Amunicja> getAmunicja() {

		EntityManager em = DatabaseHandler.getEntityManager();

		String qString = "SELECT a FROM Amunicja a";

		TypedQuery<Amunicja> q = em.createQuery(qString, Amunicja.class);

		List<Amunicja> lista = q.getResultList();

		return lista;

	}


}
