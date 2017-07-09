package arma.arma3app;

import java.util.List;

import arma.itemdao.AmunicjaDao;
import arma.itemdb.Amunicja;
import arma.misc.DuplicateEntryException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class AmunicjaOkno extends BorderPane {

	App app;

	private ObservableList<Amunicja> listObAmunicja;
	private List<Amunicja> listaAmunicja;

	public AmunicjaOkno(App app) {
		this.app = app;
		updateTable();
		setCenter(createTabelaAmunicja());

		setBottom(getHBox());
	}

	private void updateTable() {
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

	private TableView<Amunicja> createTabelaAmunicja() {

		TableView<Amunicja> tabela = new TableView<Amunicja>();

		TableColumn<Amunicja, String> colNazwa = new TableColumn<Amunicja, String>("Nazwa");
		// tworzy wartość do każdej komórki, parametr new PropertyValueFactory, argument to nazwa pola
		colNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa_amunicji"));
		TableColumn<Amunicja, Integer> colIlosc = new TableColumn<Amunicja, Integer>("Ilość");
		colIlosc.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
		// modyfikacja zeby na poczatku 0 nie wyswietlala
		TableColumn<Amunicja, Double> colKaliber = new TableColumn<Amunicja, Double>("Kaliber");
		// colKaliber.setCellFactory(new DoubleCellFactory<Amunicja>());
		colKaliber.setCellValueFactory(new PropertyValueFactory<>("kaliber"));

		// kolumna implementujaca guziki do edycji poszczegolnego wiersza w bazie
		TableColumn editButton = new TableColumn("Actions");
		editButton.setCellValueFactory(new PropertyValueFactory<>("buttons"));
		Callback<TableColumn<Amunicja, String>, TableCell<Amunicja, String>> cellFactory = //
				new Callback<TableColumn<Amunicja, String>, TableCell<Amunicja, String>>() {
					@Override
					public TableCell call(final TableColumn<Amunicja, String> param) {
						final TableCell<Amunicja, String> cell = new TableCell<Amunicja, String>() {

							final Button btn = new Button("Edit");
	
							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btn.setOnAction(event -> {
										Amunicja amunicja = getTableView().getItems().get(getIndex());
										System.out.println(amunicja.getId());
									});
									setGraphic(btn);
									setText(null);
								}
								}
						};
						return cell;
					}
				};
		editButton.setCellFactory(cellFactory);

		tabela.getColumns().addAll(colNazwa, colIlosc, colKaliber, editButton);

		listObAmunicja = FXCollections.observableArrayList(listaAmunicja);
		tabela.setItems(listObAmunicja);

		return tabela;

	}

	private HBox getHBox() {
		HBox hbox = new HBox(10);

		Button btnBack = new Button("Powrót");
		btnBack.setOnAction(event -> {
			app.moveToGlowny();
		});

		hbox.getChildren().add(btnBack);

		TextField rodzaj = new TextField("Rodzaj amunicji");
		hbox.getChildren().add(rodzaj);
		rodzaj.setOnMouseClicked(event -> {
			rodzaj.clear();
		});

		TextField ilosc = new TextField("Ilość magazynków");
		hbox.getChildren().add(ilosc);
		ilosc.setOnMouseClicked(event -> {
			ilosc.clear();
		});

		TextField kaliber = new TextField("Kaliber pocisku");

		hbox.getChildren().add(kaliber);
		kaliber.setOnMouseClicked(event -> {
			kaliber.clear();
		});

		kaliber.setOnKeyPressed(event -> {

			if (event.getCode() == KeyCode.ENTER) {
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
		});

		return hbox;
	}

	}
