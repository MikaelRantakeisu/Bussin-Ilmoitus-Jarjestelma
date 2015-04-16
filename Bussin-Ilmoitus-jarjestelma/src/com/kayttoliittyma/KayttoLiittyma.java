package com.kayttoliittyma;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.toimintalogiikka.TietoDto;
import com.toimintalogiikka.Viesti;

public class KayttoLiittyma extends JPanel implements KayttoLiittymaRajaPinta, ActionListener{
	private Presender presender = null;
	private JButton sijainti = new JButton("sijainti");
	private JButton lopeta = new JButton("lopeta");
	private JLabel sijaintiNyt =  new JLabel("Sijainti");
	private JTextArea sijaintiIlmoitus = new JTextArea("Sijainti");

	public KayttoLiittyma(Presender presender) {
		this.presender = presender;
		/*
		this.getRootPane().add(this.lopeta);
		this.getRootPane().add(this.sijainti);
		this.getRootPane().add(this.sijaintiNyt);
		this.getRootPane().add(this.sijaintiIlmoitus);
		 */
		this.lopeta.addActionListener(this);
		this.sijainti.addActionListener(this);

		//this.getRootPane().setVisible(true);
	}

	/**
	 * Metodi joka asettaa toimintalogiikka kerroksesta tulleen tiedon 
	 * näytölle
	 * 
	 * 
	 */

	@Override
	public void kayttoLiittymaTietoDto(TietoDto dto) {

		//if(dto.getViesti().equals(Viesti.SIJAINTI))
			//this.sijaintiIlmoitus.setText(dto.getViesti().toString()+": "+dto.getTeksti());

		this.sijaintiIlmoitus.setText(dto.getViesti().toString()+ ": "+ dto.getTeksti());
	}

	/**
	 * Metodi, joka lähettää viestin presender oliolle ja sitä 
	 * kautta toimintalogiikka kerrokseen
	 * @param mikaViesti
	 */

	private void lahetaViesti(int mikaViesti){
		switch (mikaViesti) {
		case 1:

			this.presender.kapseloiTietoDto(Viesti.KAYNNISTA);

			break;

		case 2:

			this.presender.kapseloiTietoDto(Viesti.LOPETA);

			break;


		case 3:

			this.presender.kapseloiTietoDto(Viesti.SIJAINTI);

			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if(arg0.getSource().equals(sijainti)){
			this.lahetaViesti(3);
		}

		if(arg0.getSource().equals(lopeta)){
			this.lahetaViesti(2);
		}

	}

}
