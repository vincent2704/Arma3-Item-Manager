package arma.itemdb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import arma.misc.Konfiguracja;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DatabaseHandler 
{

	private static EntityManager em;
	private static EntityManagerFactory emf;


	public static void createConnection(Konfiguracja conf) {
		closeConnection();
		try {
			emf = Persistence.createEntityManagerFactory("arma3", conf.getProperties());
		// przejmuje ustawienia z persistence.xml
			em = emf.createEntityManager();

		} catch (Exception exc) {
			alertConnection();
			return;
		}
		connSuccess();
	}


	public static EntityManager getEntityManager() {
		return em;
	}

	public static void closeConnection() {
		if (em != null) {
			em.close();
		}
		if (emf != null) {
			emf.close();
		}
	}

	public static Alert alertConnection() {
		Alert alertCon = new Alert(AlertType.ERROR);
		alertCon.setTitle("Błąd połączenia!");
		alertCon.setHeaderText("Nie udało się połączyć z bazą danych.");
		alertCon.setContentText("Sprawdź swoje połączenie z bazą danych.");

		alertCon.showAndWait();

		return alertCon;

	}

	public static void connSuccess() {
		Alert alertCon = new Alert(AlertType.INFORMATION);
		alertCon.setTitle("Połączenie udane");
		alertCon.setHeaderText("Nastąpiło połączenie z bazą danych.");
		alertCon.setContentText("Program będzie działał w oparciu o bazę danych o podanych parametrach.");
		alertCon.setX(0);
		alertCon.showAndWait();
	}

}
