package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {

	Graph<Player, DefaultWeightedEdge> grafo;
	PremierLeagueDAO dao;
	Map<Integer, Player> playerIdMap;
	List<Player> best;
	Integer gradoBest;

	public Model() {
		this.dao = new PremierLeagueDAO();
		playerIdMap = new HashMap<>();
		List<Player> players = this.dao.listAllPlayers();
		for (Player p : players) {
			playerIdMap.put(p.getPlayerID(), p);
		}

	}

	public int getVertici() {
		return this.grafo.vertexSet().size();
	}

	public Player getTopPlayer() {

		Player top = null;
		Integer gradoMax = 0;

		// Trovo il player col grado maggiore
		for (Player p : this.grafo.vertexSet()) {

			Integer battuti = this.grafo.outDegreeOf(p);
			if (battuti > gradoMax) {
				top = p;
				gradoMax = battuti;
			}
		}

		return top;

	}

	public List<Player> getDreamTeam(Integer k) {

		List<Player> parziale = new ArrayList<>();

		List<Player> inutilizzabili = new ArrayList<>();
		gradoBest = 0;

		ricorsione(parziale, k, inutilizzabili);

		return best;
	}

	private void ricorsione(List<Player> parziale, Integer k, List<Player> inutilizzabili) {

		// CASO TERMINALE
		if (parziale.size() == k) {
			// se la soluzione è migliore della best attuale
			Integer gradoParz = getTitolarità(parziale);
			if (gradoParz > gradoBest) {

				this.best = new ArrayList<>(parziale);
				gradoBest = gradoParz;

			}
			return;
		}

		// CREAZIONE NUOVE SOLUZIONI
		for (Player p : this.grafo.vertexSet()) {

			if (!inutilizzabili.contains(p) && (!parziale.contains(p))) {

				parziale.add(p);
				List<Player> inut = new ArrayList<>(inutilizzabili);
				for (Player daAgg : Graphs.successorListOf(this.grafo, p)) {
					inut.add(daAgg);
				}
				ricorsione(parziale, k, inut);
				parziale.remove(p);

			}

		}

	}

	private Integer getTitolarità(List<Player> parziale) {

		Integer titolarita = 0;

		for (Player p : parziale) {

			int gradoIn = 0;
			int gradoOut = 0;

			for (Player battuto : Graphs.successorListOf(this.grafo, p)) {

				gradoOut += this.grafo.getEdgeWeight(this.grafo.getEdge(p, battuto));
			}

			for (Player battente : Graphs.predecessorListOf(this.grafo, p)) {

				gradoIn += this.grafo.getEdgeWeight(this.grafo.getEdge(battente, p));
			}
			titolarita += gradoOut - gradoIn;

		}

		return titolarita;
	}

	public List<GiocatoreBattuto> getBattuti(Player p) {

		List<Player> lista = Graphs.successorListOf(this.grafo, p);

		List<GiocatoreBattuto> result = new ArrayList<>();

		for (Player giocatore : lista) {

			Double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(p, giocatore));

			result.add(new GiocatoreBattuto(giocatore, peso));

		}
		// ordino decrescente
		Collections.sort(result);

		return result;

	}

	public int getArchi() {
		return this.grafo.edgeSet().size();
	}

	public void creaGrafo(double media) {

		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

		// Aggiungiamo i vertici
		List<Player> giocatori = this.dao.getPlayers(media);
		Graphs.addAllVertices(this.grafo, giocatori);

		// Ora aggiungiamo gli archi
		List<TwoPlayers> coppie = this.dao.getCoppie();

		for (TwoPlayers t : coppie) {

			Player giocatore1 = playerIdMap.get(t.getId1());
			Player giocatore2 = playerIdMap.get(t.getId2());

			// se entrambi i giocatori fanno parte del grafo, aggiungo l'arco
			if (this.grafo.containsVertex(giocatore1) && this.grafo.containsVertex(giocatore2)) {

				Graphs.addEdgeWithVertices(this.grafo, giocatore1, giocatore2, t.getPeso());

			}

		}

	}

}
