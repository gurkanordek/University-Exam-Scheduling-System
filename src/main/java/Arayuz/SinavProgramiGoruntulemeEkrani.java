package Arayuz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Aksiyonlar.DbBaglantisi;
import Aksiyonlar.SinavProgramiGoruntulemeAksiyonlari;

public class SinavProgramiGoruntulemeEkrani {

	private static SinavProgramiGoruntulemeEkrani sinavProgramiGoruntulemeEkrani;

	private JPanel sinavProgramiGoruntulemePaneli;
	private String[] sinavProgramiTablosuSutunlari;
	private Object[][] sinavProgramiTablosuVerileri;
	private JTable sinavProgramiTablosu;
	private JTableHeader sinavProgramiTablosuBasligi;
	private JScrollPane sinavProgramiTablosuKaydirmaCubugu;
	private JButton pdfButonu;
	private ImageIcon geriDonIkon;
	private Image geriDonImage;
	private JButton geriDonButonu;

	private SinavProgramiGoruntulemeEkrani() {

		sinavProgramiGoruntulemePaneli = new JPanel();
		sinavProgramiGoruntulemePaneli.setBounds(0, 0, 1000, 600);
		sinavProgramiGoruntulemePaneli.setBackground(new Color(90, 140, 120));
		sinavProgramiGoruntulemePaneli.setLayout(null);
		sinavProgramiGoruntulemePaneli.setVisible(false);

		sinavProgramiTablosuSutunlari = new String[] { "Tarih", "Sınav Saati", "Ders Adı", "Öğretim Elemanı",
				"Derslik" };

		DefaultTableModel model = new DefaultTableModel(sinavProgramiTablosuVerileri, sinavProgramiTablosuSutunlari);
		sinavProgramiTablosu = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		sinavProgramiTablosu.setBounds(100, 100, 800, 400);
		sinavProgramiTablosu.setBackground(new Color(150, 200, 255));
		sinavProgramiTablosu.setFont(new Font("Serif", Font.BOLD, 18));
		sinavProgramiTablosu.setGridColor(Color.black);
		sinavProgramiTablosu.setRowHeight(30);
		sinavProgramiTablosu.setSelectionBackground(Color.red);
		sinavProgramiTablosu.setForeground(Color.black);
		sinavProgramiTablosu.setOpaque(true);

		sinavProgramiTablosu.getColumnModel().getColumn(2).setPreferredWidth(150);
		sinavProgramiTablosu.getColumnModel().getColumn(3).setPreferredWidth(150);

		sinavProgramiTablosuBasligi = sinavProgramiTablosu.getTableHeader();
		sinavProgramiTablosuBasligi.setFont(new Font("Serif", Font.BOLD, 14));
		sinavProgramiTablosuBasligi.setBackground(Color.yellow);
		sinavProgramiTablosuBasligi.setForeground(Color.BLACK);
		sinavProgramiTablosuBasligi.setOpaque(true);

		sinavProgramiTablosuKaydirmaCubugu = new JScrollPane(sinavProgramiTablosu);
		sinavProgramiTablosuKaydirmaCubugu.setBounds(50, 100, 900, 450);
		sinavProgramiTablosuKaydirmaCubugu.setBackground(Color.yellow);
		sinavProgramiTablosuKaydirmaCubugu.setOpaque(true);
		sinavProgramiGoruntulemePaneli.add(sinavProgramiTablosuKaydirmaCubugu);

		pdfButonu = new JButton("PDF olarak kaydet");
		pdfButonu.setBounds(750, 50, 200, 30);
		pdfButonu.setFocusable(false);
		pdfButonu.setFont(new Font("Serif", Font.BOLD, 20));
		pdfButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiGoruntulemePaneli.add(pdfButonu);
		pdfButonu.addActionListener(e -> SinavProgramiGoruntulemeAksiyonlari.pdfOlarakKaydetButonuKontrol());

		geriDonIkon = new ImageIcon(GirisEkrani.class.getResource("/geri.png"));
		geriDonImage = geriDonIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		geriDonIkon = new ImageIcon(geriDonImage);

		geriDonButonu = new JButton("");
		geriDonButonu.setBounds(20, 20, 50, 50);
		geriDonButonu.setFocusable(false);
		geriDonButonu.setFont(new Font("Serif", Font.BOLD, 20));
		geriDonButonu.setIcon(geriDonIkon);
		geriDonButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiGoruntulemePaneli.add(geriDonButonu);
		geriDonButonu.addActionListener(e -> SinavProgramiGoruntulemeAksiyonlari.geriDonButonuKontrol());
	}

	private Object[][] sinavProgramiTablosuVerileriniGetir() {

		List<Object[]> satirlar = new ArrayList<>();

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sql = "SELECT * FROM sinav_programi ORDER BY sinav_tarihi ASC, sinav_saati ASC";
			PreparedStatement sorgu = baglanti.prepareStatement(sql);
			ResultSet sonuc = sorgu.executeQuery();
			ResultSetMetaData metaData = sonuc.getMetaData();
			int sutunSayisi = metaData.getColumnCount();

			while (sonuc.next()) {

				Object[] satir = new Object[sutunSayisi];

				for (int i = 1; i <= sutunSayisi; i++) {

					if (metaData.getColumnName(i).equalsIgnoreCase("sinav_tarihi")) {

						Date tarih = sonuc.getDate(i);
						String tarihFormatli = new SimpleDateFormat("dd.MM.yyyy").format(tarih);

						satir[i - 1] = tarihFormatli;

					} else
						satir[i - 1] = sonuc.getObject(i);
				}

				satirlar.add(satir);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		Object[][] veriler = new Object[satirlar.size()][];

		for (int i = 0; i < satirlar.size(); i++)
			veriler[i] = satirlar.get(i);

		return veriler;
	}

	public void sinavProgramiTablosunuYenile() {

		DefaultTableModel model = (DefaultTableModel) sinavProgramiTablosu.getModel();

		model.setRowCount(0);

		Object[][] yeniVeriler = this.sinavProgramiTablosuVerileriniGetir();

		for (Object[] satir : yeniVeriler)
			model.addRow(satir);

		sinavProgramiTablosu.revalidate();
		sinavProgramiTablosu.repaint();
	}

	public static SinavProgramiGoruntulemeEkrani getSinavProgramiGoruntulemeEkrani() {

		if (sinavProgramiGoruntulemeEkrani == null)
			sinavProgramiGoruntulemeEkrani = new SinavProgramiGoruntulemeEkrani();

		return sinavProgramiGoruntulemeEkrani;
	}

	public JPanel getSinavProgramiGoruntulemePaneli() {

		return this.sinavProgramiGoruntulemePaneli;
	}

	public JTable getSinavProgramiTablosu() {

		return this.sinavProgramiTablosu;
	}
}
