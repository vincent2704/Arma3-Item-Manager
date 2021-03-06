package arma.arma3app;

import java.util.List;

import arma.arma3app.cellfactory.AmunicjaCellFactory;
import arma.arma3app.cellfactory.DoubleCellFactory;
import arma.itemdao.AmunicjaDao;
import arma.itemdb.Amunicja;
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

public class AmunicjaOkno extends BorderPane {

	App app;

	private ObservableList<Amunicja> listObAmunicja;
	private List<Amunicja> listaAmunicja;

	private TextField rodzaj;

	private TextField ilosc;

	private TextField kaliber;

	public AmunicjaOkno(App app) {
		this.app = app;
		updateTable();

		Button btnBack = new Button("Powrót");
		btnBack.setOnAction(event -> {
			app.moveToGlowny();
		});

		setBottom(btnBack);
		setCenter(createTabelaAmunicja());
		setTop(gridPane());
	}

	public void updateTable() {
		listaAmunicja = AmunicjaDao.getAmunicja();
		if (listObAmunicja != null) {
			listObAmunicja.clear();
			listObAmunicja.addAll(listaAmunicja);
		}
	}

	public void duplicateAmmo() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wpis istnieje!");
		alert.setHeaderText("Wpis istnieje w bazie danych.");
		alert.setContentText("Podany rodzaj amunicji znajduje się już w bazie danych.");
		alert.showAndWait();

	}

	public void zlyFormatAmmo() {
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



		rodzaj = new TextField("Rodzaj amunicji");
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
			putInDatabaseAmmo();
		});

		kaliber.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				putInDatabaseAmmo();

			}
		});

		return gp;
	}

	private void putInDatabaseAmmo() {
		try {
			AmunicjaDao.registerAmmo(rodzaj.getText(), Integer.parseInt(ilosc.getText()),
					Double.parseDouble(kaliber.getText()));
		} catch (NumberFormatException e) {
			zlyFormatAmmo();
		} catch (DuplicateEntryException e) {
			duplicateAmmo();
		}
		updateTable();
	}

	private TableView<Amunicja> createTabelaAmunicja() {

		TableView<Amunicja> tabela = new TableView<Amunicja>();
		// tabela.setFixedCellSize(35);

		TableColumn<Amunicja, String> colNazwa = new TableColumn<Amunicja, String>("Nazwa");
		// id do zmiany alignmentu zeby nazwa amunicji byla od lewej a nie wysrodkowana w programie
		colNazwa.setId("colNazwa");
		colNazwa.setMaxWidth(210);
		colNazwa.setMinWidth(210);
		// tworzy wartość do każdej komórki, parametr new PropertyValueFactory, argument to nazwa pola
		colNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa_amunicji"));
		TableColumn<Amunicja, Integer> colIlosc = new TableColumn<Amunicja, Integer>("Ilość");
		colIlosc.setPrefWidth(44);
		colIlosc.setMaxWidth(44);
		colIlosc.setMinWidth(44);
		colIlosc.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
		// modyfikacja zeby na poczatku 0 nie wyswietlala
		TableColumn<Amunicja, Double> colKaliber = new TableColumn<Amunicja, Double>("Kaliber");
		colKaliber.setPrefWidth(76);
		colKaliber.setMaxWidth(76);
		colKaliber.setMinWidth(76);
		// z klasy doublecellfactory
		colKaliber.setCellFactory(new DoubleCellFactory<Amunicja>());
		colKaliber.setCellValueFactory(new PropertyValueFactory<>("kaliber"));

		// kolumna implementujaca guziki do edycji poszczegolnego wiersza w bazie
		TableColumn colOperacje = new TableColumn();
		colOperacje.setPrefWidth(206);
		colOperacje.setMaxWidth(206);
		colOperacje.setMinWidth(206);

		Callback<TableColumn<Amunicja, String>, TableCell<Amunicja, String>> colGuzikiFactory =
				new AmunicjaCellFactory(this);
		colOperacje.setCellFactory(colGuzikiFactory);
		colOperacje.setCellValueFactory(new PropertyValueFactory<>("guziki"));


		tabela.getColumns().addAll(colNazwa, colIlosc, colKaliber, colOperacje);

		listObAmunicja = FXCollections.observableArrayList(listaAmunicja);
		tabela.setItems(listObAmunicja);

		return tabela;

	}


	}
