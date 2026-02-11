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
import Aksiyonlar.DersAramaAksiyonlari;

public class DersAramaEkrani {

	public static DersAramaEkrani dersAramaEkrani;

	private JPanel dersAramaPaneli;
	private String[] dersTablosuSutunlari;
	private Object[][] dersTablosuVerileri;
	private JTableHeader dersTablosuBasligi;
	private JTable dersTablosu;
	private JScrollPane dersTablosuKaydirmaCubugu;
	private JButton dersiAlanlariGosterButonu;
	private ImageIcon geriDonIkon;
	private Image geriDonImage;
	private JButton geriDonButonu;

	private DersAramaEkrani() {

		dersAramaPaneli = new JPanel();
		dersAramaPaneli.setBounds(0, 0, 1000, 600);
		dersAramaPaneli.setBackground(new Color(90, 140, 120));
		dersAramaPaneli.setLayout(null);
		dersAramaPaneli.setVisible(false);

		dersTablosuSutunlari = new String[] { "Ders Kodu", "Ders Adı" };

		DefaultTableModel model = new DefaultTableModel(dersTablosuVerileri, dersTablosuSutunlari);
		dersTablosu = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		dersTablosu.setBounds(100, 100, 800, 400);
		dersTablosu.setBackground(new Color(150, 200, 255));
		dersTablosu.setFont(new Font("Serif", Font.BOLD, 18));
		dersTablosu.setGridColor(Color.black);
		dersTablosu.setRowHeight(30);
		dersTablosu.setSelectionBackground(Color.red);
		dersTablosu.setForeground(Color.black);
		dersTablosu.setOpaque(true);

		dersTablosu.getColumnModel().getColumn(1).setPreferredWidth(350);

		dersTablosuBasligi = dersTablosu.getTableHeader();
		dersTablosuBasligi.setFont(new Font("Serif", Font.BOLD, 18));
		dersTablosuBasligi.setBackground(Color.yellow);
		dersTablosuBasligi.setForeground(Color.BLACK);
		dersTablosuBasligi.setOpaque(true);

		dersTablosuKaydirmaCubugu = new JScrollPane(dersTablosu);
		dersTablosuKaydirmaCubugu.setBounds(200, 120, 600, 400);
		dersTablosuKaydirmaCubugu.setBackground(Color.yellow);
		dersTablosuKaydirmaCubugu.setOpaque(true);
		dersAramaPaneli.add(dersTablosuKaydirmaCubugu);

		dersiAlanlariGosterButonu = new JButton("Dersi Alanları Göster");
		dersiAlanlariGosterButonu.setBounds(400, 40, 200, 50);
		dersiAlanlariGosterButonu.setFocusable(false);
		dersiAlanlariGosterButonu.setFont(new Font("Serif", Font.BOLD, 20));
		dersiAlanlariGosterButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		dersAramaPaneli.add(dersiAlanlariGosterButonu);
		dersiAlanlariGosterButonu.addActionListener(e -> DersAramaAksiyonlari.dersiAlanlariGosterButonuKontrol());

		geriDonIkon = new ImageIcon(GirisEkrani.class.getResource("/geri.png"));
		geriDonImage = geriDonIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		geriDonIkon = new ImageIcon(geriDonImage);

		geriDonButonu = new JButton("");
		geriDonButonu.setBounds(20, 20, 50, 50);
		geriDonButonu.setFocusable(false);
		geriDonButonu.setFont(new Font("Serif", Font.BOLD, 20));
		geriDonButonu.setIcon(geriDonIkon);
		geriDonButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		dersAramaPaneli.add(geriDonButonu);
		geriDonButonu.addActionListener(e -> DersAramaAksiyonlari.geriDonButonuKontrol());
	}

	private Object[][] dersTablosuVerileriniGetir() {

		List<Object[]> satirlar = new ArrayList<>();

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sql;
			PreparedStatement sorgu;

			if (GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim().equals("admin")) {

				sql = "SELECT ders_kodu, ders_adi FROM dersler";
				sorgu = baglanti.prepareStatement(sql);

			} else {

				sql = "SELECT ders_kodu, ders_adi FROM dersler WHERE bolum_adi = ?";
				sorgu = baglanti.prepareStatement(sql);
				sorgu.setString(1, GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu());
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

	public void dersTablosunuYenile() {

		DefaultTableModel model = (DefaultTableModel) dersTablosu.getModel();

		model.setRowCount(0);

		Object[][] yeniVeriler = this.dersTablosuVerileriniGetir();

		for (Object[] satir : yeniVeriler)
			model.addRow(satir);

		dersTablosu.revalidate();
		dersTablosu.repaint();
	}

	public static DersAramaEkrani getDersAramaEkrani() {

		if (dersAramaEkrani == null)
			dersAramaEkrani = new DersAramaEkrani();

		return dersAramaEkrani;
	}

	public JPanel getDersAramaPaneli() {

		return this.dersAramaPaneli;
	}

	public JTable getDersTablosu() {

		return this.dersTablosu;
	}
}
