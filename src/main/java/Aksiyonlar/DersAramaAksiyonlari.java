package Aksiyonlar;

import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import Arayuz.KullaniciEkrani;
import Arayuz.DersAramaEkrani;

public class DersAramaAksiyonlari {

	public static void dersiAlanlariGosterButonuKontrol() {

		int seciliSatir = DersAramaEkrani.getDersAramaEkrani().getDersTablosu().getSelectedRow();
		DefaultTableModel model = (DefaultTableModel) DersAramaEkrani.getDersAramaEkrani().getDersTablosu().getModel();

		if (seciliSatir == -1) {

			JOptionPane.showMessageDialog(null, "Lütfen önce bir ders seçiniz", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int dersKoduSutun = 0;
		String dersKodu = model.getValueAt(seciliSatir, dersKoduSutun).toString();

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sql = "SELECT ogrenci_no, ad_soyad FROM ogrenciler WHERE ders_kodu = ?";
			PreparedStatement sorgu = baglanti.prepareStatement(sql);
			sorgu.setString(1, dersKodu);
			ResultSet sonuc = sorgu.executeQuery();

			if (!sonuc.isBeforeFirst()) {

				JOptionPane.showMessageDialog(null, "Bu dersi alan öğrenci bulunamadı", "Bilgi",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			StringBuilder ogrencilerListesi = new StringBuilder();
			ogrencilerListesi.append("Ders Kodu: ").append(dersKodu).append("\n\n");
			ogrencilerListesi.append("Bu dersi alan öğrenciler:\n");
			ogrencilerListesi.append("-----------------------------\n");

			while (sonuc.next()) {

				String ogrenciNo = sonuc.getString("ogrenci_no");
				String adSoyad = sonuc.getString("ad_soyad");

				ogrencilerListesi.append(ogrenciNo).append(" - ").append(adSoyad).append("\n");
			}

			JTextArea dersBilgisiAlani = new JTextArea(ogrencilerListesi.toString());
			dersBilgisiAlani.setEditable(false);
			dersBilgisiAlani.setFont(new Font("Serif", Font.BOLD, 20));

			JScrollPane dersBilgisiAlaniKaydirmaCubugu = new JScrollPane(dersBilgisiAlani);
			dersBilgisiAlaniKaydirmaCubugu.setPreferredSize(new Dimension(400, 300));

			JOptionPane.showMessageDialog(null, dersBilgisiAlaniKaydirmaCubugu, "Dersi Alan Öğrenciler",
					JOptionPane.INFORMATION_MESSAGE);

			DersAramaEkrani.getDersAramaEkrani().getDersTablosu().clearSelection();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void geriDonButonuKontrol() {

		DersAramaEkrani.getDersAramaEkrani().getDersAramaPaneli().setVisible(false);
		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(true);
		DersAramaEkrani.getDersAramaEkrani().getDersTablosu().clearSelection();
	}
}
