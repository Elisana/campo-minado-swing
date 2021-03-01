package com.gmail.elisana.favarin.cm.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.elisana.favarin.cm.modelo.Tabuleiro;

//JPanel é um container, é um agrupador de componentes
@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel{

	//Construtor
	public PainelTabuleiro(Tabuleiro tabuleiro) {
		//aqui define como será o leiaute. como os componentes visuais serão apresentados
		//no nosso caso usaremos o griplayout pois queremos uma grid
		setLayout(new GridLayout(
				tabuleiro.getLinhas(),tabuleiro.getColunas()));
		
		tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
		
		//nessa classe iremos mostrar o resultado ao usuário
		tabuleiro.registrarObservador( e ->{
			SwingUtilities.invokeLater(() ->{
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "Ganhou :)");
				} else {
					JOptionPane.showMessageDialog(this, "Perdeu :)");
				}
				tabuleiro.reiniciar();
			});
		});
		
	}

}
