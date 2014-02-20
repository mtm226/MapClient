package mapPack;

// Kartankatseluohjelman graafinen käyttöliittymä

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.ArrayList;

public class MapDialog extends JFrame {

	// Luodaan kartta-olio
	Map kartta = new Map(-180.0, -90.0, 180.0, 90.0);
	
	// Zoomaustaso
	private double zoomcount = 1.0;

	// Käyttöliittymän komponentit
	private JLabel imageLabel = new JLabel();
	private JPanel leftPanel = new JPanel();
	private JButton refreshB = new JButton("Päivitä");
	private JButton leftB = new JButton("<");
	private JButton rightB = new JButton(">");
	private JButton upB = new JButton("^");
	private JButton downB = new JButton("v");
	private JButton zoomInB = new JButton("+");
	private JButton zoomOutB = new JButton("-");

	public MapDialog() throws Exception {

		//Haetaan layer-infot XMLKasittelija luokan getLayer-metodeilla
		ArrayList<String> titles = XMLHandler.getLayerTitles();
		ArrayList<String> names = XMLHandler.getLayerNames();
		
		// Valmistele ikkuna ja lisää siihen komponentit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		//Aloitusnäkymä
		imageLabel.setIcon(new ImageIcon(new URL("http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX=-180,-90,180,90&SRS=EPSG:4326&WIDTH=953&HEIGHT=480&LAYERS=bluemarble&STYLES=&FORMAT=image/png&TRANSPARENT=true")));

		add(imageLabel, BorderLayout.EAST);

		ButtonListener bl = new ButtonListener();
		refreshB.addActionListener(bl);  
		leftB.addActionListener(bl);
		rightB.addActionListener(bl);
		upB.addActionListener(bl);
		downB.addActionListener(bl);
		zoomInB.addActionListener(bl);
		zoomOutB.addActionListener(bl);

		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		leftPanel.setMaximumSize(new Dimension(100, 600));

		//SILMUKKA JOKA LISÄÄ KÄYTTÖLIITTYMÄÄN KAIKKIEN XML-DATASTA HAETTUJEN KERROSTEN VALINTALAATIKOT
		for(int i = 0; i < names.size(); i++){
			if(i == 0)
				leftPanel.add(new LayerCheckBox(titles.get(i), names.get(i), true));
			else
				leftPanel.add(new LayerCheckBox(titles.get(i), names.get(i), false));
		}

		leftPanel.add(refreshB);
		leftPanel.add(Box.createVerticalStrut(20));
		leftPanel.add(leftB);
		leftPanel.add(rightB);
		leftPanel.add(upB);
		leftPanel.add(downB);
		leftPanel.add(zoomInB);
		leftPanel.add(zoomOutB);

		add(leftPanel, BorderLayout.WEST);

		pack();
		setVisible(true);

	}
	
	// Zoomaustasoja käsittelevä metodi
	public double zoom() {
		if(zoomcount > 0) {
			return 15.0/zoomcount;
		}
		else {
			return 15.0;
		}
	}

	public static void main(String[] args) throws Exception {
		new MapDialog();
	}

	// Kontrollinappien kuuntelija
	// KAIKKIEN NAPPIEN YHTEYDESSÄ VOINEE HYÖDYNTÄÄ updateImage()-METODIA
	private class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == refreshB) {
				try { updateImage(); } catch(Exception ex) { ex.printStackTrace(); }
			}
			if(e.getSource() == leftB) {
				// VASEMMALLE SIIRTYMINEN KARTALLA
				
				// Päivitetään kartta-olion koordinaatit.
				kartta.setLow_x(kartta.getLow_x()-3.0*zoom());
				kartta.setHigh_x(kartta.getHigh_x()-3.0*zoom());
				
				try{ updateImage(); } catch(Exception e1) {System.out.println(e1.getMessage());}
			}
			if(e.getSource() == rightB) {
				// OIKEALLE SIIRTYMINEN KARTALLA
				
				// Päivitetään kartta-olion koordinaatit.
				kartta.setLow_x(kartta.getLow_x()+3.0*zoom());
				kartta.setHigh_x(kartta.getHigh_x()+3.0*zoom());
				
				try{ updateImage(); } catch(Exception e1) {System.out.println(e1.getMessage());}
			}
			if(e.getSource() == upB) {
				// YLÖSPÄIN SIIRTYMINEN KARTALLA
				
				// Päivitetään kartta-olion koordinaatit.
				kartta.setLow_y(kartta.getLow_y()+3.0*zoom());
				kartta.setHigh_y(kartta.getHigh_y()+3.0*zoom());

				try{ updateImage(); } catch(Exception e1) {System.out.println(e1.getMessage());}
			}
			if(e.getSource() == downB) {
				// ALASPÄIN SIIRTYMINEN KARTALLA
		
				// Päivitetään kartta-olion koordinaatit.
				kartta.setLow_y(kartta.getLow_y()-3.0*zoom());
				kartta.setHigh_y(kartta.getHigh_y()-3.0*zoom());
				
				try{ updateImage(); } catch(Exception e1) {System.out.println(e1.getMessage());}

			}
			if(e.getSource() == zoomInB) {
				// ZOOM IN -TOIMINTO
				
				//Kasvatetaan zoomaustasoa
				zoomcount++;	
			
				// Päivitetään kartta-olion koordinaatit.
				kartta.setLow_x(kartta.getLow_x()+6.0*zoom());
				kartta.setLow_y(kartta.getLow_y()+3.0*zoom());
				kartta.setHigh_x(kartta.getHigh_x()-6.0*zoom());
				kartta.setHigh_y(kartta.getHigh_y()-3.0*zoom());
				
				try{ updateImage(); } catch(Exception e1) {System.out.println(e1.getMessage());}

			}
			if(e.getSource() == zoomOutB) {
				// ZOOM OUT -TOIMINTO
				
				// Pienennetään zoomaustasoa
				zoomcount--;	
				
				// Päivitetään kartta-olion koordinaatit.
				kartta.setLow_x(kartta.getLow_x()-6.0*zoom());
				kartta.setLow_y(kartta.getLow_y()-3.0*zoom());
				kartta.setHigh_x(kartta.getHigh_x()+6.0*zoom());
				kartta.setHigh_y(kartta.getHigh_y()+3.0*zoom());
				
				try{ updateImage(); } catch(Exception e1) {System.out.println(e1.getMessage());}
			}
		}
	}

	// Valintalaatikko, joka muistaa karttakerroksen nimen
	private class LayerCheckBox extends JCheckBox {
		private String name = "";
		public LayerCheckBox(String name, String title, boolean selected) {
			super(title, null, selected);
			this.name = name;
		}
		public String getName() { return name; }
	}

	// Tarkastetaan mitkä karttakerrokset on valittu,
	// tehdään uudesta karttakuvasta pyyntö palvelimelle ja päivitetään kuva
	public void updateImage() throws Exception {
		String s = "";

		// Tutkitaan, mitkä valintalaatikot on valittu, ja
		// kerätään s:ään pilkulla erotettu lista valittujen kerrosten
		// nimistä (käytetään haettaessa uutta kuvaa)
		Component[] components = leftPanel.getComponents();
		for(Component com:components) {
			if(com instanceof LayerCheckBox)
				if(((LayerCheckBox)com).isSelected()) s = s + com.getName() + ",";
		}
		if (s.endsWith(",")) s = s.substring(0, s.length() - 1);

		// getMap-KYSELYN URL-OSOITTEEN MUODOSTAMINEN JA KUVAN PÄIVITYS ERILLISESSÄ SÄIKEESSÄ
		
		//Päivitetään kartta-olion layerit
		kartta.setTasot(s);
		
		//Luodaan Lataaja olio ja käynnistetään säie, jolla kartta ladataan. Tarvittava URL annetaan konstruktorille.
		Lataaja laturi = new Lataaja("http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX=" + kartta.getLow_x() + "," + kartta.getLow_y() + "," + kartta.getHigh_x() + "," + kartta.getHigh_y() + "&SRS=EPSG:4326&WIDTH=953&HEIGHT=480&LAYERS=" + kartta.getTasot() + "&STYLES=&FORMAT=image/png&TRANSPARENT=true");
		laturi.start();
	}
	
	//Lataaja luokassa hoidetaan uuden kartan lataus omassa säikeessään.
	private class Lataaja extends Thread{
		private String url;
		private ImageIcon kuva = new ImageIcon();

		//Konstruktorissa annetaan luokalle tarvittava url, jota kartan lataukseen tarvitaan
		public Lataaja(String url) throws Exception {
			this.url = url;
		}
		
		public void run() {
			try {
				//Ladataan kuva ja ladotaan se käyttöliittymään
				imageLabel.setIcon(new ImageIcon(new URL(url)));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
} // MapDialog
