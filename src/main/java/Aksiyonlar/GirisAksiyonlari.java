package Aksiyonlar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Arayuz.KullaniciEkrani;
import Arayuz.SinavProgramiOlusturmaEkrani;
import Arayuz.DersAramaEkrani;
import Arayuz.DerslikDuzenleSilEkrani;
import Arayuz.GirisEkrani;

public class GirisAksiyonlari {

	public static void girisButonuKontrol() {

		String ePosta = GirisEkrani.getGirisEkrani().getEPostaAlani().getText().trim();
		String sifre = new String(GirisEkrani.getGirisEkrani().getSifreAlani().getPassword()).trim();

		if (ePosta.isEmpty() || sifre.isEmpty()) {

			JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurunuz", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {

			Connection baglanti = DbBaglantisi.connect();

			String sql = "SELECT eposta, sifre, bolum_adi FROM kullanicilar WHERE eposta = ? AND sifre = ?";
			PreparedStatement sorgu = baglanti.prepareStatement(sql);
			sorgu.setString(1, ePosta);
			sorgu.setString(2, sifre);
			ResultSet sonuc = sorgu.executeQuery();

			if (sonuc.next()) {

				GirisEkrani.getGirisEkrani().getGirisPaneli().setVisible(false);
				KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(true);

				GirisEkrani ekran = GirisEkrani.getGirisEkrani();
				ekran.getEPostaAlani().setText("");
				ekran.getSifreAlani().setText("");

				ekran.setAktifKullaniciBolumu(sonuc.getString("bolum_adi").trim());
				DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().derslikTablosunuYenile();
				DersAramaEkrani.getDersAramaEkrani().dersTablosunuYenile();
				SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().dersTablosunuYenile();
				KullaniciEkrani.getKullaniciEkrani().butonlariKontrolEt();

				if (GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim().equals("admin"))
					KullaniciEkrani.getKullaniciEkrani().koordinatorEklemeButonuGoster();

				else {

					if (KullaniciEkrani.getKullaniciEkrani().getKoordinatorEklemeButonu() != null)
						KullaniciEkrani.getKullaniciEkrani().getKoordinatorEklemeButonu().setVisible(false);
				}

			} else {

				JOptionPane.showMessageDialog(null, "Girilen bilgiler hatalı", "Hatalı giriş",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
