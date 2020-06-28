/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.GiocatoreBattuto;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="btnCreaGrafo"
	private Button btnCreaGrafo; // Value injected by FXMLLoader

	@FXML // fx:id="btnTopPlayer"
	private Button btnTopPlayer; // Value injected by FXMLLoader

	@FXML // fx:id="btnDreamTeam"
	private Button btnDreamTeam; // Value injected by FXMLLoader

	@FXML // fx:id="txtK"
	private TextField txtK; // Value injected by FXMLLoader

	@FXML // fx:id="txtGoals"
	private TextField txtGoals; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCreaGrafo(ActionEvent event) {

		txtResult.clear();

		String kStr = txtGoals.getText();
		Double media;
		try {
			media = Double.parseDouble(kStr);
		} catch (NumberFormatException e) {
			txtResult.appendText("Inserire un valore numerico x");
			return;
		}

		this.model.creaGrafo(media);
		if (this.model.getVertici() <= 0) {
			txtResult.appendText("Non è stato possibile creare il grafo. Prova con k<0.9");
			return;
		}
		txtResult.appendText("Grafo creato!\n#Vertici " + this.model.getVertici() + "\n#Arhi " + this.model.getArchi());

		// ora posso fare il top player
		btnTopPlayer.setDisable(false);
		btnDreamTeam.setDisable(false);

	}

	@FXML
	void doDreamTeam(ActionEvent event) {
		txtResult.setText("DREAM TEAM:");

		String kString = txtK.getText();
		int k;
		try {
			k = Integer.parseInt(kString);
		} catch (NumberFormatException e) {
			txtResult.appendText("Inserire un valore numerico k");
			return;
		}
		txtResult.appendText(" (k = " + k + "):\n");

		List<Player> team = this.model.getDreamTeam(k);

		for (Player p : team) {
			txtResult.appendText(p.toString() + " \n");
		}

	}

	@FXML
	void doTopPlayer(ActionEvent event) {

		txtResult.clear();
		Player top = this.model.getTopPlayer();

		if (top == null) {
			txtResult.appendText("Nessun top player trovato");
			return;
		}

		txtResult.appendText("Giocatore TOP PLAYER: " + top + "\n");
		txtResult.appendText("(giocatori battuti in ordine decrescente di peso)\n");
		List<GiocatoreBattuto> lista = this.model.getBattuti(top);

		for (GiocatoreBattuto g : lista) {
			txtResult.appendText(g.getP() + " | " + g.getPeso() + "\n");
		}

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		btnTopPlayer.setDisable(true);
		btnDreamTeam.setDisable(true);
	}
}
