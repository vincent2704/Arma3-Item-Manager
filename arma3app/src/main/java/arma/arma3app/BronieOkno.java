package arma.arma3app;

import java.util.List;

import arma.arma3app.cellfactory.BronieCellFactory;
import arma.arma3app.cellfactory.DoubleCellFactory;
import arma.itemdao.BronieDao;
import arma.itemdb.Bronie;
import arma.misc.DuplicateEntryException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class BronieOkno extends BorderPane {

	App app;

	private ObservableList<Bronie> listObBronie;
	private List<Bronie> listaBronie;

	private TextField rodzaj;

	private TextField ilosc;

	private TextField kaliber;

	public BronieOkno(App app) {
		this.app = app;
		updateTable();

		Button btnBack = new Button("Powrót");
		btnBack.setOnAction(event -> {
			app.moveToGlowny();
		});

		setBottom(btnBack);
		setCenter(createTabelaBronie());
		setTop(gridPane());
	}

	public void updateTable() {
		listaBronie = BronieDao.getListaBroni();
		if (listObBronie != null) {
			listObBronie.clear();
			listObBronie.addAll(listaBronie);
		}
	}

	public void duplicateBron() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wpis istnieje!");
		alert.setHeaderText("Wpis istnieje w bazie danych.");
		alert.setContentText("Podany rodzaj amunicji znajduje się już w bazie danych.");
		alert.showAndWait();

	}

	public void zlyFormatBron() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Zły format!");
		alert.setHeaderText("Niepoprawna forma zapisu!");
		alert.setContentText(
				"Zły format zapisu!\nUpewnij się, że:\n*w polu ilość podałeś same cyfry\n*w polu kaliber podałeś same cyfry oddzielone kropką!");
		alert.showAndWait();
	}

	private GridPane gridPane() {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setHgap(10);

		Label lbWprow = new Label("Szybkie wprowadzanie: ");
		gp.add(lbWprow, 0, 0);
		lbWprow.setId("lbWprow");

		rodzaj = new TextField("Model broni");
		gp.add(rodzaj, 0, 1);
		rodzaj.setOnMouseClicked(event -> {
			rodzaj.clear();
		});

		ilosc = new TextField("Ilość");
		ilosc.setMaxWidth(80);
		gp.add(ilosc, 1, 1);
		ilosc.setOnMouseClicked(event -> {
			ilosc.clear();
		});

		kaliber = new TextField("Kaliber");
		kaliber.setMaxWidth(80);
		gp.add(kaliber, 2, 1);
		kaliber.setOnMouseClicked(event -> {
			kaliber.clear();
		});

		Button btnDodaj = new Button("Dodaj");
		gp.add(btnDodaj, 3, 1);
		btnDodaj.setOnAction(event -> {
			putInDatabaseBron();
		});

		kaliber.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {


			}
		});

		return gp;
	}

	private void putInDatabaseBron() {
		try {
			BronieDao.registerBron(rodzaj.getText(), Integer.parseInt(ilosc.getText()),
					Double.parseDouble(kaliber.getText()));
		} catch (NumberFormatException e) {
			zlyFormatBron();
		} catch (DuplicateEntryException e) {
			duplicateBron();
		}
		updateTable();
	}

	private TableView<Bronie> createTabelaBronie() {

		TableView<Bronie> tabela = new TableView<Bronie>();
		// tabela.setFixedCellSize(35);

		TableColumn<Bronie, String> colNazwa = new TableColumn<Bronie, String>("Model");
		colNazwa.setPrefWidth(191);
		colNazwa.setMaxWidth(191);
		colNazwa.setMinWidth(191);
		// tworzy wartość do każdej komórki, parametr new PropertyValueFactory, argument to nazwa pola
		colNazwa.setCellValueFactory(new PropertyValueFactory<>("model_broni"));
		TableColumn<Bronie, Integer> colIlosc = new TableColumn<Bronie, Integer>("Ilość");
		colIlosc.setPrefWidth(44);
		colIlosc.setMaxWidth(44);
		colIlosc.setMinWidth(44);
		colIlosc.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
		// modyfikacja zeby na poczatku 0 nie wyswietlala
		TableColumn<Bronie, Double> colKaliber = new TableColumn<Bronie, Double>("Kaliber");
		colKaliber.setPrefWidth(76);
		colKaliber.setMaxWidth(76);
		colKaliber.setMinWidth(76);
		// z klasy doublecellfactory
		colKaliber.setCellFactory(new DoubleCellFactory<Bronie>());
		colKaliber.setCellValueFactory(new PropertyValueFactory<>("kaliber"));

		// kolumna implementujaca guziki do edycji poszczegolnego wiersza w bazie
		TableColumn colOperacje = new TableColumn();
		colOperacje.setPrefWidth(206);
		colOperacje.setMaxWidth(206);
		colOperacje.setMinWidth(206);

		Callback<TableColumn<Bronie, String>, TableCell<Bronie, String>> colGuzikiFactory = new BronieCellFactory(this);
		colOperacje.setCellFactory(colGuzikiFactory);
		colOperacje.setCellValueFactory(new PropertyValueFactory<>("guziki"));

		tabela.getColumns().addAll(colNazwa, colIlosc, colKaliber, colOperacje);

		listObBronie = FXCollections.observableArrayList(listaBronie);
		tabela.setItems(listObBronie);

		return tabela;

	}

}

