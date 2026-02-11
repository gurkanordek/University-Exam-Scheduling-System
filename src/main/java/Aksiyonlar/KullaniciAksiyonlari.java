package Aksiyonlar;

import Arayuz.KullaniciEkrani;
import Arayuz.DersAramaEkrani;
import Arayuz.DerslikAramaEkrani;
import Arayuz.DerslikDuzenleSilEkrani;
import Arayuz.DerslikEklemeEkrani;
import Arayuz.GirisEkrani;
import Arayuz.KoordinatorEklemeEkrani;
import Arayuz.ListeYuklemeEkrani;
import Arayuz.OgrenciAramaEkrani;
import Arayuz.SinavProgramiOlusturmaEkrani;

public class KullaniciAksiyonlari {

	public static void listeleriYukleButonuKontrol() {

		ListeYuklemeEkrani ekran = ListeYuklemeEkrani.getListeYuklemeEkrani();

		if (!GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim().equals("admin")) {

			ekran.getBolumAdiSecimi().setSelectedItem(GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu());
			ekran.getBolumAdiSecimi().setEnabled(false);

		} else
			ekran.getBolumAdiSecimi().setEnabled(true);

		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(false);
		ekran.getListeYuklemePaneli().setVisible(true);
	}

	public static void derslikEkleButonuKontrol() {

		DerslikEklemeEkrani ekran = DerslikEklemeEkrani.getDerslikEklemeEkrani();

		if (!GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu().trim().equals("admin")) {

			ekran.getBolumAdiSecimi().setSelectedItem(GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu());
			ekran.getBolumAdiSecimi().setEnabled(false);

		} else
			ekran.getBolumAdiSecimi().setEnabled(true);

		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(false);
		ekran.getDerslikEklemePaneli().setVisible(true);
	}

	public static void geriDonButonuKontrol() {

		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(false);
		GirisEkrani.getGirisEkrani().getGirisPaneli().setVisible(true);
		GirisEkrani.getGirisEkrani().setAktifKullaniciBolumu("");
	}

	public static void derslikDuzenleSilButonuKontrol() {

		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(false);
		DerslikDuzenleSilEkrani.getDerslikDuzenleSilEkrani().getDerslikDuzenleSilPaneli().setVisible(true);
	}

	public static void derslikAramaButonuKontrol() {

		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(false);
		DerslikAramaEkrani.getDerslikAramaEkrani().getDerslikAramaPaneli().setVisible(true);
	}

	public static void ogrenciAramaButonuKontrol() {

		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(false);
		OgrenciAramaEkrani.getOgrenciAramaEkrani().getOgrenciAramaPaneli().setVisible(true);
	}

	public static void dersAramaButonuKontrol() {

		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(false);
		DersAramaEkrani.getDersAramaEkrani().getDersAramaPaneli().setVisible(true);
	}

	public static void koordinatorEklemeButonuKontrol() {

		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(false);
		KoordinatorEklemeEkrani.getKoordinatorEklemeEkrani().getKoordinatorEklemePaneli().setVisible(true);
	}

	public static void sinavProgramiOlusturmaButonuKontrol() {

		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(false);
		SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().getSinavProgramiOlusturmaPaneli()
				.setVisible(true);
	}
}
