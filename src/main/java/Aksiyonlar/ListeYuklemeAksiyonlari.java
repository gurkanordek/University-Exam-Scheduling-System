package Aksiyonlar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Arayuz.DersAramaEkrani;
import Arayuz.KullaniciEkrani;
import Arayuz.ListeYuklemeEkrani;
import Arayuz.SinavProgramiOlusturmaEkrani;

public class ListeYuklemeAksiyonlari {

	private static File dersDosyasi;
	private static File ogrenciDosyasi;

	public static void listeYuklemeButonlariKontrol(String listeTuru) {

		JFileChooser dosyaSecici = new JFileChooser("C:\\Users\\Gürkan\\OneDrive\\Masaüstü");
		FileNameExtensionFilter excelFiltresi = new FileNameExtensionFilter("Excel Dosyaları", "xls", "xlsx");
		dosyaSecici.addChoosableFileFilter(excelFiltresi);
		dosyaSecici.setAcceptAllFileFilterUsed(false);
		dosyaSecici.setDialogTitle("Dosya gezgini");
		dosyaSecici.setDialogType(JFileChooser.OPEN_DIALOG);
		int sonuc = dosyaSecici.showDialog(null, "Seç");

		if (sonuc == JFileChooser.APPROVE_OPTION) {

			if (listeTuru.equals("dersListesi")) {

				dersDosyasi = dosyaSecici.getSelectedFile();
				ListeYuklemeEkrani.getListeYuklemeEkrani().getDersListesiYuklemeButonu().setText(dersDosyasi.getPath());
			}

			else if (listeTuru.equals("ogrenciListesi")) {

				ogrenciDosyasi = dosyaSecici.getSelectedFile();
				ListeYuklemeEkrani.getListeYuklemeEkrani().getOgrenciListesiYuklemeButonu()
						.setText(ogrenciDosyasi.getPath());
			}
		}
	}

	private static void dersListesiniParseEt(Connection baglanti) throws IOException, SQLException {

		String sqlSilDersler = "DELETE FROM dersler WHERE bolum_adi = ?";
		String sqlSilSinavSuresi = "DELETE FROM sinav_sureleri WHERE bolum_adi = ?";

		try (PreparedStatement sorgu1 = baglanti.prepareStatement(sqlSilDersler);
				PreparedStatement sorguSilSinav = baglanti.prepareStatement(sqlSilSinavSuresi)) {

			String bolumAdi = ListeYuklemeEkrani.getListeYuklemeEkrani().getBolumAdiSecimi().getSelectedItem()
					.toString();

			sorgu1.setString(1, bolumAdi);
			sorgu1.executeUpdate();

			sorguSilSinav.setString(1, bolumAdi);
			sorguSilSinav.executeUpdate();
		}

		try (FileInputStream inputstream = new FileInputStream(dersDosyasi.getPath());
				XSSFWorkbook workbook = new XSSFWorkbook(inputstream)) {

			XSSFSheet sheet = workbook.getSheetAt(0);
			int satirSayisi = sheet.getLastRowNum();
			int sutunSayisi = sheet.getRow(0).getLastCellNum();
			String aktifSinif = "";
			String aktifYapi = "zorunlu";

			String sqlEkleDersler = "INSERT INTO dersler (ders_kodu, ders_adi, ders_hocasi, dersin_yapisi, sinif_seviyesi, bolum_adi) VALUES (?, ?, ?, ?, ?, ?)";
			String sqlEkleSinavSureleri = "INSERT INTO sinav_sureleri (ders_kodu, sinav_suresi, bolum_adi) VALUES (?, ?, ?)";

			try (PreparedStatement sorgu2 = baglanti.prepareStatement(sqlEkleDersler);
					PreparedStatement sorgu3 = baglanti.prepareStatement(sqlEkleSinavSureleri)) {

				int islemGrubuBoyutu = 500;
				int sayac = 0;

				String bolumAdi = ListeYuklemeEkrani.getListeYuklemeEkrani().getBolumAdiSecimi().getSelectedItem()
						.toString();

				for (int s = 0; s <= satirSayisi; s++) {

					XSSFRow satir = sheet.getRow(s);

					if (satir == null)
						continue;

					boolean atla = false;

					for (int h = 0; h < sutunSayisi; h++) {

						XSSFCell hucre = satir.getCell(h);

						if (hucre == null)
							continue;

						String veri = hucre.getStringCellValue().trim();

						if (veri.isEmpty() && h == 0) {

							atla = true;
							break;
						}

						if (veri.toLowerCase().contains("seçimlik") || veri.toLowerCase().contains("seçmeli")) {

							aktifYapi = veri;
							atla = true;
						}

						if (veri.toLowerCase().contains("sınıf")) {

							aktifSinif = veri;
							aktifYapi = "zorunlu";
							atla = true;
						}

						if (veri.equalsIgnoreCase("ders kodu") || veri.equalsIgnoreCase("dersin adı")
								|| veri.equalsIgnoreCase("dersi veren öğr. elemanı"))
							atla = true;
					}

					if (!atla) {

						String dersKodu = satir.getCell(0).getStringCellValue();
						String dersAdi = satir.getCell(1).getStringCellValue();
						String dersHocasi = satir.getCell(2).getStringCellValue();

						sorgu2.setString(1, dersKodu);
						sorgu2.setString(2, dersAdi);
						sorgu2.setString(3, dersHocasi);
						sorgu2.setString(4, aktifYapi);
						sorgu2.setString(5, aktifSinif);
						sorgu2.setString(6, bolumAdi);
						sorgu2.addBatch();

						sorgu3.setString(1, dersKodu);
						sorgu3.setInt(2, 75);
						sorgu3.setString(3, bolumAdi);
						sorgu3.addBatch();

						if (++sayac % islemGrubuBoyutu == 0) {

							sorgu2.executeBatch();
							sorgu3.executeBatch();
						}
					}
				}

				sorgu2.executeBatch();
				sorgu3.executeBatch();
			}
		}
	}

	private static void ogrenciListesiniParseEt(Connection baglanti) throws IOException, SQLException {

		String sql1 = "DELETE FROM Ogrenciler WHERE bolum_adi = ?";

		try (PreparedStatement sorgu1 = baglanti.prepareStatement(sql1)) {

			sorgu1.setString(1,
					ListeYuklemeEkrani.getListeYuklemeEkrani().getBolumAdiSecimi().getSelectedItem().toString());
			sorgu1.executeUpdate();
		}

		try (FileInputStream inputstream = new FileInputStream(ogrenciDosyasi.getPath());
				XSSFWorkbook workbook = new XSSFWorkbook(inputstream)) {

			XSSFSheet sheet = workbook.getSheetAt(0);
			int satirSayisi = sheet.getLastRowNum();

			String sql2 = "INSERT INTO Ogrenciler (ogrenci_no, ad_soyad, sinif, ders_kodu, bolum_adi) VALUES (?, ?, ?, ?, ?)";

			try (PreparedStatement sorgu2 = baglanti.prepareStatement(sql2)) {

				int islemGrubuBoyutu = 500;
				int sayac = 0;

				for (int s = 1; s <= satirSayisi; s++) {

					XSSFRow satir = sheet.getRow(s);

					if (satir == null || satir.getCell(0) == null)
						continue;

					sorgu2.setString(1, satir.getCell(0).getStringCellValue().trim());
					sorgu2.setString(2, satir.getCell(1).getStringCellValue().trim());
					sorgu2.setString(3, satir.getCell(2).getStringCellValue().trim());
					sorgu2.setString(4, satir.getCell(3).getStringCellValue().trim());
					sorgu2.setString(5, ListeYuklemeEkrani.getListeYuklemeEkrani().getBolumAdiSecimi().getSelectedItem()
							.toString());
					sorgu2.addBatch();

					if (++sayac % islemGrubuBoyutu == 0)
						sorgu2.executeBatch();
				}

				sorgu2.executeBatch();
			}
		}
	}

	public static void onaylaButonuKontrol() {

		if (dersDosyasi == null && ogrenciDosyasi == null) {

			JOptionPane.showMessageDialog(null, "Lütfen önce dosyaları seçiniz", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try (Connection baglanti = DbBaglantisi.connect()) {

			baglanti.setAutoCommit(false);

			if (dersDosyasi != null && ogrenciDosyasi == null) {

				dersListesiniParseEt(baglanti);
				JOptionPane.showMessageDialog(null, "Ders listesi başarıyla yüklendi", "İşlem başarılı",
						JOptionPane.INFORMATION_MESSAGE);

			} else if (dersDosyasi == null && ogrenciDosyasi != null) {

				ogrenciListesiniParseEt(baglanti);
				JOptionPane.showMessageDialog(null, "Öğrenci listesi başarıyla yüklendi", "İşlem başarılı",
						JOptionPane.INFORMATION_MESSAGE);

			} else {

				dersListesiniParseEt(baglanti);
				ogrenciListesiniParseEt(baglanti);
				JOptionPane.showMessageDialog(null, "Ders listesi ve öğrenci listesi başarıyla yüklendi",
						"İşlem başarılı", JOptionPane.INFORMATION_MESSAGE);
			}

			baglanti.commit();

			ListeYuklemeEkrani.getListeYuklemeEkrani().getDersListesiYuklemeButonu().setText("Dosya Seç");
			ListeYuklemeEkrani.getListeYuklemeEkrani().getOgrenciListesiYuklemeButonu().setText("Dosya Seç");
			DersAramaEkrani.getDersAramaEkrani().dersTablosunuYenile();
			SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().dersTablosunuYenile();
			KullaniciEkrani.getKullaniciEkrani().butonlariKontrolEt();
			dersDosyasi = null;
			ogrenciDosyasi = null;

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static void geriDonButonuKontrol() {

		ListeYuklemeEkrani ekran = ListeYuklemeEkrani.getListeYuklemeEkrani();

		ekran.getListeYuklemePaneli().setVisible(false);
		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(true);
		ekran.getDersListesiYuklemeButonu().setText("Dosya Seç");
		ekran.getOgrenciListesiYuklemeButonu().setText("Dosya Seç");
	}
}
