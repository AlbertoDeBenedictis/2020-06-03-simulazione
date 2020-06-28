package it.polito.tdp.PremierLeague.model;

public class GiocatoreBattuto implements Comparable<GiocatoreBattuto>{

	Player p;
	Double peso;

	public GiocatoreBattuto(Player p, Double peso) {
		super();
		this.p = p;
		this.peso = peso;
	}

	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(GiocatoreBattuto o) {
		// TODO Auto-generated method stub
		return -this.getPeso().compareTo(o.getPeso());
	}

}
