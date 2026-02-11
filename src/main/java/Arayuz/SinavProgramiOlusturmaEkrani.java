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
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;

import Aksiyonlar.DbBaglantisi;
import Aksiyonlar.SinavProgramiOlusturmaAksiyonlari;

public class SinavProgramiOlusturmaEkrani {

	public static SinavProgramiOlusturmaEkrani sinavProgramiOlusturmaEkrani;

	private JPanel sinavProgramiOlusturmaPaneli;
	private String[] dersTablosuSutunlari;
	private Object[][] dersTablosuVerileri;
	private JTableHeader dersTablosuBasligi;
	private JTable dersTablosu;
	private JScrollPane dersTablosuKaydirmaCubugu;
	private ImageIcon carpiIkon;
	private Image carpiImage;
	private JButton dersCikarButonu;
	private ImageIcon kumSaatiIkon;
	private Image kumSaatiImage;
	private JButton sureDegistirmeButonu;
	private JLabel cikarilacakGunlerEtiketi;
	private ImageIcon gunCikarmaIkon;
	private Image gunCikarmaImage;
	private ImageIcon gunEklemeIkon;
	private Image gunEklemeImage;
	private JCheckBox[] gunSecimleri;
	private ButtonGroup grup;
	private JLabel baslangicTarihiEtiketi;
	private JDateChooser baslangicTarihiSecimi;
	private JLabel bitisTarihiEtiketi;
	private JDateChooser bitisTarihiSecimi;
	private JLabel sinavTuruEtiketi;
	private JRadioButton[] sinavTuruSecimi;
	private JRadioButton esZamanliSinavSecimi;
	private JLabel beklemeSuresiEtiketi;
	private SpinnerNumberModel spinnerModeli;
	private JSpinner beklemeSuresiAyari;
	private SpinnerDateModel spinnerDateModeli;
	private JLabel baslangicSaatiEtiketi;
	private JSpinner baslangicSaatiAyari;
	private JLabel bitisSaatiEtiketi;
	private JSpinner bitisSaatiAyari;
	private Calendar defaultSaatiAyarlama;
	private JButton sinavTakviminiOlusturButonu;
	private ImageIcon geriDonIkon;
	private Image geriDonImage;
	private JButton geriButonu;

	private SinavProgramiOlusturmaEkrani() {

		sinavProgramiOlusturmaPaneli = new JPanel();
		sinavProgramiOlusturmaPaneli.setBounds(0, 0, 1000, 600);
		sinavProgramiOlusturmaPaneli.setBackground(new Color(90, 140, 120));
		sinavProgramiOlusturmaPaneli.setLayout(null);
		sinavProgramiOlusturmaPaneli.setVisible(false);

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
		dersTablosuKaydirmaCubugu.setBounds(10, 250, 500, 300);
		dersTablosuKaydirmaCubugu.setBackground(Color.yellow);
		dersTablosuKaydirmaCubugu.setOpaque(true);
		dersTablosuKaydirmaCubugu.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiOlusturmaPaneli.add(dersTablosuKaydirmaCubugu);

		carpiIkon = new ImageIcon(GirisEkrani.class.getResource("/math.png"));
		carpiImage = carpiIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		carpiIkon = new ImageIcon(carpiImage);

		dersCikarButonu = new JButton("");
		dersCikarButonu.setBounds(10, 200, 40, 40);
		dersCikarButonu.setFocusable(false);
		dersCikarButonu.setFont(new Font("Serif", Font.BOLD, 20));
		dersCikarButonu.setIcon(carpiIkon);
		dersCikarButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiOlusturmaPaneli.add(dersCikarButonu);
		dersCikarButonu.addActionListener(e -> SinavProgramiOlusturmaAksiyonlari.dersCikarButonuKontrol());

		kumSaatiIkon = new ImageIcon(GirisEkrani.class.getResource("/hourglass.png"));
		kumSaatiImage = kumSaatiIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		kumSaatiIkon = new ImageIcon(kumSaatiImage);

		sureDegistirmeButonu = new JButton("");
		sureDegistirmeButonu.setBounds(60, 200, 40, 40);
		sureDegistirmeButonu.setFocusable(false);
		sureDegistirmeButonu.setFont(new Font("Serif", Font.BOLD, 20));
		sureDegistirmeButonu.setIcon(kumSaatiIkon);
		sureDegistirmeButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiOlusturmaPaneli.add(sureDegistirmeButonu);
		sureDegistirmeButonu.addActionListener(e -> {
			SinavProgramiOlusturmaAksiyonlari.sureDegistirmeButonuKontrol();
		});

		cikarilacakGunlerEtiketi = new JLabel("Çıkarılacak günleri seçiniz");
		cikarilacakGunlerEtiketi.setBounds(530, 240, 250, 30);
		cikarilacakGunlerEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		sinavProgramiOlusturmaPaneli.add(cikarilacakGunlerEtiketi);

		gunCikarmaIkon = new ImageIcon(GirisEkrani.class.getResource("/close.png"));
		gunCikarmaImage = gunCikarmaIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		gunCikarmaIkon = new ImageIcon(gunCikarmaImage);

		gunEklemeIkon = new ImageIcon(GirisEkrani.class.getResource("/checked.png"));
		gunEklemeImage = gunEklemeIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		gunEklemeIkon = new ImageIcon(gunEklemeImage);

		String[] gunler = { "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar" };
		gunSecimleri = new JCheckBox[gunler.length];

		int x1 = 530;
		int y1 = 280;

		for (int i = 0; i < gunler.length; i++) {

			gunSecimleri[i] = new JCheckBox(gunler[i]);
			gunSecimleri[i].setBounds(x1, y1 + (i * 40), 250, 30);
			gunSecimleri[i].setFont(new Font("Serif", Font.BOLD, 20));
			gunSecimleri[i].setFocusable(false);
			gunSecimleri[i].setBorder(BorderFactory.createLineBorder(Color.black));
			gunSecimleri[i].setIcon(gunEklemeIkon);
			gunSecimleri[i].setSelectedIcon(gunCikarmaIkon);

			sinavProgramiOlusturmaPaneli.add(gunSecimleri[i]);
		}

		baslangicTarihiEtiketi = new JLabel("Başlangıç tarihi");
		baslangicTarihiEtiketi.setBounds(800, 240, 250, 30);
		baslangicTarihiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		sinavProgramiOlusturmaPaneli.add(baslangicTarihiEtiketi);

		baslangicTarihiSecimi = new JDateChooser();
		baslangicTarihiSecimi.setBounds(800, 280, 150, 30);
		baslangicTarihiSecimi.setFont(new Font("Serif", Font.BOLD, 20));
		baslangicTarihiSecimi.getDateEditor().setEnabled(false);
		baslangicTarihiSecimi.setFocusable(false);
		baslangicTarihiSecimi.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiOlusturmaPaneli.add(baslangicTarihiSecimi);

		bitisTarihiEtiketi = new JLabel("Bitiş tarihi");
		bitisTarihiEtiketi.setBounds(800, 320, 250, 30);
		bitisTarihiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		sinavProgramiOlusturmaPaneli.add(bitisTarihiEtiketi);

		bitisTarihiSecimi = new JDateChooser();
		bitisTarihiSecimi.setBounds(800, 360, 150, 30);
		bitisTarihiSecimi.setFont(new Font("Serif", Font.BOLD, 20));
		bitisTarihiSecimi.getDateEditor().setEnabled(false);
		bitisTarihiSecimi.setFocusable(false);
		bitisTarihiSecimi.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiOlusturmaPaneli.add(bitisTarihiSecimi);

		sinavTuruEtiketi = new JLabel("Sınav türünü seçiniz");
		sinavTuruEtiketi.setBounds(800, 400, 250, 30);
		sinavTuruEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		sinavProgramiOlusturmaPaneli.add(sinavTuruEtiketi);

		String[] sinavTurleri = { "Vize", "Final", "Bütünleme" };
		sinavTuruSecimi = new JRadioButton[sinavTurleri.length];

		int x2 = 800;
		int y2 = 440;

		grup = new ButtonGroup();

		for (int i = 0; i < sinavTurleri.length; i++) {

			sinavTuruSecimi[i] = new JRadioButton(sinavTurleri[i]);
			sinavTuruSecimi[i].setBounds(x2, y2 + (i * 40), 150, 30);
			sinavTuruSecimi[i].setFont(new Font("Serif", Font.BOLD, 20));
			sinavTuruSecimi[i].setFocusable(false);
			sinavTuruSecimi[i].setBorder(BorderFactory.createLineBorder(Color.black));
			sinavProgramiOlusturmaPaneli.add(sinavTuruSecimi[i]);
			grup.add(sinavTuruSecimi[i]);
		}

		esZamanliSinavSecimi = new JRadioButton("<html>Eş zamanlı sınavlara<br>izin verilsin</html>");
		esZamanliSinavSecimi.setBounds(530, 180, 220, 50);
		esZamanliSinavSecimi.setFont(new Font("Serif", Font.BOLD, 20));
		esZamanliSinavSecimi.setFocusable(false);
		esZamanliSinavSecimi.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiOlusturmaPaneli.add(esZamanliSinavSecimi);

		beklemeSuresiEtiketi = new JLabel("Bekleme süresi");
		beklemeSuresiEtiketi.setBounds(800, 160, 250, 30);
		beklemeSuresiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		sinavProgramiOlusturmaPaneli.add(beklemeSuresiEtiketi);

		spinnerModeli = new SpinnerNumberModel(15, 0, 120, 1);
		beklemeSuresiAyari = new JSpinner(spinnerModeli);
		beklemeSuresiAyari.setBounds(800, 200, 150, 30);
		beklemeSuresiAyari.setFont(new Font("Serif", Font.BOLD, 20));
		beklemeSuresiAyari.setFocusable(false);
		((JSpinner.DefaultEditor) beklemeSuresiAyari.getEditor()).getTextField().setEditable(false);
		beklemeSuresiAyari.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiOlusturmaPaneli.add(beklemeSuresiAyari);

		baslangicSaatiEtiketi = new JLabel("Başlangıç saati");
		baslangicSaatiEtiketi.setBounds(800, 0, 150, 30);
		baslangicSaatiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		sinavProgramiOlusturmaPaneli.add(baslangicSaatiEtiketi);

		spinnerDateModeli = new SpinnerDateModel();
		baslangicSaatiAyari = new JSpinner(spinnerDateModeli);
		baslangicSaatiAyari.setEditor(new JSpinner.DateEditor(baslangicSaatiAyari, "HH:mm"));
		baslangicSaatiAyari.setBounds(800, 40, 150, 30);
		baslangicSaatiAyari.setFont(new Font("Serif", Font.BOLD, 20));
		baslangicSaatiAyari.setFocusable(false);
		((JSpinner.DefaultEditor) baslangicSaatiAyari.getEditor()).getTextField().setEditable(false);
		baslangicSaatiAyari.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiOlusturmaPaneli.add(baslangicSaatiAyari);

		bitisSaatiEtiketi = new JLabel("Bitiş saati");
		bitisSaatiEtiketi.setBounds(800, 80, 150, 30);
		bitisSaatiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		sinavProgramiOlusturmaPaneli.add(bitisSaatiEtiketi);

		spinnerDateModeli = new SpinnerDateModel();
		bitisSaatiAyari = new JSpinner(spinnerDateModeli);
		bitisSaatiAyari.setEditor(new JSpinner.DateEditor(bitisSaatiAyari, "HH:mm"));
		bitisSaatiAyari.setBounds(800, 120, 150, 30);
		bitisSaatiAyari.setFont(new Font("Serif", Font.BOLD, 20));
		bitisSaatiAyari.setFocusable(false);
		((JSpinner.DefaultEditor) bitisSaatiAyari.getEditor()).getTextField().setEditable(false);
		bitisSaatiAyari.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiOlusturmaPaneli.add(bitisSaatiAyari);

		defaultSaatiAyarlama = Calendar.getInstance();
		defaultSaatiAyarlama.set(Calendar.HOUR_OF_DAY, 9);
		defaultSaatiAyarlama.set(Calendar.MINUTE, 0);
		baslangicSaatiAyari.setValue(defaultSaatiAyarlama.getTime());

		defaultSaatiAyarlama.set(Calendar.HOUR_OF_DAY, 17);
		defaultSaatiAyarlama.set(Calendar.MINUTE, 0);
		bitisSaatiAyari.setValue(defaultSaatiAyarlama.getTime());

		sinavTakviminiOlusturButonu = new JButton("Sınav Takvimini Oluştur");
		sinavTakviminiOlusturButonu.setBounds(150, 50, 250, 100);
		sinavTakviminiOlusturButonu.setFocusable(false);
		sinavTakviminiOlusturButonu.setFont(new Font("Serif", Font.BOLD, 20));
		sinavTakviminiOlusturButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiOlusturmaPaneli.add(sinavTakviminiOlusturButonu);
		sinavTakviminiOlusturButonu
				.addActionListener(e -> SinavProgramiOlusturmaAksiyonlari.sinavTakviminiOlusturButonuKontrol());

		geriDonIkon = new ImageIcon(GirisEkrani.class.getResource("/geri.png"));
		geriDonImage = geriDonIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		geriDonIkon = new ImageIcon(geriDonImage);

		geriButonu = new JButton("");
		geriButonu.setBounds(20, 20, 50, 50);
		geriButonu.setFocusable(false);
		geriButonu.setFont(new Font("Serif", Font.BOLD, 20));
		geriButonu.setIcon(geriDonIkon);
		geriButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		sinavProgramiOlusturmaPaneli.add(geriButonu);
		geriButonu.addActionListener(e -> SinavProgramiOlusturmaAksiyonlari.geriDonButonuKontrol());
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

	public void sinavProgramiOlusturmaEkraniTemizle() {

		defaultSaatiAyarlama = Calendar.getInstance();
		defaultSaatiAyarlama.set(Calendar.HOUR_OF_DAY, 9);
		defaultSaatiAyarlama.set(Calendar.MINUTE, 0);
		baslangicSaatiAyari.setValue(defaultSaatiAyarlama.getTime());

		defaultSaatiAyarlama.set(Calendar.HOUR_OF_DAY, 17);
		defaultSaatiAyarlama.set(Calendar.MINUTE, 0);
		bitisSaatiAyari.setValue(defaultSaatiAyarlama.getTime());

		for (JCheckBox c : gunSecimleri)
			c.setSelected(false);

		for (JRadioButton r : sinavTuruSecimi)
			r.setSelected(false);

		dersTablosu.clearSelection();
		grup.clearSelection();
		baslangicTarihiSecimi.setDate(null);
		bitisTarihiSecimi.setDate(null);
		esZamanliSinavSecimi.setSelected(false);
		beklemeSuresiAyari.setValue(15);
	}

	public static SinavProgramiOlusturmaEkrani getSinavProgramiOlusturmaEkrani() {

		if (sinavProgramiOlusturmaEkrani == null)
			sinavProgramiOlusturmaEkrani = new SinavProgramiOlusturmaEkrani();

		return sinavProgramiOlusturmaEkrani;
	}

	public JPanel getSinavProgramiOlusturmaPaneli() {

		return this.sinavProgramiOlusturmaPaneli;
	}

	public JTable getDersTablosu() {

		return this.dersTablosu;
	}

	public JCheckBox[] getGunSecimleri() {

		return this.gunSecimleri;
	}

	public JRadioButton getEsZamanliSinavSecimi() {

		return this.esZamanliSinavSecimi;
	}

	public JRadioButton[] getSinavTuruSecimi() {

		return this.sinavTuruSecimi;
	}

	public ButtonGroup getGrup() {

		return this.grup;
	}

	public JDateChooser getBaslangicTarihiSecimi() {

		return this.baslangicTarihiSecimi;
	}

	public JDateChooser getBitisTarihiSecimi() {

		return this.bitisTarihiSecimi;
	}

	public JSpinner getBeklemeSuresiAyari() {

		return this.beklemeSuresiAyari;
	}

	public JSpinner getBaslangicSaatiAyari() {

		return this.baslangicSaatiAyari;
	}

	public JSpinner getBitisSaatiAyari() {

		return this.bitisSaatiAyari;
	}
}
