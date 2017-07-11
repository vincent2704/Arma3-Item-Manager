package arma.arma3app;

import arma.itemdao.BronieDao;
import arma.misc.DuplicateEntryException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DodajBronOkno extends GridPane {
	
	App app;
	private Alert diaZlyFormat = new Alert(AlertType.ERROR);
	private Alert diaZarejestrowano = new Alert(AlertType.INFORMATION);
	private Alert diaBronIstnieje = new Alert(AlertType.WARNING);
	private TextField tfModel;
	private TextField tfIlosc;
	private TextField tfKaliber;

	private Alert zlyFormatBron() {
		diaZlyFormat.setTitle("Zły format!");
		diaZlyFormat.setHeaderText("Niepoprawna forma zapisu!");
		diaZlyFormat.setContentText(
				"Zły format zapisu!\nUpewnij się, że:\n*w polu ilość podałeś same cyfry\n*w polu kaliber podałeś same cyfry oddzielone kropką!");
		diaZlyFormat.showAndWait();
		return diaZlyFormat;
	}

	private Alert zarejestrowano() {
		diaZarejestrowano.setTitle("Zarejestrowano");
		diaZarejestrowano.setHeaderText("Pomyślnie zarejestrowano broń!");
		diaZarejestrowano.setContentText("Broń została dodana do bazy danych.");
		diaZarejestrowano.showAndWait();

		return diaZarejestrowano;
	}

	private Alert bronIstnieje() {
		diaBronIstnieje.setTitle("Broń już istnieje!");
		diaBronIstnieje.setHeaderText("Broń jest już zapisana w bazie danych");
		diaBronIstnieje.setContentText("Broń, którą chcesz zarejestrować, już istnieje w bazie danych!");
		diaBronIstnieje.showAndWait();

		return diaBronIstnieje;
	}

	private void clearTextFields() {
		tfModel.clear();
		tfIlosc.clear();
		tfKaliber.clear();
	}

	public DodajBronOkno(App app) {

		this.app = app;

		setPadding(new Insets(25, 25, 25, 25));
		setVgap(10);
		setHgap(5);
		setAlignment(Pos.CENTER);

	Label lbModel = new Label("Podaj DOKŁADNĄ nazwę broni: ");
	Label lbIlosc = new Label("Podaj ilość sztuk: ");
	Label lbKaliber = new Label("Podaj kaliber pocisku: ");

		tfModel = new TextField();
		tfIlosc = new TextField();
		tfKaliber = new TextField();
	

		Button btnBack = new Button("Powrót");
		Button btnRejestruj = new Button("Zarejestruj broń");

		add(lbModel, 0, 0);
		add(lbIlosc, 0, 1);
		add(lbKaliber, 0, 2);
	
		add(tfModel, 1, 0);
		add(tfIlosc, 1, 1);
		add(tfKaliber, 1, 2);

		add(btnBack, 0, 3);
		btnBack.setOnAction(event -> {
			app.moveToBronieOkno();
		});

		add(btnRejestruj, 1, 3);
		btnRejestruj.setOnAction(event -> {
			if (tfModel.getText() != null) {

				try {
			BronieDao.registerBron(tfModel.getText(), Integer.parseInt(tfIlosc.getText()),
						Double.parseDouble(tfKaliber.getText()));
				} catch (NumberFormatException exc) {
					zlyFormatBron();
					return;
				} catch (DuplicateEntryException exc) {
					bronIstnieje();
					return;
				}

				zarejestrowano();
				clearTextFields();

			}

			else {
				System.out.println("Nieokreslony_blad_rejestracji\nPrawdopodobnie blad w danych wejsciowych.");
			}
		});
	
	}

}
