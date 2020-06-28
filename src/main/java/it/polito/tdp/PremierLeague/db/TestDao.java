package it.polito.tdp.PremierLeague.db;

import java.util.List;

import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.TwoPlayers;

public class TestDao {

	public static void main(String[] args) {
		TestDao testDao = new TestDao();
		testDao.run();
	}

	public void run() {
		PremierLeagueDAO dao = new PremierLeagueDAO();
		/*
		 * System.out.println("Players:"); System.out.println(dao.listAllPlayers());
		 * System.out.println("Actions:"); System.out.println(dao.listAllActions());
		 */

		List<Player> lista = dao.getPlayers(0.8);

		System.out.println(lista);

		List<TwoPlayers> listaCoppie = dao.getCoppie();

		TwoPlayers prova = listaCoppie.get(0);

		System.out.print(prova.getId1() + " " + prova.getId2() + " " + prova.getPeso());

	}

}
