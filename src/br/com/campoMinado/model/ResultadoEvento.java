package br.com.campoMinado.model;

public class ResultadoEvento {
	
	private final Boolean ganhou;
	
	public ResultadoEvento(boolean ganhou) {
		this.ganhou = ganhou;
	}

	public Boolean isGanhou() {
		return ganhou;
	}
	
	

}
