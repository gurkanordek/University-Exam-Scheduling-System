package Aksiyonlar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Arayuz.KullaniciEkrani;
import Arayuz.DerslikAramaEkrani;
import Arayuz.GirisEkrani;

public class DerslikAramaAksiyonlari {

	public static void dersligiGoster() {

		try {

			String derslikKodu = DerslikAramaEkrani.getDerslikAramaEkrani().getDerslikKoduGirmeAlani().getText().trim();

			if (derslikKodu == null || derslikKodu.trim().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Lütfen bir derslik kodu giriniz", "Hatalı giriş",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			Connection baglanti = DbBaglantisi.connect();

			String sql;
			PreparedStatement sorgu;

			if (GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim().equals("admin")) {

				sql = "SELECT * FROM derslikler WHERE derslik_kodu = ?";
				sorgu = baglanti.prepareStatement(sql);
				sorgu.setString(1, derslikKodu);

			} else {

				sql = "SELECT * FROM derslikler WHERE derslik_kodu = ? AND bolum_adi = ?";
				sorgu = baglanti.prepareStatement(sql);
				sorgu.setString(1, derslikKodu);
				sorgu.setString(2, GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim());
			}

			ResultSet sonuc = sorgu.executeQuery();

			ImageIcon masaIkon = new ImageIcon(GirisEkrani.class.getResource("/desk.png"));
			Image masaImage = masaIkon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);

			String bolumAdi;
			String derslikAdi;
			String derslikKapasitesi;
			int sutunSayisi;
			int satirSayisi;
			int siraYapisi;

			if (sonuc.next()) {

				bolumAdi = sonuc.getString("bolum_adi");
				derslikAdi = sonuc.getString("derslik_adi");
				derslikKapasitesi = sonuc.getString("derslik_kapasitesi");
				sutunSayisi = sonuc.getInt("sira_sutun");
				satirSayisi = sonuc.getInt("sira_satir");
				siraYapisi = sonuc.getInt("sira_yapisi");

			} else {

				JOptionPane.showMessageDialog(null, "Bu koda ait derslik bulunamadı", "Hatalı giriş",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			DerslikAramaEkrani ekran = DerslikAramaEkrani.getDerslikAramaEkrani();

			ekran.getDerslikBilgileriEtiketi()
					.setText("<html><b><span style='color:#800080;'>Derslik Bilgileri</span></b></html>");
			ekran.getBolumAdiEtiketi()
					.setText("<html><span style='color:#004080;'>Bölüm Adı :</span> <span style='color:#B22222;'>"
							+ bolumAdi + "</span></html>");
			ekran.getDerslikKoduEtiketi()
					.setText("<html><span style='color:#004080;'>Derslik Kodu :</span> <span style='color:#B22222;'>"
							+ derslikKodu + "</span></html>");
			ekran.getDerslikAdiEtiketi()
					.setText("<html><span style='color:#004080;'>Derslik Adı :</span> <span style='color:#B22222;'>"
							+ derslikAdi + "</span></html>");
			ekran.getDerslikKapasitesiEtiketi().setText(
					"<html><span style='color:#004080;'>Derslik Kapasitesi :</span> <span style='color:#B22222;'>"
							+ derslikKapasitesi + "</span></html>");
			ekran.getSutunSayisiEtiketi()
					.setText("<html><span style='color:#004080;'>Sütun Sayısı :</span> <span style='color:#B22222;'>"
							+ sutunSayisi + "</span></html>");
			ekran.getSatirSayisiEtiketi()
					.setText("<html><span style='color:#004080;'>Satır Sayısı :</span> <span style='color:#B22222;'>"
							+ satirSayisi + "</span></html>");
			ekran.getSiraYapisiEtiketi()
					.setText("<html><span style='color:#004080;'>Sıra Yapısı :</span> <span style='color:#B22222;'>"
							+ siraYapisi + "</span></html>");

			int masaBoyutu = 25;
			int sutunBoslugu = 10;
			int koridorBoslugu = 25;
			int satirBoslugu = 10;

			JPanel derslikPaneli = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);

					int xKordinati = 10, yKordinati = 10;

					for (int satir = 0; satir < satirSayisi; satir++) {

						for (int sutun = 0; sutun < sutunSayisi * siraYapisi; sutun++) {

							g.drawImage(masaImage, xKordinati, yKordinati, this);

							if (sutun % siraYapisi == siraYapisi - 1)
								xKordinati += masaBoyutu + koridorBoslugu;
							else
								xKordinati += masaBoyutu + sutunBoslugu;
						}

						yKordinati += masaBoyutu + satirBoslugu;
						xKordinati = 10;
					}
				}
			};

			int panelGenislik = sutunSayisi * siraYapisi * masaBoyutu + sutunSayisi * (siraYapisi - 1) * sutunBoslugu
					+ (sutunSayisi - 1) * masaBoyutu + 15;
			int panelYukseklik = satirSayisi * (masaBoyutu + satirBoslugu) + 10;

			JFrame derslikPenceresi = new JFrame();
			derslikPenceresi.setTitle("Derslik Görseli");
			derslikPenceresi.setSize(panelGenislik + 14, panelYukseklik + 37);
			derslikPenceresi.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			derslikPenceresi.setLocationRelativeTo(null);
			derslikPenceresi.setLayout(null);
			derslikPenceresi.setVisible(true);
			derslikPenceresi.getContentPane().removeAll();

			derslikPenceresi.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosed(java.awt.event.WindowEvent e) {

					DerslikAramaEkrani ekran = DerslikAramaEkrani.getDerslikAramaEkrani();
					ekran.getDerslikBilgileriEtiketi().setText("");
					ekran.getBolumAdiEtiketi().setText("");
					ekran.getDerslikKoduEtiketi().setText("");
					ekran.getDerslikAdiEtiketi().setText("");
					ekran.getDerslikKapasitesiEtiketi().setText("");
					ekran.getSutunSayisiEtiketi().setText("");
					ekran.getSatirSayisiEtiketi().setText("");
					ekran.getSiraYapisiEtiketi().setText("");
					ekran.getDerslikKoduGirmeAlani().setText("");
				}

				@Override
				public void windowClosing(java.awt.event.WindowEvent e) {
					windowClosed(e);
				}
			});

			derslikPaneli.setBounds(0, 0, panelGenislik, panelYukseklik);
			derslikPaneli.setBackground(new Color(90, 140, 120));
			derslikPaneli.setLayout(null);
			derslikPaneli.setVisible(true);
			derslikPenceresi.add(derslikPaneli);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void geriDonButonuKontrol() {

		DerslikAramaEkrani.getDerslikAramaEkrani().getDerslikAramaPaneli().setVisible(false);
		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(true);
		DerslikAramaEkrani.getDerslikAramaEkrani().getDerslikKoduGirmeAlani().setText("");
	}
}
