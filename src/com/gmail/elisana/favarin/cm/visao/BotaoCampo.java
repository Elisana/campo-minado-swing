package com.gmail.elisana.favarin.cm.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.gmail.elisana.favarin.cm.modelo.Campo;
import com.gmail.elisana.favarin.cm.modelo.CampoEvento;
import com.gmail.elisana.favarin.cm.modelo.CampoObservador;

//Esse é o componente visual correspondente a cada um dos campos do tabuleiro
//ele traduz tudo que ocorre no campo
@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador, MouseListener{
	
	private final Color BG_PADRAO = new Color(184,184,184); //cinza
	private final Color BG_MARCAR = new Color(8,179,247); 
	private final Color BG_EXPLODIR = new Color(189,66,68); //VERMELHO
	private final Color TEXTO_VERDE = new Color(0,100,0); //VERDE
	
	private Campo campo;
	
	//construtor
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO); //Seta a cor padrão cinza
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0)); //set bordas para o botão
		
		//registra essa classe como observador
		campo.registrarObservador(this);
		
		//a propria classe implementa o MouseListener
	    addMouseListener(this);
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		switch(evento) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;	
		default:
			aplicarEstiloPadrão();
		}
	}

	private void aplicarEstiloPadrão() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");		
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLODIR);
		setForeground(Color.WHITE);
		setText("X");
	}

	private void aplicarEstiloMarcar() {
		setBackground(BG_MARCAR);
		setForeground(Color.BLACK);
		setText("M");		
	}

	private void aplicarEstiloAbrir() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(campo.isMinado()) {
			setBackground(BG_EXPLODIR);	
			return;
		}		
		
		setBackground(BG_PADRAO);	
		
		switch(campo.minasNaVizinhanca()) {
		case 1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:	
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		String valor = !campo.vizinhancaSegura() ?
				 		campo.minasNaVizinhanca() + "" : "";
		setText(valor);
	}
		
	//inteface dos eventos do mouse
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			//quando clica no botão esquerdo, é para abrir o campo
			campo.abrir();		
		}else {
			//quando clica no botão direito, é para alternar a marcação do campo
			campo.alternarMarcacao();			
		}		
	}
	
	public void mouseClicked(MouseEvent e) {}	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}	

	

}
