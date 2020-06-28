package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.TwoPlayers;

public class PremierLeagueDAO {

	public List<TwoPlayers> getCoppie() {

		String sql = "select a1.playerID as g1, a2.playerID as g2, sum(a1.timePlayed-a2.timePlayed) as peso "
				+ "from actions as a1, actions as a2 " + "where a1.matchID = a2.matchID "
				+ "AND a1.playerID <> a2.playerID AND a1.teamID <>a2.teamID " + "AND a1.starts = 1 " + "AND a2.starts = 1 " + "group by g1,g2 "
				+ "having peso > 0";

		List<TwoPlayers> listaCoppie = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();
			while (res.next()) {
				;

				TwoPlayers player = new TwoPlayers(res.getInt("g1"), res.getInt("g2"), res.getDouble("peso"));

				listaCoppie.add(player);
			}
			conn.close();
			return listaCoppie;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Ritorna i nodi del nuovo grafo, ovvero i giocatori con media > di quella
	 * inserita
	 * 
	 * @param media
	 * @return
	 */
	public List<Player> getPlayers(double media) {

		String sql = "select a.playerID as id, p.name as nome, sum(a.goals)/count(*) as media "
				+ "from actions as a, players as p " + "where a.playerID = p.playerID " + "group by a.playerID "
				+ "having media > ?";

		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, media);

			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("id"), res.getString("nome"));

				result.add(player);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Player> listAllPlayers() {
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));

				result.add(player);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Action> listAllActions() {
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"), res.getInt("MatchID"), res.getInt("TeamID"),
						res.getInt("Starts"), res.getInt("Goals"), res.getInt("TimePlayed"), res.getInt("RedCards"),
						res.getInt("YellowCards"), res.getInt("TotalSuccessfulPassesAll"),
						res.getInt("totalUnsuccessfulPassesAll"), res.getInt("Assists"),
						res.getInt("TotalFoulsConceded"), res.getInt("Offsides"));

				result.add(action);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
