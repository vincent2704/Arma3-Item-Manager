package arma.arma3app;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class FirstScreen extends GridPane {

	App app;

	public FirstScreen(App app) {
		this.app = app;

		setVgap(10);
		setHgap(10);

		setAlignment(Pos.CENTER);

		Button btnBronie = new Button("Baza broni");
		add(btnBronie, 0, 0);
		btnBronie.setMaxWidth(150);
		btnBronie.setOnAction(event -> {
			app.moveToBronieOkno();
		});
		
		Button btnAmunicja = new Button("Baza amunicji");
		add(btnAmunicja, 0, 1);
		btnAmunicja.setMaxWidth(150);
		btnAmunicja.setOnAction(event -> {
			app.moveToAmunicjaOkno();
		});

		Button btnDodajBron = new Button("Dodaj broń");
		add(btnDodajBron, 0, 2);
		btnDodajBron.setMaxWidth(150);
		btnDodajBron.setOnAction(event -> {
			app.moveToDodajBron();
			;
		});

	}


}
