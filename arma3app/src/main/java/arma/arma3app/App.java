package arma.arma3app;

import java.io.File;
import java.util.Locale;

import arma.itemdb.DatabaseHandler;
import arma.misc.Konfiguracja;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{

	App main;
	Stage stage;
	FirstScreen firstScreen;
	BronieOkno bronieOkno;
	AmunicjaOkno amunicjaOkno;
	KonfiguracjaOkno konfiguracjaOkno;
	Scene scFirstScreen, scBronieOkno, scAmunicjaOkno, scKonfiguracjaOkno;
	private static final int width = 525, height = 600;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setTitle("Arma 3 Item Manager");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
		stage.setResizable(false);

		primaryStage.setOnCloseRequest(event -> {
			DatabaseHandler.closeConnection();
		});

		firstScreen = new FirstScreen(this);
		scFirstScreen = new Scene(firstScreen, width, height);
		scFirstScreen.getStylesheets().add(getClass().getResource("/firstScreenCSS.css").toExternalForm());
		moveToGlowny();
		primaryStage.setWidth(width);
		primaryStage.setHeight(height);
		primaryStage.show();

		Konfiguracja k = Konfiguracja.readConfig();

		if (k == null) {
			brakKonfiguracji();
			moveToKonfiguracjaOkno();
		}

		else {
			if (DatabaseHandler.createConnection(k)) {
				connSuccess();
			} else {
				alertConnection();
			}

		}
	}

    public void moveToBronieOkno() {
		if (scBronieOkno == null) {
		bronieOkno = new BronieOkno(this);
			scBronieOkno = new Scene(bronieOkno, width, height);
		scBronieOkno.getStylesheets().add(getClass().getResource("/bronieOknoCSS.css").toExternalForm());
		}
		stage.setScene(scBronieOkno);
		bronieOkno.updateTable();
	}

	public void moveToAmunicjaOkno() {
		if (scAmunicjaOkno == null) {
		amunicjaOkno = new AmunicjaOkno(this);
			scAmunicjaOkno = new Scene(amunicjaOkno, width, height);
		scAmunicjaOkno.getStylesheets().add(getClass().getResource("/amunicjaOknoCSS.css").toExternalForm());
		}
		stage.setScene(scAmunicjaOkno);
	}

	public void moveToKonfiguracjaOkno() {
		if (scKonfiguracjaOkno == null) {
		konfiguracjaOkno = new KonfiguracjaOkno(this);
			scKonfiguracjaOkno = new Scene(konfiguracjaOkno, width, height);
		scKonfiguracjaOkno.getStylesheets().add(getClass().getResource("/konfiguracjaOknoCSS.css").toExternalForm());
		}
		stage.setScene(scKonfiguracjaOkno);
	}

	public void moveToGlowny() {
		stage.setScene(scFirstScreen);
	}

	public static void alertConnection() {
		Alert alertCon = new Alert(AlertType.ERROR);
		alertCon.setTitle("Błąd połączenia!");
		alertCon.setHeaderText("Nie udało się połączyć z bazą danych.");
		alertCon.setContentText("Sprawdź swoje połączenie z bazą danych.");
		alertCon.showAndWait();
	}

	public static void connSuccess() {
		Alert alertCon = new Alert(AlertType.INFORMATION);
		alertCon.setTitle("Połączenie udane");
		alertCon.setHeaderText("Nastąpiło połączenie z bazą danych.");
		alertCon.setContentText("Program będzie działał w oparciu o bazę danych o podanych parametrach.");
		alertCon.showAndWait();
	}

	public static void brakKonfiguracji() {
		Alert alBrakKonfiguracji = new Alert(AlertType.WARNING);
		alBrakKonfiguracji.setTitle("Brak konfiguracji");
		alBrakKonfiguracji.setHeaderText("Uwaga");
		alBrakKonfiguracji
				.setContentText(Lang.t("alert.conf.msg"));
		alBrakKonfiguracji.showAndWait();
	}

	public static void main( String[] args )
	{
		Lang.init(new Locale("pl", "PL"));
		launch(args);

		File config = null;
		try {
			config = new File("app.conf");
			config.createNewFile();
		} catch (Exception exc) {
			exc.printStackTrace();
			System.out.println("Coś poszło nie tak z tworzeniem pliku!");
		}
	
	}

}
