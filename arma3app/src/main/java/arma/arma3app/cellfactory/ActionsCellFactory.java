package arma.arma3app.cellfactory;

import arma.arma3app.AmunicjaOkno;
import arma.itemdb.Amunicja;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
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

			// override metody updateItem pozwala na customowy wyglad komorek. tutaj jest to uzyte w celu
			// wprowadzania buttonow jako elementow kazdego wpisu
			@Override
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					btnPlus1.setOnAction(event -> {
						Amunicja amunicja = getTableView().getItems().get(getIndex());
						// funkcja buttona
						amunicja.setIlosc(amunicja.getIlosc() + 1);
						amunicjaOkno.updateTable();
					});
					FlowPane guziki = new FlowPane(btnPlus1, btnMinus1);
					guziki.setHgap(10);
					setGraphic(guziki);
					setText(null);
				}
		}
		};
		return cell;
	}
}
