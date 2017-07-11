package arma.arma3app;

import java.io.File;

import arma.itemdb.DatabaseHandler;
import arma.misc.Konfiguracja;
import javafx.application.Application;
import javafx.scene.Scene;
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
	DodajBronOkno dodajBronOkno;
	AmunicjaOkno amunicjaOkno;
	KonfiguracjaOkno konfiguracjaOkno;
	Scene scFirstScreen, scBronieOkno, scDodajBron, scAmunicjaOkno, scKonfiguracjaOkno;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setTitle("Arma 3 Item Manager");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("sniper_rifle-512.png")));



		firstScreen = new FirstScreen(this);
		scFirstScreen = new Scene(firstScreen, 300, 300);
		scFirstScreen.getStylesheets().add(getClass().getResource("firstScreenCSS.css").toExternalForm());
		primaryStage.setScene(scFirstScreen);
		primaryStage.show();

		primaryStage.setOnCloseRequest(event -> {
			DatabaseHandler.closeConnection();
		});

		Konfiguracja k = Konfiguracja.readConfig();

		if (k == null) {
			Konfiguracja.brakKonfiguracji();
			moveToKonfiguracjaOkno();
		}
		else {

			DatabaseHandler.createConnection(k);
		}
	}

    public void moveToBronieOkno() {
		bronieOkno = new BronieOkno(this);
		scBronieOkno = new Scene(bronieOkno, 350, 600);
		scBronieOkno.getStylesheets().add(getClass().getResource("bronieOknoCSS.css").toExternalForm());
		stage.setScene(scBronieOkno);
	}

	public void moveToDodajBron() {
		dodajBronOkno = new DodajBronOkno(this);
		scDodajBron = new Scene(dodajBronOkno, 380, 300);
		stage.setScene(scDodajBron);

	}

	public void moveToAmunicjaOkno() {
		amunicjaOkno = new AmunicjaOkno(this);
		scAmunicjaOkno = new Scene(amunicjaOkno, 525, 600);
		scAmunicjaOkno.getStylesheets().add(getClass().getResource("amunicjaOknoCSS.css").toExternalForm());
		stage.setScene(scAmunicjaOkno);
	}

	public void moveToKonfiguracjaOkno() {
		konfiguracjaOkno = new KonfiguracjaOkno(this);
		scKonfiguracjaOkno = new Scene(konfiguracjaOkno, 300, 400);
		scKonfiguracjaOkno.getStylesheets().add(getClass().getResource("konfiguracjaOknoCSS.css").toExternalForm());
		stage.setScene(scKonfiguracjaOkno);
	}

	public void moveToGlowny() {
		stage.setScene(scFirstScreen);
	}

	public static void main( String[] args )
	{
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
