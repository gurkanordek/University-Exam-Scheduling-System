package Aksiyonlar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Arayuz.KullaniciEkrani;
import Arayuz.DerslikDuzenleSilEkrani;
import Arayuz.DerslikEklemeEkrani;

public class DerslikDuzenleSilAksiyonlari {

	public static void kayitSil() {

		int[] seciliSatirlar = DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().getDerslikTablosu()
				.getSelectedRows();
		DefaultTableModel model = (DefaultTableModel) DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani()
				.getDerslikTablosu().getModel();

		if (seciliSatirlar.length == 0) {

			JOptionPane.showMessageDialog(null, "Lütfen silmek için önce derslik seçiniz", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int sutun = 1;

		try (Connection baglanti = DbBaglantisi.connect()) {

			baglanti.setAutoCommit(false);

			String sql = "DELETE FROM derslikler WHERE derslik_kodu = ?";

			try (PreparedStatement sorgu = baglanti.prepareStatement(sql)) {

				for (int i = seciliSatirlar.length - 1; i >= 0; i--) {

					Object veri = model.getValueAt(seciliSatirlar[i], sutun);
					String derslikKodu = veri.toString().trim();

					sorgu.setString(1, derslikKodu);
					sorgu.addBatch();
				}

				sorgu.executeBatch();
				baglanti.commit();
			}

			for (int i = seciliSatirlar.length - 1; i >= 0; i--)
				model.removeRow(seciliSatirlar[i]);

			JOptionPane.showMessageDialog(null, "Silme işlemi başarıyla tamamlandı", "İşlem Başarılı",
					JOptionPane.INFORMATION_MESSAGE);

			KullaniciEkrani.getKullaniciEkrani().butonlariKontrolEt();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void kayitDuzenle() {

		int seciliSatir = DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().getDerslikTablosu().getSelectedRow();
		DefaultTableModel model = (DefaultTableModel) DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani()
				.getDerslikTablosu().getModel();

		if (seciliSatir == -1) {

			JOptionPane.showMessageDialog(null, "Lütfen düzenlemek için önce derslik seçiniz", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int[] sutunlar = { 0, 1, 2, 3, 4, 5, 6 };

		Object veri;
		String stringVeri;

		for (int i = 0; i < 7; i++) {

			DerslikEklemeEkrani ekran = DerslikEklemeEkrani.getDerslikEklemeEkrani();
			veri = model.getValueAt(seciliSatir, sutunlar[i]);
			stringVeri = veri.toString().trim();

			switch (i) {

			case 0:
				ekran.getBolumAdiSecimi().setSelectedItem(veri);
				break;

			case 1:
				ekran.getDerslikKoduAlani().setText(stringVeri);
				break;

			case 2:
				ekran.getDerslikAdiAlani().setText(stringVeri);
				break;

			case 3:
				ekran.getDerslikKapasitesiAlani().setText(stringVeri);
				break;

			case 4:
				ekran.getSutunSayisiAlani().setText(stringVeri);
				break;

			case 5:
				ekran.getSatirSayisiAlani().setText(stringVeri);
				break;

			case 6:
				ekran.getSiraYapisiAlani().setText(stringVeri);
				break;

			}
		}

		DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().setDerslikTablosuYenilemeBayragi(1);
		DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().getDerslikDuzenleSilPaneli().setVisible(false);
		DerslikEklemeEkrani.getDerslikEklemeEkrani().getDerslikEklemePaneli().setVisible(true);
		DerslikEklemeEkrani.getDerslikEklemeEkrani().getDerslikKoduAlani().setEnabled(false);
	}

	public static void geriDonButonuKontrol() {

		DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().getDerslikDuzenleSilPaneli().setVisible(false);
		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(true);
		DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().getDerslikTablosu().clearSelection();
	}
}
