package arma.arma3app.cellfactory;

import java.util.Optional;

import arma.arma3app.BronieOkno;
import arma.arma3app.WiecejOpcjiOkno;
import arma.itemdao.BronieDao;
import arma.itemdb.Bronie;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class BronieCellFactory implements Callback<TableColumn<Bronie, String>, TableCell<Bronie, String>> {
	private BronieOkno bronieOkno;

	public BronieCellFactory(BronieOkno bronieOkno) {
		this.bronieOkno = bronieOkno;
	}

	@Override
	public TableCell<Bronie, String> call(TableColumn<Bronie, String> param) {
		TableCell<Bronie, String> cell = new TableCell<Bronie, String>() {

			// button, element komorki
			Button btnPlus1 = new Button("+1");
			Button btnMinus1 = new Button("-1");
			TextField tfCustomIlosc = new TextField("Ilość");
			Button btnWiecej = new Button("Więcej");
			HBox fpOperacje = new HBox(10, btnPlus1, btnMinus1, tfCustomIlosc, btnWiecej);

			// override metody updateItem pozwala na customowy wyglad komorek. tutaj jest to uzyte w celu
			// wprowadzania buttonow jako elementow kazdego wpisu
			@Override
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				setPrefHeight(35);
				// szerokosc komorki, w jakiej dopuszcza sie nowe elementy. jak przekraczaja ilosc double w argumencie to nowy element jest w nowej linijce komorki
				// na razie zacomenntowane, bo bez sensu widac kreske w programie
				// guziki.setPrefWrapLength(100);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					btnPlus1.setOnAction(event -> {
						Bronie bronie = getTableView().getItems().get(getIndex());
						// funkcja buttona
						bronie.setIlosc(bronie.getIlosc() + 1);
						BronieDao.updateBronie(bronie);
						bronieOkno.updateTable();
					});
					btnMinus1.setOnAction(event -> {
						Bronie bronie = getTableView().getItems().get(getIndex());
						// funkcja buttona
						if (bronie.getIlosc() > 0) {
							bronie.setIlosc(bronie.getIlosc() - 1);
							BronieDao.updateBronie(bronie);
							bronieOkno.updateTable();
						}
					});
					tfCustomIlosc.setOnMousePressed(event -> {
						tfCustomIlosc.clear();
					});

					tfCustomIlosc.setMaxWidth(50);

					tfCustomIlosc.setOnKeyPressed(event -> {
						if (event.getCode() == KeyCode.ENTER) {
							Bronie bronie = getTableView().getItems().get(getIndex());
							bronie.setIlosc(Integer.parseInt(tfCustomIlosc.getText()));
							BronieDao.updateBronie(bronie);
							bronieOkno.updateTable();
						}
					});

					btnWiecej.setOnAction(event -> {
						Bronie bronie = getTableView().getItems().get(getIndex());
						Optional<Boolean> changed = new WiecejOpcjiOkno(bronie).showAndWait();
						changed.ifPresent(c -> {
							if (c) {
								bronieOkno.updateTable();
							}
						});
					});
					setGraphic(fpOperacje);
					setText(null);
				}
			}
		};
		return cell;
	}
}
