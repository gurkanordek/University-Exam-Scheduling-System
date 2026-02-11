package Aksiyonlar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Arayuz.KullaniciEkrani;
import Arayuz.DerslikDuzenleSilEkrani;
import Arayuz.DerslikEklemeEkrani;

public class DerslikEklemeAksiyonlari {

	public static void onaylaButonuKontrol() {

		DerslikEklemeEkrani ekran = DerslikEklemeEkrani.getDerslikEklemeEkrani();

		String derslikKodu = ekran.getDerslikKoduAlani().getText();
		String derslikAdi = ekran.getDerslikAdiAlani().getText();
		String derslikKapasitesiString = ekran.getDerslikKapasitesiAlani().getText();
		String sutunSayisiString = ekran.getSutunSayisiAlani().getText();
		String satirSayisiString = ekran.getSatirSayisiAlani().getText();
		String siraYapisiString = ekran.getSiraYapisiAlani().getText();
		String bolumAdi = ekran.getBolumAdiSecimi().getSelectedItem().toString();

		if (derslikKodu.isEmpty() || derslikAdi.isEmpty() || derslikKapasitesiString.isEmpty()
				|| sutunSayisiString.isEmpty() || satirSayisiString.isEmpty() || bolumAdi.isEmpty()
				|| siraYapisiString.isEmpty() || bolumAdi.equals("Seçiniz")) {

			JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurunuz", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int derslikKapasitesi, sutunSayisi, satirSayisi, siraYapisi;

		try {

			derslikKapasitesi = Integer.parseInt(derslikKapasitesiString);
			sutunSayisi = Integer.parseInt(sutunSayisiString);
			satirSayisi = Integer.parseInt(satirSayisiString);
			siraYapisi = Integer.parseInt(siraYapisiString);

		} catch (NumberFormatException ex) {

			JOptionPane.showMessageDialog(null, "Lütfen sayı olarak belirtilen alanlara geçerli bir giriş yapınız",
					"Hatalı giriş", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {

			Connection baglanti = DbBaglantisi.connect();

			String sql1 = "SELECT derslik_kodu FROM derslikler WHERE derslik_kodu = ?";
			PreparedStatement sorgu1 = baglanti.prepareStatement(sql1);
			sorgu1.setString(1, derslikKodu);
			ResultSet sonuc = sorgu1.executeQuery();

			if (DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().getDerslikTablosuYenilemeBayragi() == 1) {

				String sql2 = "UPDATE derslikler SET bolum_adi = ?, derslik_kodu = ?, derslik_adi = ?, derslik_kapasitesi = ?, sira_sutun = ?, sira_satir = ?, sira_yapisi = ? WHERE derslik_kodu = ?";
				PreparedStatement sorgu2 = baglanti.prepareStatement(sql2);
				sorgu2.setString(1, bolumAdi);
				sorgu2.setString(2, derslikKodu);
				sorgu2.setString(3, derslikAdi);
				sorgu2.setInt(4, derslikKapasitesi);
				sorgu2.setInt(5, sutunSayisi);
				sorgu2.setInt(6, satirSayisi);
				sorgu2.setInt(7, siraYapisi);
				sorgu2.setString(8, derslikKodu);
				sorgu2.executeUpdate();

				DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().setDerslikTablosuYenilemeBayragi(0);

				JOptionPane.showMessageDialog(null, "Derslik güncelleme işlemi başarıyla tamamlandı", "İşlem başarılı",
						JOptionPane.INFORMATION_MESSAGE);

				DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().derslikTablosunuYenile();
				DerslikEklemeEkrani.getDerslikEklemeEkrani().derslikEklemeEkraniTemizle();
				DerslikEklemeEkrani.getDerslikEklemeEkrani().getDerslikEklemePaneli().setVisible(false);
				DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().getDerslikDuzenleSilPaneli().setVisible(true);
				DerslikEklemeEkrani.getDerslikEklemeEkrani().getDerslikKoduAlani().setEnabled(true);

				return;

			} else if (sonuc.next()) {

				JOptionPane.showMessageDialog(null, "Bu derslik kodu daha önce kullanıldı", "Hatalı giriş",
						JOptionPane.WARNING_MESSAGE);
				return;

			} else {

				String sql3 = "INSERT INTO derslikler (bolum_adi, derslik_kodu, derslik_adi, derslik_kapasitesi, sira_sutun, sira_satir, sira_yapisi) VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement sorgu3 = baglanti.prepareStatement(sql3);
				sorgu3.setString(1, bolumAdi);
				sorgu3.setString(2, derslikKodu);
				sorgu3.setString(3, derslikAdi);
				sorgu3.setInt(4, derslikKapasitesi);
				sorgu3.setInt(5, sutunSayisi);
				sorgu3.setInt(6, satirSayisi);
				sorgu3.setInt(7, siraYapisi);
				sorgu3.executeUpdate();

				KullaniciEkrani.getKullaniciEkrani().butonlariKontrolEt();

				JOptionPane.showMessageDialog(null, "Derslik başarıyla eklendi", "İşlem başarılı",
						JOptionPane.INFORMATION_MESSAGE);

				DerslikEklemeEkrani.getDerslikEklemeEkrani().derslikEklemeEkraniTemizle();

				DefaultTableModel model = (DefaultTableModel) DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani()
						.getDerslikTablosu().getModel();
				model.addRow(new Object[] { bolumAdi, derslikKodu, derslikAdi, derslikKapasitesi, sutunSayisi,
						satirSayisi, siraYapisi });
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void geriDonButonuKontrol() {

		DerslikEklemeEkrani.getDerslikEklemeEkrani().getDerslikEklemePaneli().setVisible(false);
		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(true);
		DerslikEklemeEkrani.getDerslikEklemeEkrani().derslikEklemeEkraniTemizle();
		DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().getDerslikTablosu().clearSelection();
		DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().setDerslikTablosuYenilemeBayragi(0);
	}
}
