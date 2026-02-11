package Arayuz;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class AnaPencere {

	private JFrame anaPencere;
	private ImageIcon anaPencereIkon;
	private Image anaPencereImage;

	private AnaPencere() {

		anaPencere = new JFrame();
		anaPencere.setTitle("YAZ-LAB");
		anaPencere.setSize(1000, 600);
		anaPencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		anaPencere.setLocationRelativeTo(null);
		anaPencere.setLayout(null);

		anaPencereIkon = new ImageIcon(GirisEkrani.class.getResource("/hacker.png"));
		anaPencereImage = anaPencereIkon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		anaPencereIkon = new ImageIcon(anaPencereImage);
		anaPencere.setIconImage(anaPencereIkon.getImage());

		anaPencere.add(GirisEkrani.getGirisEkrani().getGirisPaneli());
		anaPencere.add(KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli());
		anaPencere.add(ListeYuklemeEkrani.getListeYuklemeEkrani().getListeYuklemePaneli());
		anaPencere.add(DerslikEklemeEkrani.getDerslikEklemeEkrani().getDerslikEklemePaneli());
		anaPencere.add(DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().getDerslikDuzenleSilPaneli());
		anaPencere.add(DerslikAramaEkrani.getDerslikAramaEkrani().getDerslikAramaPaneli());
		anaPencere.add(OgrenciAramaEkrani.getOgrenciAramaEkrani().getOgrenciAramaPaneli());
		anaPencere.add(DersAramaEkrani.getDersAramaEkrani().getDersAramaPaneli());
		anaPencere.add(KoordinatorEklemeEkrani.getKoordinatorEklemeEkrani().getKoordinatorEklemePaneli());
		anaPencere
				.add(SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().getSinavProgramiOlusturmaPaneli());
		anaPencere.add(
				SinavProgramiGoruntulemeEkrani.getSinavProgramiGoruntulemeEkrani().getSinavProgramiGoruntulemePaneli());

		anaPencere.setVisible(true);
	}

	public static void main(String[] args) {

		AnaPencere anaPencere = new AnaPencere();
	}
}
