package arma.itemdb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DatabaseHandler 
{

	private static EntityManager em;
	private static EntityManagerFactory emf;


	public static void createConnection() {
		try {

			emf = Persistence.createEntityManagerFactory("arma3");
		// przejmuje ustawienia z persistence.xml
			em = emf.createEntityManager();

		} catch (Exception exc) {
			alertConnection();
		}
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

}
