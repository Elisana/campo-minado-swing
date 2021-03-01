package com.gmail.elisana.favarin.cm.visao;

import javax.swing.JFrame;

import com.gmail.elisana.favarin.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame{
	
	//Criar construtor			
	public TelaPrincipal() {
		
		Tabuleiro tabuleiro = new Tabuleiro(16, 30, 5);
		
		//cria um painel tabuleiro e adiciona na interface da tela principal (aqui) 
		add (new PainelTabuleiro(tabuleiro)); 
		
		setTitle("Campo Minado");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); //faz a aplicação para ao fechar a janela
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new TelaPrincipal();
	}
}
