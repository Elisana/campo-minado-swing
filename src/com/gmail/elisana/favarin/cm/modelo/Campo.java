package com.gmail.elisana.favarin.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {
	
	private final int linha;
	private final int coluna;
	
	private boolean aberto;
	private boolean minado;
	private boolean marcado;
	
	
	//Autorelacionamento: ter um campo do tipo da própria classe
	//atributo é private para que nenhuma outra classe altere os vizinhos do campo
	private List<Campo> vizinhos = new ArrayList<Campo>();
	private List<CampoObservador> observadores = new ArrayList<>();
	
	//o nível de visibilidade do construtor do campo é pacote
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;		
	}
	
	//cria método público para registrar observadores
	public void registrarObservador(CampoObservador observador) {
		observadores.add(observador);
	}
	
	//esse método é private porque ele vai ocorrer apenas dentro do campo. Quando um
	//evento ocorrer, ele vai notificar os observadores.
	private void notificarObservadores(CampoEvento evento) {
		observadores.stream()
			.forEach(o -> o.eventoOcorreu(this, evento));
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha); //diferença da linha
		int deltaColuna = Math.abs(coluna - vizinho.coluna); //diferença entre as colunas
		int deltaGeral = deltaColuna + deltaLinha;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2 && diagonal){
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}		
	}
	
	public void alternarMarcacao() {		
		//só marca os campos que ainda não foram abertos
		if(!aberto) {
			marcado = !marcado;
			if(marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			}else {
				notificarObservadores(CampoEvento.DESMARCAR);
			}
		}
	}
	
	public boolean abrir() {
		//abre o campo se ele estiver fechado e não estiver aberto
		if(!aberto && !marcado) {
			aberto = true;
			
			//se o campo está minado, acaba o jogo
			if(minado) {
				//Vai interromper o processo e voltar para quem chamou
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}
			
			setAberto(true);			
			//se os vizinhos são seguros, faz uma chamada recursiva para abrir os vizinhos dos vizinhos
			if(vizinhancaSegura()) {
				//passa uma consumer como parametro
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
	
	void minar() {		
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	
	void setAberto(boolean aberto){
		this.aberto = aberto;
		if(aberto) {
		   notificarObservadores(CampoEvento.ABRIR);
		}
	}
	
	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	public int minasNaVizinhanca() {
		return (int) vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}
}
