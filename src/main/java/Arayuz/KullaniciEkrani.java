package Arayuz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Aksiyonlar.KullaniciAksiyonlari;
import Aksiyonlar.DbBaglantisi;

public class KullaniciEkrani {

	private static KullaniciEkrani kullaniciEkrani;

	private JPanel kullaniciPaneli;
	private JButton koordinatorEklemeButonu;
	private JButton derslikEklemeButonu;
	private JButton derslikDuzenleSilButonu;
	private JButton derslikAramaButonu;
	private JButton listeYuklemeButonu;
	private JButton ogrenciAramaButonu;
	private JButton dersAramaButonu;
	private JButton sinavProgramiOlusturmaButonu;
	private ImageIcon geriDonIkon;
	private Image geriDonImage;
	private JButton geriDonButonu;

	private KullaniciEkrani() {

		kullaniciPaneli = new JPanel();
		kullaniciPaneli.setBounds(0, 0, 1000, 600);
		kullaniciPaneli.setBackground(new Color(90, 140, 120));
		kullaniciPaneli.setLayout(null);
		kullaniciPaneli.setVisible(false);

		derslikEklemeButonu = new JButton("Derslik Ekle");
		derslikEklemeButonu.setBounds(350, 170, 250, 30);
		derslikEklemeButonu.setFocusable(false);
		derslikEklemeButonu.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemeButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		kullaniciPaneli.add(derslikEklemeButonu);
		derslikEklemeButonu.addActionListener(e -> KullaniciAksiyonlari.derslikEkleButonuKontrol());

		derslikDuzenleSilButonu = new JButton("Derslik Düzenle / Sil");
		derslikDuzenleSilButonu.setBounds(350, 210, 250, 30);
		derslikDuzenleSilButonu.setFocusable(false);
		derslikDuzenleSilButonu.setFont(new Font("Serif", Font.BOLD, 20));
		derslikDuzenleSilButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		kullaniciPaneli.add(derslikDuzenleSilButonu);
		derslikDuzenleSilButonu.addActionListener(e -> KullaniciAksiyonlari.derslikDuzenleSilButonuKontrol());

		derslikAramaButonu = new JButton("Derslik Ara");
		derslikAramaButonu.setBounds(350, 250, 250, 30);
		derslikAramaButonu.setFocusable(false);
		derslikAramaButonu.setFont(new Font("Serif", Font.BOLD, 20));
		derslikAramaButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		kullaniciPaneli.add(derslikAramaButonu);
		derslikAramaButonu.addActionListener(e -> KullaniciAksiyonlari.derslikAramaButonuKontrol());

		listeYuklemeButonu = new JButton("Listeleri Yükle");
		listeYuklemeButonu.setBounds(350, 290, 250, 30);
		listeYuklemeButonu.setFocusable(false);
		listeYuklemeButonu.setFont(new Font("Serif", Font.BOLD, 20));
		listeYuklemeButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		kullaniciPaneli.add(listeYuklemeButonu);
		listeYuklemeButonu.addActionListener(e -> KullaniciAksiyonlari.listeleriYukleButonuKontrol());

		dersAramaButonu = new JButton("Ders Ara");
		dersAramaButonu.setBounds(350, 330, 250, 30);
		dersAramaButonu.setFocusable(false);
		dersAramaButonu.setFont(new Font("Serif", Font.BOLD, 20));
		dersAramaButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		kullaniciPaneli.add(dersAramaButonu);
		dersAramaButonu.addActionListener(e -> KullaniciAksiyonlari.dersAramaButonuKontrol());

		ogrenciAramaButonu = new JButton("Öğrenci Ara");
		ogrenciAramaButonu.setBounds(350, 370, 250, 30);
		ogrenciAramaButonu.setFocusable(false);
		ogrenciAramaButonu.setFont(new Font("Serif", Font.BOLD, 20));
		ogrenciAramaButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		kullaniciPaneli.add(ogrenciAramaButonu);
		ogrenciAramaButonu.addActionListener(e -> KullaniciAksiyonlari.ogrenciAramaButonuKontrol());

		sinavProgramiOlusturmaButonu = new JButton("Sınav Programı Oluştur");
		sinavProgramiOlusturmaButonu.setBounds(350, 410, 250, 30);
		sinavProgramiOlusturmaButonu.setFocusable(false);
		sinavProgramiOlusturmaButonu.setFont(new Font("Serif", Font.BOLD, 20));
		sinavProgramiOlusturmaButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		kullaniciPaneli.add(sinavProgramiOlusturmaButonu);
		sinavProgramiOlusturmaButonu.addActionListener(e -> KullaniciAksiyonlari.sinavProgramiOlusturmaButonuKontrol());

		geriDonIkon = new ImageIcon(GirisEkrani.class.getResource("/geri.png"));
		geriDonImage = geriDonIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		geriDonIkon = new ImageIcon(geriDonImage);

		geriDonButonu = new JButton("");
		geriDonButonu.setBounds(20, 20, 50, 50);
		geriDonButonu.setFocusable(false);
		geriDonButonu.setFont(new Font("Serif", Font.BOLD, 20));
		geriDonButonu.setIcon(geriDonIkon);
		geriDonButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		kullaniciPaneli.add(geriDonButonu);
		geriDonButonu.addActionListener(e -> KullaniciAksiyonlari.geriDonButonuKontrol());
	}

	public void koordinatorEklemeButonuGoster() {

		if (GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().equals("admin")) {

			koordinatorEklemeButonu = new JButton("Bölüm Koordinatoru Ekle");
			koordinatorEklemeButonu.setBounds(350, 130, 250, 30);
			koordinatorEklemeButonu.setFocusable(false);
			koordinatorEklemeButonu.setFont(new Font("Serif", Font.BOLD, 20));
			koordinatorEklemeButonu.setBorder(BorderFactory.createLineBorder(Color.black));
			kullaniciPaneli.add(koordinatorEklemeButonu);
			koordinatorEklemeButonu.addActionListener(e -> KullaniciAksiyonlari.koordinatorEklemeButonuKontrol());
		}
	}

	public void butonlariKontrolEt() {

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sql;
			PreparedStatement sorgu;

			boolean dersVar = false;

			if (GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim().equals("admin")) {

				sql = "SELECT 1 FROM dersler LIMIT 1";
				sorgu = baglanti.prepareStatement(sql);

			} else {

				sql = "SELECT 1 FROM dersler WHERE bolum_adi = ? LIMIT 1";
				sorgu = baglanti.prepareStatement(sql);
				sorgu.setString(1, GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim());
			}

			ResultSet sonuc1 = sorgu.executeQuery();
			dersVar = sonuc1.next();

			boolean ogrenciVar = false;

			if (GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim().equals("admin")) {

				sql = "SELECT 1 FROM ogrenciler LIMIT 1";
				sorgu = baglanti.prepareStatement(sql);

			} else {

				sql = "SELECT 1 FROM ogrenciler WHERE bolum_adi = ? LIMIT 1";
				sorgu = baglanti.prepareStatement(sql);
				sorgu.setString(1, GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim());
			}

			sonuc1 = sorgu.executeQuery();
			ogrenciVar = sonuc1.next();

			boolean derslikVar = false;

			if (GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim().equals("admin")) {

				sql = "SELECT 1 FROM derslikler LIMIT 1";
				sorgu = baglanti.prepareStatement(sql);

			} else {

				sql = "SELECT 1 FROM derslikler WHERE bolum_adi = ? LIMIT 1";
				sorgu = baglanti.prepareStatement(sql);
				sorgu.setString(1, GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim());
			}

			sonuc1 = sorgu.executeQuery();
			derslikVar = sonuc1.next();

			listeYuklemeButonu.setEnabled(derslikVar);
			derslikDuzenleSilButonu.setEnabled(derslikVar);
			derslikAramaButonu.setEnabled(derslikVar);

			if (derslikVar == true && dersVar == true)
				dersAramaButonu.setEnabled(true);
			else
				dersAramaButonu.setEnabled(false);

			if (derslikVar == true && ogrenciVar == true)
				ogrenciAramaButonu.setEnabled(true);
			else
				ogrenciAramaButonu.setEnabled(false);

			if (derslikVar == true && dersVar == true && ogrenciVar == true)
				sinavProgramiOlusturmaButonu.setEnabled(true);
			else
				sinavProgramiOlusturmaButonu.setEnabled(false);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static KullaniciEkrani getKullaniciEkrani() {

		if (kullaniciEkrani == null)
			kullaniciEkrani = new KullaniciEkrani();

		return kullaniciEkrani;
	}

	public JPanel getKullaniciPaneli() {

		return this.kullaniciPaneli;
	}

	public JButton getKoordinatorEklemeButonu() {

		return this.koordinatorEklemeButonu;
	}

	public JButton getListeYuklemeButonu() {

		return this.listeYuklemeButonu;
	}

	public JButton getDerslikDuzenleSilButonu() {

		return this.derslikDuzenleSilButonu;
	}

	public JButton getDerslikAramaButonu() {

		return this.derslikAramaButonu;
	}

	public JButton getOgrenciAramaButonu() {

		return this.ogrenciAramaButonu;
	}

	public JButton getDersAramaButonu() {

		return this.dersAramaButonu;
	}

	public JButton getSinavProgramiOlusturmaButonu() {

		return this.sinavProgramiOlusturmaButonu;
	}
}
