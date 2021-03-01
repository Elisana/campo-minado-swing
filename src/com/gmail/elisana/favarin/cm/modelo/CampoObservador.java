package com.gmail.elisana.favarin.cm.modelo;

@FunctionalInterface
public interface CampoObservador {
	public void eventoOcorreu(Campo campo, CampoEvento evento);
}
