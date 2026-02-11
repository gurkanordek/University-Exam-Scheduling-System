package Arayuz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
import Aksiyonlar.DerslikDuzenleSilAksiyonlari;

public class DerslikDuzenleSilEkrani {

	private static DerslikDuzenleSilEkrani derslikDuzenleSilEkrani;

	private int derslikTablosuYenilemeBayragi = 0;
	private JPanel derslikDuzenleSilPaneli;
	private String[] derslikTablosuSutunlari;
	private Object[][] derslikTablosuVerileri;
	private JTableHeader derslikTablosuBasligi;
	private JTable derslikTablosu;
	private JScrollPane derslikTablosuKaydirmaCubugu;
	private ImageIcon silIkon;
	private Image silImage;
	private JButton derslikSilButonu;
	private ImageIcon duzenleIkon;
	private Image duzenleImage;
	private JButton derslikDuzenleButonu;
	private ImageIcon geriDonIkon;
	private Image geriDonImage;
	private JButton geriDonButonu;

	private DerslikDuzenleSilEkrani() {

		derslikDuzenleSilPaneli = new JPanel();
		derslikDuzenleSilPaneli.setBounds(0, 0, 1000, 600);
		derslikDuzenleSilPaneli.setBackground(new Color(90, 140, 120));
		derslikDuzenleSilPaneli.setLayout(null);
		derslikDuzenleSilPaneli.setVisible(false);

		derslikTablosuSutunlari = new String[] { "Bölüm Adı", "Derslik Kodu", "Derslik Adı", "Derslik Kapasitesi",
				"Sütun Sayısı", "Satır Sayısı", "Sıra Yapısı" };

		DefaultTableModel model = new DefaultTableModel(derslikTablosuVerileri, derslikTablosuSutunlari);
		derslikTablosu = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		derslikTablosu.setBounds(100, 100, 800, 400);
		derslikTablosu.setBackground(new Color(150, 200, 255));
		derslikTablosu.setFont(new Font("Serif", Font.BOLD, 18));
		derslikTablosu.setGridColor(Color.black);
		derslikTablosu.setRowHeight(30);
		derslikTablosu.setSelectionBackground(Color.red);
		derslikTablosu.setForeground(Color.black);
		derslikTablosu.setOpaque(true);

		derslikTablosu.getColumnModel().getColumn(0).setPreferredWidth(200);
		derslikTablosu.getColumnModel().getColumn(2).setPreferredWidth(100);
		derslikTablosu.getColumnModel().getColumn(3).setPreferredWidth(110);

		derslikTablosuBasligi = derslikTablosu.getTableHeader();
		derslikTablosuBasligi.setFont(new Font("Serif", Font.BOLD, 14));
		derslikTablosuBasligi.setBackground(Color.yellow);
		derslikTablosuBasligi.setForeground(Color.BLACK);
		derslikTablosuBasligi.setOpaque(true);

		derslikTablosuKaydirmaCubugu = new JScrollPane(derslikTablosu);
		derslikTablosuKaydirmaCubugu.setBounds(100, 100, 800, 400);
		derslikTablosuKaydirmaCubugu.setBackground(Color.yellow);
		derslikTablosuKaydirmaCubugu.setOpaque(true);
		derslikDuzenleSilPaneli.add(derslikTablosuKaydirmaCubugu);

		silIkon = new ImageIcon(GirisEkrani.class.getResource("/bin.png"));
		silImage = silIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		silIkon = new ImageIcon(silImage);

		derslikSilButonu = new JButton("");
		derslikSilButonu.setBounds(850, 20, 50, 50);
		derslikSilButonu.setFocusable(false);
		derslikSilButonu.setFont(new Font("Serif", Font.BOLD, 20));
		derslikSilButonu.setIcon(silIkon);
		derslikSilButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		derslikDuzenleSilPaneli.add(derslikSilButonu);
		derslikSilButonu.addActionListener(e -> DerslikDuzenleSilAksiyonlari.kayitSil());

		duzenleIkon = new ImageIcon(GirisEkrani.class.getResource("/edit.png"));
		duzenleImage = duzenleIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		duzenleIkon = new ImageIcon(duzenleImage);

		derslikDuzenleButonu = new JButton("");
		derslikDuzenleButonu.setBounds(780, 20, 50, 50);
		derslikDuzenleButonu.setFocusable(false);
		derslikDuzenleButonu.setFont(new Font("Serif", Font.BOLD, 20));
		derslikDuzenleButonu.setIcon(duzenleIkon);
		derslikDuzenleButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		derslikDuzenleSilPaneli.add(derslikDuzenleButonu);
		derslikDuzenleButonu.addActionListener(e -> DerslikDuzenleSilAksiyonlari.kayitDuzenle());

		geriDonIkon = new ImageIcon(GirisEkrani.class.getResource("/geri.png"));
		geriDonImage = geriDonIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		geriDonIkon = new ImageIcon(geriDonImage);

		geriDonButonu = new JButton("");
		geriDonButonu.setBounds(20, 20, 50, 50);
		geriDonButonu.setFocusable(false);
		geriDonButonu.setFont(new Font("Serif", Font.BOLD, 20));
		geriDonButonu.setIcon(geriDonIkon);
		geriDonButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		derslikDuzenleSilPaneli.add(geriDonButonu);
		geriDonButonu.addActionListener(e -> DerslikDuzenleSilAksiyonlari.geriDonButonuKontrol());
	}

	private Object[][] derslikTablosuVerileriniGetir() {

		List<Object[]> satirlar = new ArrayList<>();

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sql;
			PreparedStatement sorgu;

			if (GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim().equals("admin")) {

				sql = "SELECT * FROM derslikler";
				sorgu = baglanti.prepareStatement(sql);

			} else {

				sql = "SELECT * FROM derslikler WHERE bolum_adi = ?";
				sorgu = baglanti.prepareStatement(sql);
				sorgu.setString(1, GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim());
			}

			ResultSet sonuc = sorgu.executeQuery();
			ResultSetMetaData metaData = sonuc.getMetaData();
			int sutunSayisi = metaData.getColumnCount();

			while (sonuc.next()) {

				Object[] satir = new Object[sutunSayisi];

				for (int i = 1; i <= sutunSayisi; i++)
					satir[i - 1] = sonuc.getObject(i);

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

	public void derslikTablosunuYenile() {

		DefaultTableModel model = (DefaultTableModel) derslikTablosu.getModel();

		model.setRowCount(0);

		Object[][] yeniVeriler = this.derslikTablosuVerileriniGetir();

		for (Object[] satir : yeniVeriler)
			model.addRow(satir);

		derslikTablosu.revalidate();
		derslikTablosu.repaint();
	}

	public static DerslikDuzenleSilEkrani getDerslikDuzenleSilEkrani() {

		if (derslikDuzenleSilEkrani == null)
			derslikDuzenleSilEkrani = new DerslikDuzenleSilEkrani();

		return derslikDuzenleSilEkrani;
	}

	public JPanel getDerslikDuzenleSilPaneli() {

		return this.derslikDuzenleSilPaneli;
	}

	public JTable getDerslikTablosu() {

		return this.derslikTablosu;
	}

	public int getDerslikTablosuYenilemeBayragi() {

		return this.derslikTablosuYenilemeBayragi;
	}

	public void setDerslikTablosuYenilemeBayragi(int i) {

		this.derslikTablosuYenilemeBayragi = i;
	}
}
