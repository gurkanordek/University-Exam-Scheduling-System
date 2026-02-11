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

import Aksiyonlar.KoordinatorEklemeAksiyonlari;

public class KoordinatorEklemeEkrani {

	public static KoordinatorEklemeEkrani koordinatorEklemeEkrani;

	private JPanel koordinatorEklemePaneli;
	private ImageIcon harfIkon;
	private Image harfImage;
	private JLabel adSoyadEtiketi;
	private JTextField adSoyadAlani;
	private ImageIcon ePostaIkon;
	private Image ePostaImage;
	private JLabel ePostaEtiketi;
	private JTextField ePostaAlani;
	private ImageIcon sifreIkon;
	private Image sifreImage;
	private JLabel sifreEtiketi;
	private JTextField sifreAlani;
	private JLabel bolumAdiEtiketi;
	private JComboBox<String> bolumAdiSecimi;
	private JButton onaylaButonu;
	private ImageIcon geriDonIkon;
	private Image geriDonImage;
	private JButton geriDonButonu;

	private KoordinatorEklemeEkrani() {

		koordinatorEklemePaneli = new JPanel();
		koordinatorEklemePaneli.setBounds(0, 0, 1000, 600);
		koordinatorEklemePaneli.setBackground(new Color(90, 140, 120));
		koordinatorEklemePaneli.setLayout(null);
		koordinatorEklemePaneli.setVisible(false);

		harfIkon = new ImageIcon(GirisEkrani.class.getResource("/abc.png"));
		harfImage = harfIkon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		harfIkon = new ImageIcon(harfImage);

		adSoyadEtiketi = new JLabel("Ad-Soyad giriniz", harfIkon, JLabel.LEFT);
		adSoyadEtiketi.setBounds(350, 100, 250, 30);
		adSoyadEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		koordinatorEklemePaneli.add(adSoyadEtiketi);

		adSoyadAlani = new JTextField();
		adSoyadAlani.setBounds(350, 140, 250, 30);
		adSoyadAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		adSoyadAlani.setFont(new Font("Serif", Font.BOLD, 20));
		koordinatorEklemePaneli.add(adSoyadAlani);

		ePostaIkon = new ImageIcon(GirisEkrani.class.getResource("/email.png"));
		ePostaImage = ePostaIkon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ePostaIkon = new ImageIcon(ePostaImage);

		ePostaEtiketi = new JLabel("E-posta adresi giriniz", ePostaIkon, JLabel.LEFT);
		ePostaEtiketi.setBounds(350, 180, 250, 30);
		ePostaEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		koordinatorEklemePaneli.add(ePostaEtiketi);

		ePostaAlani = new JTextField();
		ePostaAlani.setBounds(350, 220, 250, 30);
		ePostaAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		ePostaAlani.setFont(new Font("Serif", Font.BOLD, 20));
		koordinatorEklemePaneli.add(ePostaAlani);

		sifreIkon = new ImageIcon(GirisEkrani.class.getResource("/password.png"));
		sifreImage = sifreIkon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		sifreIkon = new ImageIcon(sifreImage);

		sifreEtiketi = new JLabel("Bir şifre belirleyiniz", sifreIkon, JLabel.LEFT);
		sifreEtiketi.setBounds(350, 260, 250, 30);
		sifreEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		koordinatorEklemePaneli.add(sifreEtiketi);

		sifreAlani = new JTextField();
		sifreAlani.setBounds(350, 300, 250, 30);
		sifreAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		sifreAlani.setFont(new Font("Serif", Font.BOLD, 20));
		koordinatorEklemePaneli.add(sifreAlani);

		bolumAdiEtiketi = new JLabel("Bölüm seçiniz");
		bolumAdiEtiketi.setBounds(350, 340, 250, 30);
		bolumAdiEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		koordinatorEklemePaneli.add(bolumAdiEtiketi);

		String[] bolumler = { "Seçiniz", "Bilgisayar Mühendisliği", "Yazılım Mühendisliği", "Elektrik Mühendisliği",
				"Elektronik Mühendisliği", "İnşaat Mühendisliği" };
		bolumAdiSecimi = new JComboBox<String>(bolumler);
		bolumAdiSecimi.setBounds(350, 380, 250, 30);
		bolumAdiSecimi.setBorder(BorderFactory.createLineBorder(Color.black));
		bolumAdiSecimi.setFont(new Font("Serif", Font.BOLD, 20));
		koordinatorEklemePaneli.add(bolumAdiSecimi);

		onaylaButonu = new JButton("Onayla");
		onaylaButonu.setBounds(350, 420, 250, 30);
		onaylaButonu.setFocusable(false);
		onaylaButonu.setFont(new Font("Serif", Font.BOLD, 20));
		onaylaButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		koordinatorEklemePaneli.add(onaylaButonu);
		onaylaButonu.addActionListener(e -> {
			KoordinatorEklemeAksiyonlari.onaylaButonuKontrol();
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
		koordinatorEklemePaneli.add(geriDonButonu);
		geriDonButonu.addActionListener(e -> KoordinatorEklemeAksiyonlari.geriDonButonuKontrol());
	}

	public static KoordinatorEklemeEkrani getKoordinatorEklemeEkrani() {

		if (koordinatorEklemeEkrani == null)
			koordinatorEklemeEkrani = new KoordinatorEklemeEkrani();

		return koordinatorEklemeEkrani;
	}

	public JPanel getKoordinatorEklemePaneli() {

		return this.koordinatorEklemePaneli;
	}

	public JTextField getAdSoyadAlani() {

		return this.adSoyadAlani;
	}

	public JTextField getePostaAlani() {

		return this.ePostaAlani;
	}

	public JTextField getSifreAlani() {

		return this.sifreAlani;
	}

	public JComboBox<String> getBolumAdiSecimi() {

		return this.bolumAdiSecimi;
	}

	public void KoordinatorEklemeEkraniTemizle() {

		KoordinatorEklemeEkrani ekran = KoordinatorEklemeEkrani.getKoordinatorEklemeEkrani();
		ekran.getAdSoyadAlani().setText("");
		ekran.getePostaAlani().setText("");
		ekran.getSifreAlani().setText("");
		ekran.getBolumAdiSecimi().setSelectedIndex(0);
	}
}
