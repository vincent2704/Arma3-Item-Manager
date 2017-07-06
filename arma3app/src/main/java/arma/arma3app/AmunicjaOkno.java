package arma.arma3app;

import java.util.List;

import arma.itemdao.AmunicjaDao;
import arma.itemdb.Amunicja;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AmunicjaOkno extends BorderPane {

	App app;

	private ObservableList<Amunicja> listObAmunicja;
	private List<Amunicja> listaAmunicja;

	public AmunicjaOkno(App app) {
		this.app = app;
		listaAmunicja = AmunicjaDao.getAmunicja();
		setCenter(createTabelaAmunicja());

		setBottom(getVBox());
	}

	private TableView<Amunicja> createTabelaAmunicja() {

		TableView<Amunicja> tabela = new TableView<Amunicja>();

		TableColumn<Amunicja, String> colNazwa = new TableColumn<Amunicja, String>("Nazwa");
		// tworzy wartość do każdej komórki, parametr new PropertyValueFactory, argument to nazwa pola
		colNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa_amunicji"));
		TableColumn<Amunicja, Integer> colIlosc = new TableColumn<Amunicja, Integer>("Ilość");
		colIlosc.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
		TableColumn<Amunicja, Double> colKaliber = new TableColumn<Amunicja, Double>("Kaliber");
		colKaliber.setCellValueFactory(new PropertyValueFactory<>("kaliber"));

		tabela.getColumns().addAll(colNazwa, colIlosc, colKaliber);

		listObAmunicja = FXCollections.observableArrayList(listaAmunicja);
		tabela.setItems(listObAmunicja);

		return tabela;

	}

	private VBox getVBox() {
		VBox hbox = new VBox(10);

		Button btnBack = new Button("Powrót");
		btnBack.setOnAction(event -> {
			app.moveToGlowny();
		});

		hbox.getChildren().add(btnBack);

		TextField rodzaj = new TextField("Wpisz rodzaj amunicji");
		hbox.getChildren().add(rodzaj);

		return hbox;
	}

}
