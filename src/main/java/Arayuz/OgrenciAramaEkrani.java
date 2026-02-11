package Arayuz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Aksiyonlar.OgrenciAramaAksiyonlari;

public class OgrenciAramaEkrani {

	public static OgrenciAramaEkrani ogrenciAramaEkrani;

	private JPanel ogrenciAramaPaneli;
	private JLabel ogrenciNoGirmeEtiketi;
	private JTextField ogrenciNoGirmeAlani;
	private ImageIcon aramaIkon;
	private Image aramaImage;
	private JButton ogrenciAramaButonu;
	private JTextArea ogrenciBilgisiAlani;
	private JScrollPane ogrenciBilgisiKaydirmaCubugu;
	private ImageIcon geriDonIkon;
	private Image geriDonImage;
	private JButton geriDonButonu;

	private OgrenciAramaEkrani() {

		ogrenciAramaPaneli = new JPanel();
		ogrenciAramaPaneli.setBounds(0, 0, 1000, 600);
		ogrenciAramaPaneli.setBackground(new Color(90, 140, 120));
		ogrenciAramaPaneli.setLayout(null);
		ogrenciAramaPaneli.setVisible(false);

		ogrenciNoGirmeEtiketi = new JLabel("Öğrenci numarsını giriniz");
		ogrenciNoGirmeEtiketi.setBounds(350, 60, 240, 30);
		ogrenciNoGirmeEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		ogrenciAramaPaneli.add(ogrenciNoGirmeEtiketi);

		ogrenciNoGirmeAlani = new JTextField();
		ogrenciNoGirmeAlani.setBounds(350, 100, 230, 30);
		ogrenciNoGirmeAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		ogrenciNoGirmeAlani.setFont(new Font("Serif", Font.BOLD, 20));
		ogrenciAramaPaneli.add(ogrenciNoGirmeAlani);

		aramaIkon = new ImageIcon(GirisEkrani.class.getResource("/magnifying.png"));
		aramaImage = aramaIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		aramaIkon = new ImageIcon(aramaImage);

		ogrenciAramaButonu = new JButton("");
		ogrenciAramaButonu.setBounds(610, 80, 50, 50);
		ogrenciAramaButonu.setFocusable(false);
		ogrenciAramaButonu.setFont(new Font("Serif", Font.BOLD, 20));
		ogrenciAramaButonu.setIcon(aramaIkon);
		ogrenciAramaButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		ogrenciAramaPaneli.add(ogrenciAramaButonu);
		ogrenciAramaButonu.addActionListener(e -> OgrenciAramaAksiyonlari.ogrenciBilgileriniGoster());

		ogrenciBilgisiAlani = new JTextArea();
		ogrenciBilgisiAlani.setEditable(false);
		ogrenciBilgisiAlani.setFont(new Font("Serif", Font.BOLD, 20));
		ogrenciBilgisiAlani.setLineWrap(true);
		ogrenciBilgisiAlani.setWrapStyleWord(true);
		ogrenciBilgisiAlani.setBackground(new Color(220, 235, 230));
		ogrenciBilgisiAlani.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		ogrenciBilgisiKaydirmaCubugu = new JScrollPane(ogrenciBilgisiAlani);
		ogrenciBilgisiKaydirmaCubugu.setBounds(100, 180, 800, 350);
		ogrenciBilgisiKaydirmaCubugu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		ogrenciAramaPaneli.add(ogrenciBilgisiKaydirmaCubugu);

		geriDonIkon = new ImageIcon(GirisEkrani.class.getResource("/geri.png"));
		geriDonImage = geriDonIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		geriDonIkon = new ImageIcon(geriDonImage);

		geriDonButonu = new JButton("");
		geriDonButonu.setBounds(20, 20, 50, 50);
		geriDonButonu.setFocusable(false);
		geriDonButonu.setFont(new Font("Serif", Font.BOLD, 20));
		geriDonButonu.setIcon(geriDonIkon);
		geriDonButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		ogrenciAramaPaneli.add(geriDonButonu);
		geriDonButonu.addActionListener(e -> OgrenciAramaAksiyonlari.geriDonButonuKontrol());
	}

	public static OgrenciAramaEkrani getOgrenciAramaEkrani() {

		if (ogrenciAramaEkrani == null)
			ogrenciAramaEkrani = new OgrenciAramaEkrani();

		return ogrenciAramaEkrani;
	}

	public JPanel getOgrenciAramaPaneli() {

		return this.ogrenciAramaPaneli;
	}

	public JTextField getOgrenciNoGirmeAlani() {

		return this.ogrenciNoGirmeAlani;
	}

	public JTextArea getOgrenciBilgisiAlani() {

		return this.ogrenciBilgisiAlani;
	}
}
