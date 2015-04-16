package com.kayttoliittyma;

import gnu.io.CommPortIdentifier;

import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.SentenceValidator;

/**
 * Luokasta muodostettu olio kuuntelee SentenceRaeder oliota
 * Joka kuuntelee Nmea muotoista tietoa jostain portista
 * Luokasta muodostettu olio ensiksi kuuntelee mikä portti on 
 * käytössä ja jos portti löytyy niin asettaa kyseisen portin 
 * merkkivirtaa ohjaavan olion (InputStream-luokasta muodostettu)
 * parametrina SentenceReder-luokasta muodostettulle oliolle
 * Kun SentenceReader havaitsee porttiin tulevan Nmea muotoisen
 * merkkivirran niin se synnyttää  tapahtuman jota kuuntelee
 * NmeaTiedonLukijan public void sentenceRead(SentenceEvent arg0)
 * tyyppinen metodi. Saatu SentenceEvent arg0 sisältää paikkatieto
 * joka lähetetään Position tyyppisenä Presender luokasta muodostettulle
 * oliolle 
 * @author Mikael
 *
 */

public class NmeaTiedonLukija implements SentenceListener{

	private Presender presender =  new Presender();

	public NmeaTiedonLukija() {
		this.init();
	}

	private void init() {
		try {
			SerialPort sp = getSerialPort();

			if (sp != null) {
				InputStream is = sp.getInputStream();
				SentenceReader sr = new SentenceReader(is);
				sr.addSentenceListener(this);
				sr.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private SerialPort getSerialPort() {
		try {
			Enumeration<?> e = CommPortIdentifier.getPortIdentifiers();

			while (e.hasMoreElements()) {
				CommPortIdentifier id = (CommPortIdentifier) e.nextElement();

				if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {

					SerialPort sp = (SerialPort) id.open("SerialExample", 30);

					sp.setSerialPortParams(4800, SerialPort.DATABITS_8,
							SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

					InputStream is = sp.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader buf = new BufferedReader(isr);

					System.out.println("Scanning port " + sp.getName());

					// Yritä löytää jokaisesta portista Nmea tietoa viisi kertaa..
					for (int i = 0; i < 5; i++) {
						try {
							String data = buf.readLine();
							if (SentenceValidator.isValid(data)) {
								System.out.println("NMEA löytyi!");
								return sp;
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					is.close();
					isr.close();
					buf.close();
				}
			}
			System.out.println("NMEA dataa ei löytynyt..");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}



	@Override
	public void readingPaused() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readingStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readingStopped() {
		// TODO Auto-generated method stub

	}
	/**
	 * Tämä luokka onn asetettu kuuntelemaan
	 * SentenceReader luokkaa. Aina kun SentenceReader
	 * vastaanottaa Nmea muotoista paikkatietoa
	 * se lähettää saadun Nmea tiedon tähän
	 * metodiin SentenceEvent muotoisen.
	 * 
	 *  Tämä metodi taas lähettää tiedon Presender
	 *  luokasta muodostetulle oliolle Position tyyppisenä
	 * 
	 */

	@Override
	public void sentenceRead(SentenceEvent arg0) {

		GGASentence s = (GGASentence) arg0.getSentence();

		//Position  position = (Position) arg0.getSentence();
		this.presender.kapseloiTietoDto(s.getPosition()) ;
	}

	public static void main(String args[]){
		NmeaTiedonLukija lukija = new NmeaTiedonLukija();
	}

}
