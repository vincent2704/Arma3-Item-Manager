package arma.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Konfiguracja implements Serializable {

	// nazwa pliku konfiguracyjnego
	public static final String CONFIG_FILENAME = "app.conf";

	public String strSerwer = "";
	public int intPort = 0;
	public String strBaza = "";
	public String strUser = "";
	public String strHaslo = "";


	public Konfiguracja() {

	}

	public static Alert brakKonfiguracji() {
		Alert alBrakKonfiguracji = new Alert(AlertType.WARNING);
		alBrakKonfiguracji.setTitle("Brak konfiguracji");
		alBrakKonfiguracji.setHeaderText("Uwaga");
		alBrakKonfiguracji
				.setContentText("Program nie był w stanie znaleźć pliku konfiguracyjnego.\nUstaw teraz konfigurację dla"
						+ " odczytu bazy danych.");
		alBrakKonfiguracji.showAndWait();

		return alBrakKonfiguracji;
	}

	public static Konfiguracja readConfig() {
		
		if (!new File(CONFIG_FILENAME).exists()) {
			return null;
		}

		try (FileInputStream fout = new FileInputStream(CONFIG_FILENAME);
				ObjectInputStream obout = new ObjectInputStream(fout)) {

			return (Konfiguracja) obout.readObject();

		} catch (Exception exc) {
			exc.printStackTrace();
			new File(CONFIG_FILENAME).delete();
			System.out.println("ignore");
		}

		return null;
	}

	public static void saveConfig(Konfiguracja konfiguracja) {

		try (FileOutputStream fout = new FileOutputStream(CONFIG_FILENAME);
				ObjectOutputStream obout = new ObjectOutputStream(fout)) {

			obout.writeObject(konfiguracja);

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public String getStrSerwer() {
		return strSerwer;
	}

	public void setStrSerwer(String strSerwer) {
		this.strSerwer = strSerwer;
	}

	public int getIntPort() {
		return intPort;
	}

	public void setIntPort(int intPort) {
		this.intPort = intPort;
	}

	public String getStrBaza() {
		return strBaza;
	}

	public void setStrBaza(String strBaza) {
		this.strBaza = strBaza;
	}

	public String getStrUser() {
		return strUser;
	}

	public void setStrUser(String strUser) {
		this.strUser = strUser;
	}

	public String getStrHaslo() {
		return strHaslo;
	}

	public void setStrHaslo(String strHaslo) {
		this.strHaslo = strHaslo;
	}

	/*
	 * mapa jest wywolywana podczas tworzenia createEntityManagerFactory, w pliku
	 * persistence.xml wtedy znajduje poszczegolne powiazania klucz-wartosc i
	 * zmienia je na takie jakie sa ustawione przez uzytkownika
	 */
	public Map getProperties() {
		Map<String, String> mapa = new HashMap<>();
		mapa.put("javax.persistence.jdbc.url", "jdbc:mysql://" + strSerwer + ":" + intPort + "/" + strBaza);
		mapa.put("javax.persistence.jdbc.user", strUser);
		mapa.put("javax.persistence.jdbc.password", strHaslo);
		return mapa;
	}



}
