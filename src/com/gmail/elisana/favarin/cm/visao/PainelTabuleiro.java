package com.gmail.elisana.favarin.cm.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.elisana.favarin.cm.modelo.Tabuleiro;

//JPanel � um container, � um agrupador de componentes
@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel{

	//Construtor
	public PainelTabuleiro(Tabuleiro tabuleiro) {
		//aqui define como ser� o leiaute. como os componentes visuais ser�o apresentados
		//no nosso caso usaremos o griplayout pois queremos uma grid
		setLayout(new GridLayout(
				tabuleiro.getLinhas(),tabuleiro.getColunas()));
		
		tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
		
		//nessa classe iremos mostrar o resultado ao usu�rio
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
