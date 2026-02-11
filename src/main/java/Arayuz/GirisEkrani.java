package Arayuz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import Aksiyonlar.GirisAksiyonlari;

public class GirisEkrani {

	private static GirisEkrani girisEkrani;

	private String aktifKullaniciBolumu = null;
	private JPanel girisPaneli;
	private ImageIcon ePostaIkon;
	private Image ePostaImage;
	private JLabel ePostaEtiketi;
	private JTextField ePostaAlani;
	private ImageIcon sifreIkon;
	private Image sifreImage;
	private JLabel sifreEtiketi;
	private JPasswordField sifreAlani;
	private JButton girisButonu;

	private GirisEkrani() {

		girisPaneli = new JPanel();
		girisPaneli.setBounds(0, 0, 1000, 600);
		girisPaneli.setBackground(new Color(90, 140, 120));
		girisPaneli.setLayout(null);

		ePostaIkon = new ImageIcon(GirisEkrani.class.getResource("/email.png"));
		ePostaImage = ePostaIkon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ePostaIkon = new ImageIcon(ePostaImage);

		ePostaEtiketi = new JLabel("E-posta adresinizi giriniz", ePostaIkon, JLabel.LEFT);
		ePostaEtiketi.setBounds(350, 170, 250, 30);
		ePostaEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		girisPaneli.add(ePostaEtiketi);

		ePostaAlani = new JTextField();
		ePostaAlani.setBounds(350, 210, 250, 30);
		ePostaAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		ePostaAlani.setFont(new Font("Serif", Font.BOLD, 20));
		girisPaneli.add(ePostaAlani);

		sifreIkon = new ImageIcon(GirisEkrani.class.getResource("/password.png"));
		sifreImage = sifreIkon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		sifreIkon = new ImageIcon(sifreImage);

		sifreEtiketi = new JLabel("Şifrenizi giriniz", sifreIkon, JLabel.LEFT);
		sifreEtiketi.setBounds(350, 250, 250, 30);
		sifreEtiketi.setFont(new Font("Serif", Font.BOLD, 20));
		girisPaneli.add(sifreEtiketi);

		sifreAlani = new JPasswordField();
		sifreAlani.setBounds(350, 290, 250, 30);
		sifreAlani.setBorder(BorderFactory.createLineBorder(Color.black));
		sifreAlani.setFont(new Font("Serif", Font.BOLD, 20));
		girisPaneli.add(sifreAlani);

		girisButonu = new JButton("Giriş Yap");
		girisButonu.setBounds(350, 330, 250, 30);
		girisButonu.setFocusable(false);
		girisButonu.setFont(new Font("Serif", Font.BOLD, 20));
		girisButonu.setBorder(BorderFactory.createLineBorder(Color.black));
		girisPaneli.add(girisButonu);
		girisButonu.addActionListener(e -> GirisAksiyonlari.girisButonuKontrol());

		girisPaneli.setVisible(true);
	}

	public static GirisEkrani getGirisEkrani() {

		if (girisEkrani == null)
			girisEkrani = new GirisEkrani();

		return girisEkrani;
	}

	public String getAktifKullaniciBolumu() {

		return this.aktifKullaniciBolumu;
	}

	public void setAktifKullaniciBolumu(String bolum) {

		this.aktifKullaniciBolumu = bolum;
	}

	public JPanel getGirisPaneli() {

		return this.girisPaneli;
	}

	public JTextField getEPostaAlani() {

		return this.ePostaAlani;
	}

	public JPasswordField getSifreAlani() {

		return this.sifreAlani;
	}
}
