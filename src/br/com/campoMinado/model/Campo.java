package br.com.campoMinado.model;


import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int LINHA;
	private final int COLUNA;
 
	private boolean aberto = false;
	private boolean marcado = false;
	private boolean minado = false;

	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observadores = new ArrayList<>();

	private void notificarObservadores(CampoEvento evento) {
		observadores.stream()
			.forEach(o -> o.eventoOcorreu(this, evento));
	}
	
	boolean adicionarVizinho(Campo vizinho) {

		boolean linhaDiferente = LINHA != vizinho.LINHA;
		boolean colunaDiferente = COLUNA != vizinho.COLUNA;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(LINHA - vizinho.LINHA);
		int deltaColuna = Math.abs(COLUNA - vizinho.COLUNA);
		int deltaGeral = deltaColuna + deltaLinha;

		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}

	} 

	public void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
			
			if (marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			} else {
				notificarObservadores(CampoEvento.DESMARCAR);
			}
		}
	}

	public boolean abrir() {

		if (!aberto && !marcado) {

			if (minado) {
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}
			
			setAberto(true);
			
			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	public Campo(int linha, int coluna) {
		this.LINHA = linha;
		this.COLUNA = coluna;
	}
	
	public void regisrarObservador(CampoObservador observador) {
		observadores.add(observador);
	}

	public boolean isMarcado() {
		return marcado;
	}

	public boolean isOpen() {
		return aberto;
	}

	public boolean isClose() {
		return !aberto;
	}

	void minar() {
		if (!minado) {
			minado = true;
		}
	}
	
	public boolean isMinado() {
		return minado;
	}
	 

	 void setAberto(boolean aberto) {
		this.aberto = aberto;
		if (aberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protejido = minado && marcado;
		return desvendado || protejido;
	}

	public int minasNaVinzinhaca() {
		return (int) vizinhos.stream().filter(v -> v.minado).count();
	}

	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}

	public int getLINHA() {
		return LINHA;
	}

	public int getCOLUNA() {
		return COLUNA;
	}

}
