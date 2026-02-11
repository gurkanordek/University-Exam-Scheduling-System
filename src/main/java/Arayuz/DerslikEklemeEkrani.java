package Arayuz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Aksiyonlar.DerslikEklemeAksiyonlari;

public class DerslikEklemeEkrani {

	private static DerslikEklemeEkrani derslikEklemeEkrani;

	private JPanel derslikEklemePaneli;
	private JLabel derslikKoduEtiketi;
	private JTextField derslikKoduAlani;
	private JLabel derslikAdiEtiketi;
	private JTextField derslikAdiAlani;
	private JLabel derslikKapasitesiEtiketi;
	private JTextField derslikKapasitesiAlani;
	private JLabel sutunSayisiEtiketi;
	private JTextField sutunSayisiAlani;
	private JLabel satirSayisiEtiketi;
	private JTextField satirSayisiAlani;
	private JLabel siraYapisiEtiketi;
	private JTextField siraYapisiAlani;
	private JLabel bolumAdiEtiketi;
	private JComboBox<String> bolumAdiSecimi;
	private JButton onaylaButonu;
	private ImageIcon sayiIkon;
	private Image sayiImage;
	private ImageIcon harfIkon;
	private Image harfImage;
	private ImageIcon geriDonIkon;
	private Image geriDonImage;
	private JButton geriDonButonu;

	private DerslikEklemeEkrani() {

		derslikEklemePaneli = new JPanel();
		derslikEklemePaneli.setBounds(0, 0, 1000, 600);
		derslikEklemePaneli.setBackground(new Color(90, 140, 120));
		derslikEklemePaneli.setLayout(null);
		derslikEklemePaneli.setVisible(false);

		harfIkon = new ImageIcon(GirisEkrani.class.getResource("/abc.png"));
		harfImage = harfIkon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		harfIkon = new ImageIcon(harfImage);

		derslikKoduEtiketi = new JLabel("Derslik kodunu giriniz", harfIkon, JLabel.LEFT);
		derslikKoduEtiketi.setBounds(100, 100, 250, 30);
		derslikKoduEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(derslikKoduEtiketi);

		derslikKoduAlani = new JTextField();
		derslikKoduAlani.setBounds(100, 140, 250, 30);
		derslikKoduAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		derslikKoduAlani.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(derslikKoduAlani);

		derslikAdiEtiketi = new JLabel("Derslik adını giriniz", harfIkon, JLabel.LEFT);
		derslikAdiEtiketi.setBounds(100, 180, 250, 30);
		derslikAdiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(derslikAdiEtiketi);

		derslikAdiAlani = new JTextField();
		derslikAdiAlani.setBounds(100, 220, 250, 30);
		derslikAdiAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		derslikAdiAlani.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(derslikAdiAlani);

		sayiIkon = new ImageIcon(GirisEkrani.class.getResource("/123.png"));
		sayiImage = sayiIkon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		sayiIkon = new ImageIcon(sayiImage);

		derslikKapasitesiEtiketi = new JLabel("Derslik kapasitesini giriniz", sayiIkon, JLabel.LEFT);
		derslikKapasitesiEtiketi.setBounds(100, 260, 260, 30);
		derslikKapasitesiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(derslikKapasitesiEtiketi);

		derslikKapasitesiAlani = new JTextField();
		derslikKapasitesiAlani.setBounds(100, 300, 250, 30);
		derslikKapasitesiAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		derslikKapasitesiAlani.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(derslikKapasitesiAlani);

		sutunSayisiEtiketi = new JLabel("Sütun sayısını giriniz", sayiIkon, JLabel.LEFT);
		sutunSayisiEtiketi.setBounds(370, 100, 250, 30);
		sutunSayisiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(sutunSayisiEtiketi);

		sutunSayisiAlani = new JTextField();
		sutunSayisiAlani.setBounds(370, 140, 250, 30);
		sutunSayisiAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		sutunSayisiAlani.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(sutunSayisiAlani);

		satirSayisiEtiketi = new JLabel("Satır sayısını giriniz", sayiIkon, JLabel.LEFT);
		satirSayisiEtiketi.setBounds(370, 180, 250, 30);
		satirSayisiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(satirSayisiEtiketi);

		satirSayisiAlani = new JTextField();
		satirSayisiAlani.setBounds(370, 220, 250, 30);
		satirSayisiAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		satirSayisiAlani.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(satirSayisiAlani);

		siraYapisiEtiketi = new JLabel("Sıra yapısını giriniz", sayiIkon, JLabel.LEFT);
		siraYapisiEtiketi.setBounds(370, 260, 250, 30);
		siraYapisiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(siraYapisiEtiketi);

		siraYapisiAlani = new JTextField();
		siraYapisiAlani.setBounds(370, 300, 250, 30);
		siraYapisiAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		siraYapisiAlani.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(siraYapisiAlani);

		bolumAdiEtiketi = new JLabel("Bölüm adını giriniz");
		bolumAdiEtiketi.setBounds(640, 100, 250, 30);
		bolumAdiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(bolumAdiEtiketi);

		String[] bolumler = { "Seçiniz", "Bilgisayar Mühendisliği", "Yazılım Mühendisliği", "Elektrik Mühendisliği",
				"Elektronik Mühendisliği", "İnşaat Mühendisliği" };
		bolumAdiSecimi = new JComboBox<String>(bolumler);
		bolumAdiSecimi.setBounds(640, 140, 250, 30);
		bolumAdiSecimi.setBorder(BorderFactory.createLineBorder(Color.black));
		bolumAdiSecimi.setFont(new Font("Serif", Font.BOLD, 20));
		derslikEklemePaneli.add(bolumAdiSecimi);

		onaylaButonu = new JButton("Onayla");
		onaylaButonu.setBounds(370, 360, 250, 30);
		onaylaButonu.setFocusable(false);
		onaylaButonu.setFont(new Font("Serif", Font.BOLD, 20));
		onaylaButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		derslikEklemePaneli.add(onaylaButonu);
		onaylaButonu.addActionListener(e -> {
			DerslikEklemeAksiyonlari.onaylaButonuKontrol();
		});

		geriDonIkon = new ImageIcon(GirisEkrani.class.getResource("/geri.png"));
		geriDonImage = geriDonIkon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		geriDonIkon = new ImageIcon(geriDonImage);

		geriDonButonu = new JButton("");
		geriDonButonu.setBounds(20, 20, 50, 50);
		geriDonButonu.setFocusable(false);
		geriDonButonu.setFont(new Font("Serif", Font.BOLD, 20));
		geriDonButonu.setIcon(geriDonIkon);
		geriDonButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		derslikEklemePaneli.add(geriDonButonu);
		geriDonButonu.addActionListener(e -> DerslikEklemeAksiyonlari.geriDonButonuKontrol());
	}

	public static DerslikEklemeEkrani getDerslikEklemeEkrani() {

		if (derslikEklemeEkrani == null)
			derslikEklemeEkrani = new DerslikEklemeEkrani();

		return derslikEklemeEkrani;
	}

	public JPanel getDerslikEklemePaneli() {

		return this.derslikEklemePaneli;
	}

	public JTextField getDerslikKoduAlani() {

		return this.derslikKoduAlani;
	}

	public JTextField getDerslikAdiAlani() {

		return this.derslikAdiAlani;
	}

	public JTextField getDerslikKapasitesiAlani() {

		return this.derslikKapasitesiAlani;
	}

	public JTextField getSutunSayisiAlani() {

		return this.sutunSayisiAlani;
	}

	public JTextField getSatirSayisiAlani() {

		return this.satirSayisiAlani;
	}

	public JTextField getSiraYapisiAlani() {

		return this.siraYapisiAlani;
	}

	public JComboBox<String> getBolumAdiSecimi() {

		return this.bolumAdiSecimi;
	}

	public void derslikEklemeEkraniTemizle() {

		DerslikEklemeEkrani ekran = DerslikEklemeEkrani.getDerslikEklemeEkrani();
		ekran.getDerslikKoduAlani().setText("");
		ekran.getDerslikAdiAlani().setText("");
		ekran.getDerslikKapasitesiAlani().setText("");
		ekran.getSutunSayisiAlani().setText("");
		ekran.getSatirSayisiAlani().setText("");
		ekran.getSiraYapisiAlani().setText("");

		if (GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim().equals("admin"))
			ekran.getBolumAdiSecimi().setSelectedIndex(0);
		else
			ekran.getBolumAdiSecimi().setSelectedItem(GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim());
	}
}
