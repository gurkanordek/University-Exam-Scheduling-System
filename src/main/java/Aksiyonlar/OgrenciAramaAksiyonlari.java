package Aksiyonlar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import Arayuz.KullaniciEkrani;
import Arayuz.GirisEkrani;
import Arayuz.OgrenciAramaEkrani;

public class OgrenciAramaAksiyonlari {

	public static void ogrenciBilgileriniGoster() {

		try {

			String ogrenciNo = OgrenciAramaEkrani.getOgrenciAramaEkrani().getOgrenciNoGirmeAlani().getText().trim();

			if (ogrenciNo.isEmpty()) {

				JOptionPane.showMessageDialog(null, "Lütfen bir öğrenci numarası giriniz", "Hatalı giriş",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			Connection baglanti = DbBaglantisi.connect();

			String sql;
			PreparedStatement sorgu;

			if (GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim().equals("admin")) {

				sql = "SELECT o.ad_soyad, o.sinif, o.ders_kodu, d.ders_adi " + "FROM ogrenciler o "
						+ "JOIN dersler d ON o.ders_kodu = d.ders_kodu " + "WHERE o.ogrenci_no = ?";
				sorgu = baglanti.prepareStatement(sql);
				sorgu.setString(1, ogrenciNo);

			} else {

				sql = "SELECT o.ad_soyad, o.sinif, o.ders_kodu, d.ders_adi " + "FROM ogrenciler o "
						+ "JOIN dersler d ON o.ders_kodu = d.ders_kodu " + "WHERE o.ogrenci_no = ? AND o.bolum_adi = ?";
				sorgu = baglanti.prepareStatement(sql);
				sorgu.setString(1, ogrenciNo);
				sorgu.setString(2, GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim());
			}

			ResultSet sonuc = sorgu.executeQuery();

			String adSoyad = null;
			String sinif = null;
			ArrayList<String> dersler = new ArrayList<>();

			while (sonuc.next()) {

				if (adSoyad == null) {

					adSoyad = sonuc.getString("ad_soyad");
					sinif = sonuc.getString("sinif");
				}

				String dersKodu = sonuc.getString("ders_kodu");
				String dersAdi = sonuc.getString("ders_adi");
				dersler.add(dersAdi + " (" + dersKodu + ")");
			}

			JTextArea alan = OgrenciAramaEkrani.getOgrenciAramaEkrani().getOgrenciBilgisiAlani();

			if (adSoyad != null) {

				StringBuilder sb = new StringBuilder();
				sb.append("Öğrenci No: ").append(ogrenciNo).append("\n");
				sb.append("Ad Soyad: ").append(adSoyad).append("\n");
				sb.append("Sınıf: ").append(sinif).append("\n\n");
				sb.append("Ders Kodları:\n");

				for (String ders : dersler)
					sb.append(" - ").append(ders).append("\n");

				alan.setText(sb.toString());

			} else {

				JOptionPane.showMessageDialog(null, "Bu numaraya ait öğrenci kaydı bulunamadı", "Hatalı giriş",
						JOptionPane.WARNING_MESSAGE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void geriDonButonuKontrol() {

		OgrenciAramaEkrani ekran = OgrenciAramaEkrani.getOgrenciAramaEkrani();

		ekran.getOgrenciAramaPaneli().setVisible(false);
		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(true);
		ekran.getOgrenciNoGirmeAlani().setText("");
		ekran.getOgrenciBilgisiAlani().setText("");
	}
}
