package arma.arma3app.cellfactory;

import arma.arma3app.AmunicjaOkno;
import arma.itemdao.AmunicjaDao;
import arma.itemdb.Amunicja;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;

public class ActionsCellFactory implements Callback<TableColumn<Amunicja, String>, TableCell<Amunicja, String>> {
	private AmunicjaOkno amunicjaOkno;

	public ActionsCellFactory(AmunicjaOkno amunicjaOkno) {
		this.amunicjaOkno = amunicjaOkno;
	}

	@Override
	public TableCell call(TableColumn<Amunicja, String> param) {
		TableCell<Amunicja, String> cell = new TableCell<Amunicja, String>() {

			// button, element komorki
			Button btnPlus1 = new Button("+1");
			Button btnMinus1 = new Button("-1");
			TextField tfCustomIlosc = new TextField("Wprowadź ilość");
			FlowPane fpOperacje = new FlowPane(btnPlus1, btnMinus1, tfCustomIlosc);

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
						Amunicja amunicja = getTableView().getItems().get(getIndex());
						// funkcja buttona
						amunicja.setIlosc(amunicja.getIlosc() + 1);
							AmunicjaDao.updateAmmo(amunicja);
						amunicjaOkno.updateTable();
					});
					btnMinus1.setOnAction(event -> {
						Amunicja amunicja = getTableView().getItems().get(getIndex());
						// funkcja buttona
						if (amunicja.getIlosc() > 0) {
							amunicja.setIlosc(amunicja.getIlosc() - 1);
							AmunicjaDao.updateAmmo(amunicja);
							amunicjaOkno.updateTable();
						}
					});
					tfCustomIlosc.setOnMousePressed(event -> {
						tfCustomIlosc.clear();
					});

					tfCustomIlosc.setOnKeyPressed(event -> {
						if (event.getCode() == KeyCode.ENTER) {
							Amunicja amunicja = getTableView().getItems().get(getIndex());
							amunicja.setIlosc(Integer.parseInt(tfCustomIlosc.getText()));
							AmunicjaDao.updateAmmo(amunicja);
							amunicjaOkno.updateTable();
						}
					});

					fpOperacje.setHgap(10);
					setGraphic(fpOperacje);
					setText(null);
				}
			}
		};
		return cell;
		}
	}
