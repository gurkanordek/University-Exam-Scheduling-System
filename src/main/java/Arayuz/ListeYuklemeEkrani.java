package Arayuz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Aksiyonlar.ListeYuklemeAksiyonlari;

public class ListeYuklemeEkrani {

	private static ListeYuklemeEkrani listeYuklemeEkrani;

	private JPanel listeYuklemePaneli;
	private ImageIcon dosyaIkon;
	private Image dosyaImage;
	private JLabel dersListesiYuklemeEtiketi;
	private JButton dersListesiYuklemeButonu;
	private JLabel ogrenciListesiYuklemeEtiketi;
	private JButton ogrenciListesiYuklemeButonu;
	private JLabel bolumAdiEtiketi;
	private JComboBox<String> bolumAdiSecimi;
	private JButton onaylaButonu;
	private ImageIcon geriDonIkon;
	private Image geriDonImage;
	private JButton geriDonButonu;

	private ListeYuklemeEkrani() throws IOException {

		listeYuklemePaneli = new JPanel();
		listeYuklemePaneli.setBounds(0, 0, 1000, 600);
		listeYuklemePaneli.setBackground(new Color(90, 140, 120));
		listeYuklemePaneli.setLayout(null);
		listeYuklemePaneli.setVisible(false);

		dosyaIkon = new ImageIcon(GirisEkrani.class.getResource("/backlog.png"));
		dosyaImage = dosyaIkon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		dosyaIkon = new ImageIcon(dosyaImage);

		dersListesiYuklemeEtiketi = new JLabel("Ders listesini yükleyiniz", dosyaIkon, JLabel.LEFT);
		dersListesiYuklemeEtiketi.setBounds(350, 130, 250, 30);
		dersListesiYuklemeEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		listeYuklemePaneli.add(dersListesiYuklemeEtiketi);

		dersListesiYuklemeButonu = new JButton("Dosya Seç");
		dersListesiYuklemeButonu.setBounds(350, 170, 250, 30);
		dersListesiYuklemeButonu.setFocusable(false);
		dersListesiYuklemeButonu.setFont(new Font("Serif", Font.BOLD, 20));
		dersListesiYuklemeButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		listeYuklemePaneli.add(dersListesiYuklemeButonu);
		dersListesiYuklemeButonu
				.addActionListener(e -> ListeYuklemeAksiyonlari.listeYuklemeButonlariKontrol("dersListesi"));

		ogrenciListesiYuklemeEtiketi = new JLabel("Öğrenci listesini yükleyiniz", dosyaIkon, JLabel.LEFT);
		ogrenciListesiYuklemeEtiketi.setBounds(350, 210, 270, 30);
		ogrenciListesiYuklemeEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		listeYuklemePaneli.add(ogrenciListesiYuklemeEtiketi);

		ogrenciListesiYuklemeButonu = new JButton("Dosya Seç");
		ogrenciListesiYuklemeButonu.setBounds(350, 250, 250, 30);
		ogrenciListesiYuklemeButonu.setFocusable(false);
		ogrenciListesiYuklemeButonu.setFont(new Font("Serif", Font.BOLD, 20));
		ogrenciListesiYuklemeButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		listeYuklemePaneli.add(ogrenciListesiYuklemeButonu);
		ogrenciListesiYuklemeButonu
				.addActionListener(e -> ListeYuklemeAksiyonlari.listeYuklemeButonlariKontrol("ogrenciListesi"));

		bolumAdiEtiketi = new JLabel("Bölüm türünü seçiniz");
		bolumAdiEtiketi.setBounds(350, 290, 250, 30);
		bolumAdiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		listeYuklemePaneli.add(bolumAdiEtiketi);

		String[] bolumler = { "Seçiniz", "Bilgisayar Mühendisliği", "Yazılım Mühendisliği", "Elektrik Mühendisliği",
				"Elektronik Mühendisliği", "İnşaat Mühendisliği" };
		bolumAdiSecimi = new JComboBox<String>(bolumler);
		bolumAdiSecimi.setBounds(350, 330, 250, 30);
		bolumAdiSecimi.setBorder(BorderFactory.createLineBorder(Color.black));
		bolumAdiSecimi.setFont(new Font("Serif", Font.BOLD, 20));
		listeYuklemePaneli.add(bolumAdiSecimi);

		onaylaButonu = new JButton("Onayla");
		onaylaButonu.setBounds(350, 370, 250, 30);
		onaylaButonu.setFocusable(false);
		onaylaButonu.setFont(new Font("Serif", Font.BOLD, 20));
		onaylaButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		listeYuklemePaneli.add(onaylaButonu);
		onaylaButonu.addActionListener(e -> {
			ListeYuklemeAksiyonlari.onaylaButonuKontrol();
		});

		geriDonIkon = new ImageIcon(GirisEkrani.class.getResource("/geri.png"));
		geriDonImage = geriDonIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		geriDonIkon = new ImageIcon(geriDonImage);

		geriDonButonu = new JButton("");
		geriDonButonu.setBounds(20, 20, 50, 50);
		geriDonButonu.setFocusable(false);
		geriDonButonu.setFont(new Font("Serif", Font.BOLD, 20));
		geriDonButonu.setIcon(geriDonIkon);
		geriDonButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		listeYuklemePaneli.add(geriDonButonu);
		geriDonButonu.addActionListener(e -> ListeYuklemeAksiyonlari.geriDonButonuKontrol());
	}

	public static ListeYuklemeEkrani getListeYuklemeEkrani() {

		if (listeYuklemeEkrani == null)
			try {
				listeYuklemeEkrani = new ListeYuklemeEkrani();
			} catch (IOException e) {
				e.printStackTrace();
			}

		return listeYuklemeEkrani;
	}

	public JPanel getListeYuklemePaneli() {

		return this.listeYuklemePaneli;
	}

	public JButton getDersListesiYuklemeButonu() {

		return this.dersListesiYuklemeButonu;
	}

	public JButton getOgrenciListesiYuklemeButonu() {

		return this.ogrenciListesiYuklemeButonu;
	}

	public JComboBox<String> getBolumAdiSecimi() {

		return this.bolumAdiSecimi;
	}
}
