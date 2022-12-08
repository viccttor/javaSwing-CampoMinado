package br.com.campoMinado.model;

@FunctionalInterface
public interface CampoObservador {
	public void eventoOcorreu(Campo campo, CampoEvento evento);

}
