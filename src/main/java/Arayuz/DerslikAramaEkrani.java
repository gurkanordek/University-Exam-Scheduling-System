package Arayuz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Aksiyonlar.DerslikAramaAksiyonlari;

public class DerslikAramaEkrani {

	private static DerslikAramaEkrani derslikAramaEkrani;

	private JPanel derslikAramaPaneli;
	private JLabel derslikKoduGirmeEtiketi;
	private JTextField derslikKoduGirmeAlani;
	private ImageIcon aramaIkon;
	private Image aramaImage;
	private JButton derslikAramaButonu;
	private JLabel derslikBilgileriEtiketi;
	private JLabel bolumAdiEtiketi;
	private JLabel derslikKoduEtiketi;
	private JLabel derslikAdiEtiketi;
	private JLabel derslikKapasitesiEtiketi;
	private JLabel sutunSayisiEtiketi;
	private JLabel satirSayisiEtiketi;
	private JLabel siraYapisiEtiketi;
	private ImageIcon geriDonIkon;
	private Image geriDonImage;
	private JButton geriButonu;

	private DerslikAramaEkrani() {

		derslikAramaPaneli = new JPanel();
		derslikAramaPaneli.setBounds(0, 0, 1000, 600);
		derslikAramaPaneli.setBackground(new Color(90, 140, 120));
		derslikAramaPaneli.setLayout(null);
		derslikAramaPaneli.setVisible(false);

		derslikKoduGirmeEtiketi = new JLabel("Derslik kodunu giriniz");
		derslikKoduGirmeEtiketi.setBounds(350, 60, 200, 30);
		derslikKoduGirmeEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaPaneli.add(derslikKoduGirmeEtiketi);

		derslikKoduGirmeAlani = new JTextField();
		derslikKoduGirmeAlani.setBounds(350, 100, 200, 30);
		derslikKoduGirmeAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		derslikKoduGirmeAlani.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaPaneli.add(derslikKoduGirmeAlani);

		derslikBilgileriEtiketi = new JLabel("");
		derslikBilgileriEtiketi.setBounds(350, 180, 400, 30);
		derslikBilgileriEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaPaneli.add(derslikBilgileriEtiketi);

		bolumAdiEtiketi = new JLabel("");
		bolumAdiEtiketi.setBounds(350, 220, 400, 30);
		bolumAdiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaPaneli.add(bolumAdiEtiketi);

		derslikKoduEtiketi = new JLabel("");
		derslikKoduEtiketi.setBounds(350, 260, 400, 30);
		derslikKoduEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaPaneli.add(derslikKoduEtiketi);

		derslikAdiEtiketi = new JLabel("");
		derslikAdiEtiketi.setBounds(350, 300, 400, 30);
		derslikAdiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaPaneli.add(derslikAdiEtiketi);

		derslikKapasitesiEtiketi = new JLabel("");
		derslikKapasitesiEtiketi.setBounds(350, 340, 400, 30);
		derslikKapasitesiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaPaneli.add(derslikKapasitesiEtiketi);

		sutunSayisiEtiketi = new JLabel("");
		sutunSayisiEtiketi.setBounds(350, 380, 400, 30);
		sutunSayisiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaPaneli.add(sutunSayisiEtiketi);

		satirSayisiEtiketi = new JLabel("");
		satirSayisiEtiketi.setBounds(350, 420, 400, 30);
		satirSayisiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaPaneli.add(satirSayisiEtiketi);

		siraYapisiEtiketi = new JLabel("");
		siraYapisiEtiketi.setBounds(350, 460, 400, 30);
		siraYapisiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaPaneli.add(siraYapisiEtiketi);

		aramaIkon = new ImageIcon(GirisEkrani.class.getResource("/magnifying.png"));
		aramaImage = aramaIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		aramaIkon = new ImageIcon(aramaImage);

		derslikAramaButonu = new JButton("");
		derslikAramaButonu.setBounds(580, 80, 50, 50);
		derslikAramaButonu.setFocusable(false);
		derslikAramaButonu.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaButonu.setIcon(aramaIkon);
		derslikAramaButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		derslikAramaPaneli.add(derslikAramaButonu);
		derslikAramaButonu.addActionListener(e -> DerslikAramaAksiyonlari.dersligiGoster());

		geriDonIkon = new ImageIcon(GirisEkrani.class.getResource("/geri.png"));
		geriDonImage = geriDonIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		geriDonIkon = new ImageIcon(geriDonImage);

		geriButonu = new JButton("");
		geriButonu.setBounds(20, 20, 50, 50);
		geriButonu.setFocusable(false);
		geriButonu.setFont(new Font("Serif", Font.BOLD, 20));
		geriButonu.setIcon(geriDonIkon);
		geriButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		derslikAramaPaneli.add(geriButonu);
		geriButonu.addActionListener(e -> DerslikAramaAksiyonlari.geriDonButonuKontrol());
	}

	public static DerslikAramaEkrani getDerslikAramaEkrani() {

		if (derslikAramaEkrani == null)
			derslikAramaEkrani = new DerslikAramaEkrani();

		return derslikAramaEkrani;
	}

	public JPanel getDerslikAramaPaneli() {

		return this.derslikAramaPaneli;
	}

	public JTextField getDerslikKoduGirmeAlani() {

		return this.derslikKoduGirmeAlani;
	}

	public JLabel getDerslikBilgileriEtiketi() {

		return this.derslikBilgileriEtiketi;
	}

	public JLabel getBolumAdiEtiketi() {

		return this.bolumAdiEtiketi;
	}

	public JLabel getDerslikKoduEtiketi() {

		return this.derslikKoduEtiketi;
	}

	public JLabel getDerslikAdiEtiketi() {

		return this.derslikAdiEtiketi;
	}

	public JLabel getDerslikKapasitesiEtiketi() {

		return this.derslikKapasitesiEtiketi;
	}

	public JLabel getSutunSayisiEtiketi() {

		return this.sutunSayisiEtiketi;
	}

	public JLabel getSatirSayisiEtiketi() {

		return this.satirSayisiEtiketi;
	}

	public JLabel getSiraYapisiEtiketi() {

		return this.siraYapisiEtiketi;
	}
}
