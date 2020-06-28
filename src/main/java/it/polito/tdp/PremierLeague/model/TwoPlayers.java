package it.polito.tdp.PremierLeague.model;

public class TwoPlayers {

	private Integer id1;
	private Integer id2;
	private Double peso;
	
	public TwoPlayers(Integer id1, Integer id2, Double peso) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.peso = peso;
	}
	public Integer getId1() {
		return id1;
	}
	public void setId1(Integer id1) {
		this.id1 = id1;
	}
	public Integer getId2() {
		return id2;
	}
	public void setId2(Integer id2) {
		this.id2 = id2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	
	
	
	
	
}
