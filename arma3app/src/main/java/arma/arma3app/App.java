package arma.arma3app;

import arma.itemdb.DatabaseHandler;
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
	Scene scFirstScreen, scBronieOkno, scDodajBron, scAmunicjaOkno;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setTitle("Arma 3 Item Manager");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("sniper_rifle-512.png")));

		DatabaseHandler.createConnection();

		firstScreen = new FirstScreen(this);
		scFirstScreen = new Scene(firstScreen, 300, 300);
		primaryStage.setScene(scFirstScreen);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> {
			DatabaseHandler.closeConnection();
		});

	}

    public static void main( String[] args )
    {
		launch(args);
    }


	public void moveToBronieOkno() {
		bronieOkno = new BronieOkno(this);
		scBronieOkno = new Scene(bronieOkno, 350, 600);
		stage.setScene(scBronieOkno);
	}

	public void moveToDodajBron() {
		dodajBronOkno = new DodajBronOkno(this);
		scDodajBron = new Scene(dodajBronOkno, 380, 300);
		stage.setScene(scDodajBron);

	}

	public void moveToAmunicjaOkno() {
		amunicjaOkno = new AmunicjaOkno(this);
		scAmunicjaOkno = new Scene(amunicjaOkno, 350, 600);
		stage.setScene(scAmunicjaOkno);
	}

	public void moveToGlowny() {
		stage.setScene(scFirstScreen);
	}

}
