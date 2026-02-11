package Aksiyonlar;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Arayuz.GirisEkrani;
import Arayuz.SinavProgramiGoruntulemeEkrani;
import Arayuz.SinavProgramiOlusturmaEkrani;

public class SinavProgramiGoruntulemeAksiyonlari {

	public static void pdfOlarakKaydetButonuKontrol() {

		try {
			JTable sinavProgramiTablosu = SinavProgramiGoruntulemeEkrani.getSinavProgramiGoruntulemeEkrani()
					.getSinavProgramiTablosu();

			javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
			fileChooser.setDialogTitle("PDF Olarak Kaydet");
			fileChooser.setSelectedFile(new java.io.File("sinav_programi.pdf"));
			int userSelection = fileChooser.showSaveDialog(null);

			if (userSelection != javax.swing.JFileChooser.APPROVE_OPTION) {

				return;
			}

			java.io.File fileToSave = fileChooser.getSelectedFile();

			Document document = new Document(PageSize.A4, 20, 20, 20, 20);
			PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
			document.open();

			BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/times.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			com.itextpdf.text.Font tabloBaslikFont = new com.itextpdf.text.Font(baseFont, 16,
					com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
			com.itextpdf.text.Font sutunBaslikFont = new com.itextpdf.text.Font(baseFont, 12,
					com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
			com.itextpdf.text.Font hucreFont = new com.itextpdf.text.Font(baseFont, 10, com.itextpdf.text.Font.BOLD,
					BaseColor.BLACK);

			String bolumAdi = GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu();
			String sinavTuru = null;
			JRadioButton[] sinavTuruSecimi = SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani()
					.getSinavTuruSecimi();

			for (JRadioButton r : sinavTuruSecimi) {
				if (r.isSelected()) {
					sinavTuru = r.getText();
					break;
				}
			}

			Paragraph tabloBasligi = new Paragraph(
					bolumAdi.toUpperCase() + " BÖLÜMÜ " + sinavTuru.toUpperCase() + " SINAV PROGRAMI", tabloBaslikFont);
			tabloBasligi.setAlignment(Element.ALIGN_CENTER);
			tabloBasligi.setSpacingAfter(20);
			document.add(tabloBasligi);

			PdfPTable tablo = new PdfPTable(sinavProgramiTablosu.getColumnCount());
			tablo.setWidthPercentage(100);
			float[] columnWidths = { 10f, 15f, 30f, 30f, 15f };
			tablo.setWidths(columnWidths);

			for (int i = 0; i < sinavProgramiTablosu.getColumnCount(); i++) {
				PdfPCell hucre = new PdfPCell(new Phrase(sinavProgramiTablosu.getColumnName(i), sutunBaslikFont));
				hucre.setHorizontalAlignment(Element.ALIGN_CENTER);
				hucre.setVerticalAlignment(Element.ALIGN_MIDDLE);
				hucre.setBackgroundColor(new BaseColor(255, 140, 0));
				tablo.addCell(hucre);
			}

			Map<String, List<Object[]>> tarihGruplari = new LinkedHashMap<>();

			for (int i = 0; i < sinavProgramiTablosu.getRowCount(); i++) {

				String tarih = sinavProgramiTablosu.getValueAt(i, 0).toString();
				Object[] satir = new Object[sinavProgramiTablosu.getColumnCount()];
				for (int j = 0; j < sinavProgramiTablosu.getColumnCount(); j++)
					satir[j] = sinavProgramiTablosu.getValueAt(i, j);
				tarihGruplari.computeIfAbsent(tarih, k -> new ArrayList<>()).add(satir);
			}

			for (Map.Entry<String, List<Object[]>> entry : tarihGruplari.entrySet()) {
				String tarih = entry.getKey();
				List<Object[]> satirlar = entry.getValue();
				boolean ilkSatir = true;

				for (Object[] satir : satirlar) {

					for (int j = 0; j < satir.length; j++) {

						PdfPCell hucre;

						if (j == 0) {

							if (ilkSatir) {

								hucre = new PdfPCell(new Phrase(tarih, hucreFont));
								hucre.setRowspan(satirlar.size());
								hucre.setHorizontalAlignment(Element.ALIGN_CENTER);
								tablo.addCell(hucre);
							}

						} else {

							hucre = new PdfPCell(new Phrase(satir[j] != null ? satir[j].toString() : "", hucreFont));
							hucre.setHorizontalAlignment(Element.ALIGN_CENTER);
							tablo.addCell(hucre);
						}
					}

					ilkSatir = false;
				}
			}

			document.add(tablo);
			document.close();

			JOptionPane.showMessageDialog(null, "Pdf başarıyla kaydedildi", "İşlem Başarılı",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void geriDonButonuKontrol() {

		SinavProgramiGoruntulemeEkrani.getSinavProgramiGoruntulemeEkrani().getSinavProgramiGoruntulemePaneli()
				.setVisible(false);
		SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().getSinavProgramiOlusturmaPaneli()
				.setVisible(true);
	}
}
