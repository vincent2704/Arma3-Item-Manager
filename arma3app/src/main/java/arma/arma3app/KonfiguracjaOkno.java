package arma.arma3app;

import arma.itemdb.DatabaseHandler;
import arma.misc.Konfiguracja;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class KonfiguracjaOkno extends BorderPane {

	App app;
	Konfiguracja konf;;


	TextField tfSerwer, tfPort, tfBaza, tfUser;
	PasswordField pfHaslo;

	public KonfiguracjaOkno(App app) {
		this.app = app;
		setCenter(vbox());
		setBottom(hbox());

		Label baza = new Label("Obecnie jestes polaczony z bazą danych: ");

	}

	private VBox vbox() {


		konf = Konfiguracja.readConfig();

		VBox vbox = new VBox(10);

		setPadding(new Insets(25, 25, 25, 25));

		Label lbSerwer = new Label("Adres serwera: ");
		vbox.getChildren().add(lbSerwer);
		tfSerwer = new TextField(konf != null ? konf.getStrSerwer() : "");
		vbox.getChildren().add(tfSerwer);
		tfSerwer.setMaxWidth(250);

		Label lbPort = new Label("Numer portu: ");
		vbox.getChildren().add(lbPort);
		tfPort = new TextField(konf != null ? konf.getIntPort() + "" : "");
		vbox.getChildren().add(tfPort);
		tfPort.setMaxWidth(250);

		Label lbBaza = new Label("Nazwa bazy danych: ");
		vbox.getChildren().add(lbBaza);
		tfBaza = new TextField(konf != null ? konf.getStrBaza() : "");
		vbox.getChildren().add(tfBaza);
		tfBaza.setMaxWidth(250);

		Label lbUser = new Label("Nazwa użytkownika: ");
		vbox.getChildren().add(lbUser);
		tfUser = new TextField(konf != null ? konf.getStrUser() : "");
		vbox.getChildren().add(tfUser);
		tfUser.setMaxWidth(250);

		Label lbHaslo = new Label("Hasło użytkownika: ");
		vbox.getChildren().add(lbHaslo);
		pfHaslo = new PasswordField();
		vbox.getChildren().add(pfHaslo);
		pfHaslo.setMaxWidth(250);

		return vbox;
	}
	private HBox hbox() {
		HBox hbox = new HBox(25);

		Button btnBack = new Button("Powrót");
		hbox.getChildren().add(btnBack);
		btnBack.setOnAction(event -> {
			app.moveToGlowny();
		});

		Button btnApply = new Button("Zatwierdź");
		hbox.getChildren().add(btnApply);
		btnApply.setOnAction(event -> {

			String dbSerwer = tfSerwer.getText();
			int dbPort = Integer.parseInt(tfPort.getText());
			String dbBaza = tfBaza.getText();
			String dbUser = tfUser.getText();
			String dbHaslo = pfHaslo.getText();

			Konfiguracja c = new Konfiguracja();
			c.setStrSerwer(dbSerwer);
			c.setIntPort(dbPort);
			c.setStrBaza(dbBaza);
			c.setStrUser(dbUser);
			c.setStrHaslo(dbHaslo);

			Konfiguracja.saveConfig(c);
			DatabaseHandler.createConnection(c);

		});

		return hbox;
	}

}

