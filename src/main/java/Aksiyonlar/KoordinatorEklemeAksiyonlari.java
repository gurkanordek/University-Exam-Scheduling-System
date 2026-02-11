package Aksiyonlar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Arayuz.KoordinatorEklemeEkrani;
import Arayuz.KullaniciEkrani;

public class KoordinatorEklemeAksiyonlari {

	public static void onaylaButonuKontrol() {

		KoordinatorEklemeEkrani ekran = KoordinatorEklemeEkrani.getKoordinatorEklemeEkrani();

		String adSoyad = ekran.getAdSoyadAlani().getText();
		String ePosta = ekran.getePostaAlani().getText();
		String sifre = ekran.getSifreAlani().getText();
		String bolumAdi = ekran.getBolumAdiSecimi().getSelectedItem().toString();

		if (adSoyad.isEmpty() || ePosta.isEmpty() || sifre.isEmpty() || bolumAdi.equals("Seçiniz")) {

			JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurunuz", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {

			Connection baglanti = DbBaglantisi.connect();

			String sql = "INSERT INTO kullanicilar (ad_soyad, eposta, sifre, bolum_adi) VALUES (?, ?, ?, ?)";
			PreparedStatement sorgu = baglanti.prepareStatement(sql);
			sorgu.setString(1, adSoyad);
			sorgu.setString(2, ePosta);
			sorgu.setString(3, sifre);
			sorgu.setString(4, bolumAdi);
			sorgu.executeUpdate();

			JOptionPane.showMessageDialog(null, "Kullanıcı başarıyla eklendi", "İşlem başarılı",
					JOptionPane.INFORMATION_MESSAGE);

			KoordinatorEklemeEkrani.getKoordinatorEklemeEkrani().KoordinatorEklemeEkraniTemizle();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void geriDonButonuKontrol() {

		KoordinatorEklemeEkrani.getKoordinatorEklemeEkrani().getKoordinatorEklemePaneli().setVisible(false);
		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(true);
		KoordinatorEklemeEkrani.getKoordinatorEklemeEkrani().KoordinatorEklemeEkraniTemizle();
	}
}
