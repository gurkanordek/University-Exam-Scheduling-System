
package Aksiyonlar;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import Arayuz.GirisEkrani;
import Arayuz.KullaniciEkrani;
import Arayuz.SinavProgramiGoruntulemeEkrani;
import Arayuz.SinavProgramiOlusturmaEkrani;

public class SinavProgramiOlusturmaAksiyonlari {

	public static void sinavTakviminiOlusturButonuKontrol() {

		List<String> tablodakiDerslerinKodlari = getTablodakiDerslerinKodlari();

		if (tablodakiDerslerinKodlari.isEmpty()) {

			JOptionPane.showMessageDialog(null, "Sınavı yapılacak dersler tablosu boş", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().getGrup().getSelection() == null) {

			JOptionPane.showMessageDialog(null, "Lütfen sınav türünü seçiniz", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		List<String> sinavYapilacakTarihler = getSinavYapilacakTarihler();

		if (sinavYapilacakTarihler == null || sinavYapilacakTarihler.isEmpty()) {

			JOptionPane.showMessageDialog(null, "Sınav yapılabilecek bir tarih bulunamadı", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Map<String, Integer> derslerVeSinifSeviyeleri = getDerslerVeSinifSeviyeleri(tablodakiDerslerinKodlari);

		Map<String, List<String>> tarihlerVeSinavlar = getTarihlerVeSinavlar(tablodakiDerslerinKodlari,
				sinavYapilacakTarihler, derslerVeSinifSeviyeleri);

		Map<String, Set<String>> dersVeOgrenciler = getDerslerVeOgrencileri();

		Map<String, Map<String, String>> sinavlarVeSaatleri = getSinavlarVeSaatleri(tablodakiDerslerinKodlari,
				sinavYapilacakTarihler, tarihlerVeSinavlar, dersVeOgrenciler);

		if (sinavlarVeSaatleri == null) {

			JOptionPane.showMessageDialog(null, "Tüm sınavların yapılabilmesi için zaman aralığı yetersiz",
					"Hatalı giriş", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Map<String, Integer> sinavlarVeOgrenciSayilari = getSinavlarVeOgrenciSayilari(tablodakiDerslerinKodlari);

		Map<String, List<String>> sinavlarVeDerslikleri = getSinavlarVeDerslikleri(tablodakiDerslerinKodlari,
				sinavlarVeOgrenciSayilari, sinavlarVeSaatleri);

		if (sinavlarVeDerslikleri == null)
			return;

		sinavProgramiVerileriniTemizle();

		sinavPrograminiVeritabaninaKaydet(tablodakiDerslerinKodlari, tarihlerVeSinavlar, sinavlarVeSaatleri,
				sinavlarVeDerslikleri);
	}

	private static List<String> getTablodakiDerslerinKodlari() {

		SinavProgramiOlusturmaEkrani ekran = SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani();

		DefaultTableModel model = (DefaultTableModel) ekran.getDersTablosu().getModel();
		List<String> tablodakiDerslerinKodlari = new ArrayList<String>();

		for (int i = 0; i < model.getRowCount(); i++) {

			Object deger = model.getValueAt(i, 0);

			if (deger != null)
				tablodakiDerslerinKodlari.add(deger.toString());
		}

		return tablodakiDerslerinKodlari;
	}

	private static List<String> getSinavYapilacakTarihler() {

		SinavProgramiOlusturmaEkrani ekran = SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani();

		Date baslangicTarihi = ekran.getBaslangicTarihiSecimi().getDate();
		Date bitisTarihi = ekran.getBitisTarihiSecimi().getDate();

		if (baslangicTarihi == null || bitisTarihi == null) {

			JOptionPane.showMessageDialog(null, "Lütfen başlangıç ve bitiş tarihleriniz giriniz", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}

		LocalDate localBaslangicTarihi = baslangicTarihi.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localBitisTarihi = bitisTarihi.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (localBaslangicTarihi.isAfter(localBitisTarihi)) {

			JOptionPane.showMessageDialog(null, "Başlangıç tarihi, bitiş tarihinden sonra olamaz", "Hatalı giriş",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		Set<DayOfWeek> cikarilacakGunler = new HashSet<>();

		for (JCheckBox c : ekran.getGunSecimleri()) {

			if (c.isSelected()) {

				switch (c.getText()) {

				case "Pazartesi":
					cikarilacakGunler.add(DayOfWeek.MONDAY);
					break;

				case "Salı":
					cikarilacakGunler.add(DayOfWeek.TUESDAY);
					break;

				case "Çarşamba":
					cikarilacakGunler.add(DayOfWeek.WEDNESDAY);
					break;

				case "Perşembe":
					cikarilacakGunler.add(DayOfWeek.THURSDAY);
					break;

				case "Cuma":
					cikarilacakGunler.add(DayOfWeek.FRIDAY);
					break;

				case "Cumartesi":
					cikarilacakGunler.add(DayOfWeek.SATURDAY);
					break;

				case "Pazar":
					cikarilacakGunler.add(DayOfWeek.SUNDAY);
					break;
				}
			}
		}

		List<String> sinavYapilacakTarihler = new ArrayList<>();
		DateTimeFormatter tarihFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

		for (LocalDate tarih = localBaslangicTarihi; !tarih.isAfter(localBitisTarihi); tarih = tarih.plusDays(1)) {

			if (!cikarilacakGunler.contains(tarih.getDayOfWeek()))
				sinavYapilacakTarihler.add(tarih.format(tarihFormat));
		}

		return sinavYapilacakTarihler;
	}

	private static Map<String, Integer> getDerslerVeSinifSeviyeleri(List<String> dersKodlari) {

		Map<String, Integer> derslerVeSinifSeviyeleri = new HashMap<>();

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sql = "SELECT ders_kodu, sinif_seviyesi FROM dersler WHERE ders_kodu = ?";
			PreparedStatement sorgu = baglanti.prepareStatement(sql);

			for (String kod : dersKodlari) {

				sorgu.setString(1, kod);

				try (ResultSet sonuc = sorgu.executeQuery()) {

					if (sonuc.next()) {

						String sinifSeviyesiString = sonuc.getString("sinif_seviyesi");
						int sinifSeviyesiInt = Integer.parseInt(sinifSeviyesiString.replaceAll("\\D+", ""));
						derslerVeSinifSeviyeleri.put(kod, sinifSeviyesiInt);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return derslerVeSinifSeviyeleri;
	}

	private static Map<String, List<String>> getTarihlerVeSinavlar(List<String> tablodakiDerslerinKodlari,
			List<String> sinavYapilacakTarihler, Map<String, Integer> derslerVeSinifSeviyeleri) {

		Map<String, List<String>> tarihlerVeSinavlar = new LinkedHashMap<>();

		for (String gun : sinavYapilacakTarihler)
			tarihlerVeSinavlar.put(gun, new ArrayList<>());

		Map<Integer, List<String>> sinifSeviyesiVeDersler = new HashMap<>();

		for (String ders : tablodakiDerslerinKodlari) {

			int sinif = derslerVeSinifSeviyeleri.getOrDefault(ders, 0);
			sinifSeviyesiVeDersler.computeIfAbsent(sinif, k -> new ArrayList<>()).add(ders);
		}

		for (int sinif = 1; sinif <= 4; sinif++) {

			int sinifFinal = sinif;

			List<String> dersler = sinifSeviyesiVeDersler.get(sinifFinal);

			if (dersler == null)
				continue;

			for (String ders : dersler) {

				String secilenGun = sinavYapilacakTarihler.stream().min((g1, g2) -> {

					int fark = tarihlerVeSinavlar.get(g1).size() - tarihlerVeSinavlar.get(g2).size();

					if (fark != 0)
						return fark;

					boolean g1Var = tarihlerVeSinavlar.get(g1).stream()
							.anyMatch(d -> derslerVeSinifSeviyeleri.get(d).equals(sinifFinal));

					boolean g2Var = tarihlerVeSinavlar.get(g2).stream()
							.anyMatch(d -> derslerVeSinifSeviyeleri.get(d).equals(sinifFinal));

					if (g1Var && !g2Var)
						return 1;

					if (!g1Var && g2Var)
						return -1;

					return 0;

				}).orElse(sinavYapilacakTarihler.get(0));

				tarihlerVeSinavlar.get(secilenGun).add(ders);
			}
		}

		return tarihlerVeSinavlar;
	}

	private static Map<String, Set<String>> getDerslerVeOgrencileri() {

		Map<String, Set<String>> dersVeOgrenciler = new HashMap<>();

		String sql = "SELECT ders_kodu, ogrenci_no FROM ogrenciler";

		try (Connection baglanti = DbBaglantisi.connect();

				PreparedStatement sorgu = baglanti.prepareStatement(sql);
				ResultSet sonuc = sorgu.executeQuery()) {

			while (sonuc.next()) {

				String dersKodu = sonuc.getString("ders_kodu");
				String ogrenciNo = sonuc.getString("ogrenci_no");

				dersVeOgrenciler.computeIfAbsent(dersKodu, k -> new HashSet<>()).add(ogrenciNo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dersVeOgrenciler;
	}

	private static Map<String, Map<String, String>> getSinavlarVeSaatleri(List<String> tablodakiDerslerinKodlari,
			List<String> sinavYapilacakTarihler, Map<String, List<String>> tarihlerVeSinavlar,
			Map<String, Set<String>> dersVeOgrenciler) {

		SinavProgramiOlusturmaEkrani ekran = SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani();

		Date baslangicSaati = (Date) ekran.getBaslangicSaatiAyari().getValue();
		Date bitisSaati = (Date) ekran.getBitisSaatiAyari().getValue();
		LocalTime localBaslangicSaati = baslangicSaati.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
		LocalTime localBitisSaati = bitisSaati.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
		boolean esZamanliSinavIzni = ekran.getEsZamanliSinavSecimi().isSelected();
		int beklemeSuresi = (int) ekran.getBeklemeSuresiAyari().getValue();

		Map<String, Integer> sinavSureleri = new HashMap<>();

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sql = "SELECT ders_kodu, sinav_suresi FROM sinav_sureleri WHERE ders_kodu = ?";
			PreparedStatement sorgu = baglanti.prepareStatement(sql);

			for (String kod : tablodakiDerslerinKodlari) {

				sorgu.setString(1, kod);

				try (ResultSet sonuc = sorgu.executeQuery()) {

					if (sonuc.next())
						sinavSureleri.put(kod, sonuc.getInt("sinav_suresi"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DateTimeFormatter saatFormat = DateTimeFormatter.ofPattern("HH:mm");
		Map<String, Map<String, String>> sinavlarVeSaatleri = new LinkedHashMap<>();

		for (String gun : sinavYapilacakTarihler) {

			List<String> dersler = tarihlerVeSinavlar.get(gun);

			if (dersler == null || dersler.isEmpty())
				continue;

			LocalTime sinavBaslangic = localBaslangicSaati;
			Map<String, String> sinavSaatleri = new LinkedHashMap<>();

			if (esZamanliSinavIzni) {

				for (String ders : dersler) {

					int sure = sinavSureleri.getOrDefault(ders, 75);
					Set<String> ogrenciler = new HashSet<>(dersVeOgrenciler.getOrDefault(ders, Collections.emptySet()));
					LocalTime baslangic = localBaslangicSaati;
					LocalTime bitis = baslangic.plusMinutes(sure);
					boolean uygunZamanBulundu = false;

					while (!uygunZamanBulundu) {

						if (bitis.isAfter(localBitisSaati)) {
							return null;
						}

						boolean cakismaVar = false;
						LocalTime enGecBitis = LocalTime.MIN;

						for (Map.Entry<String, String> entry : sinavSaatleri.entrySet()) {

							String digerDers = entry.getKey();
							Set<String> digerOgrenciler = new HashSet<>(
									dersVeOgrenciler.getOrDefault(digerDers, Collections.emptySet()));
							String[] zaman = entry.getValue().split(" - ");
							LocalTime digerBaslangic = LocalTime.parse(zaman[0], saatFormat);
							LocalTime digerBitis = LocalTime.parse(zaman[1], saatFormat);
							boolean zamanCakismasi = !(bitis.isBefore(digerBaslangic) || baslangic.isAfter(digerBitis));

							if (zamanCakismasi && !Collections.disjoint(ogrenciler, digerOgrenciler)) {

								cakismaVar = true;

								if (digerBitis.isAfter(enGecBitis))
									enGecBitis = digerBitis;
							}
						}

						if (!cakismaVar) {

							String zamanAraligi = baslangic.format(saatFormat) + " - " + bitis.format(saatFormat);
							sinavSaatleri.put(ders, zamanAraligi);
							uygunZamanBulundu = true;

						} else {

							baslangic = enGecBitis.plusMinutes(beklemeSuresi);
							bitis = baslangic.plusMinutes(sure);
						}
					}
				}

			} else {

				for (String ders : dersler) {

					int sure = sinavSureleri.getOrDefault(ders, 75);
					LocalTime sinavBitis = sinavBaslangic.plusMinutes(sure);

					if (sinavBitis.isAfter(localBitisSaati))
						return null;

					String zamanAraligi = sinavBaslangic.format(saatFormat) + " - " + sinavBitis.format(saatFormat);
					sinavSaatleri.put(ders, zamanAraligi);
					sinavBaslangic = sinavBitis.plusMinutes(beklemeSuresi);
				}
			}

			sinavlarVeSaatleri.put(gun, sinavSaatleri);
		}

		return sinavlarVeSaatleri;
	}

	private static Map<String, Integer> getSinavlarVeOgrenciSayilari(List<String> dersKodlari) {

		Map<String, Integer> sinavlarVeOgrenciSayilari = new HashMap<>();

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sql = "SELECT COUNT(*) AS sayi FROM ogrenciler WHERE ders_kodu = ?";
			PreparedStatement sorgu = baglanti.prepareStatement(sql);

			for (String kod : dersKodlari) {

				sorgu.setString(1, kod);

				try (ResultSet sonuc = sorgu.executeQuery()) {

					if (sonuc.next())
						sinavlarVeOgrenciSayilari.put(kod, sonuc.getInt("sayi"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sinavlarVeOgrenciSayilari;
	}

	private static Map<String, List<String>> getSinavlarVeDerslikleri(List<String> dersKodlari,
			Map<String, Integer> ogrenciSayilari, Map<String, Map<String, String>> sinavlarVeSaatleri) {

		int beklemeSuresi = (int) SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().getBeklemeSuresiAyari()
				.getValue();

		Map<String, List<Map<String, Object>>> bolumDerslikleri = new HashMap<>();

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sql = "SELECT bolum_adi, derslik_kodu, derslik_adi, derslik_kapasitesi FROM derslikler";
			Statement sorgu = baglanti.createStatement();
			ResultSet sonuc = sorgu.executeQuery(sql);

			while (sonuc.next()) {

				String bolum = sonuc.getString("bolum_adi");
				Map<String, Object> derslik = new HashMap<>();
				derslik.put("kod", sonuc.getString("derslik_kodu"));
				derslik.put("ad", sonuc.getString("derslik_adi"));
				derslik.put("kapasite", sonuc.getInt("derslik_kapasitesi"));
				bolumDerslikleri.computeIfAbsent(bolum, k -> new ArrayList<>()).add(derslik);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		Map<String, List<String>> sinavlarVeDerslikleri = new HashMap<>();
		List<String> kapasiteYetmeyenDersler = new ArrayList<>();
		Map<String, Set<String>> saatVeKullanilanDerslikler = new HashMap<>();

		DateTimeFormatter saatFormat = DateTimeFormatter.ofPattern("HH:mm");

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sqlBolum = "SELECT bolum_adi FROM ogrenciler WHERE ders_kodu = ? LIMIT 1";
			PreparedStatement sorguBolum = baglanti.prepareStatement(sqlBolum);

			String sqlSure = "SELECT sinav_suresi FROM sinav_sureleri WHERE ders_kodu = ?";
			PreparedStatement sorguSure = baglanti.prepareStatement(sqlSure);

			for (String dersKodu : dersKodlari) {

				sorguBolum.setString(1, dersKodu);
				String bolumAdi = null;

				try (ResultSet sonucBolum = sorguBolum.executeQuery()) {
					if (sonucBolum.next())
						bolumAdi = sonucBolum.getString("bolum_adi");
				}

				if (bolumAdi == null || !bolumDerslikleri.containsKey(bolumAdi))
					continue;

				int sinavSuresi = 75;
				sorguSure.setString(1, dersKodu);

				try (ResultSet sonucSure = sorguSure.executeQuery()) {

					if (sonucSure.next())
						sinavSuresi = sonucSure.getInt("sinav_suresi");
				}

				int ogrenciSayisi = ogrenciSayilari.getOrDefault(dersKodu, 0);
				List<Map<String, Object>> derslikler = new ArrayList<>(bolumDerslikleri.get(bolumAdi));

				String saatKey = null;
				String gun = null;

				for (Map.Entry<String, Map<String, String>> gunEntry : sinavlarVeSaatleri.entrySet()) {

					if (gunEntry.getValue().containsKey(dersKodu)) {

						gun = gunEntry.getKey();
						saatKey = gun + " " + gunEntry.getValue().get(dersKodu);
						break;
					}
				}

				if (saatKey == null)
					continue;

				Set<String> kullanilamaz = saatVeKullanilanDerslikler.getOrDefault(saatKey, new HashSet<>());
				List<String> secilenDerslikler = new ArrayList<>();
				int kalan = ogrenciSayisi;

				derslikler.sort(Comparator.comparingInt(d -> (int) d.get("kapasite")));

				boolean tekBasinaAlindi = false;

				for (Map<String, Object> d : derslikler) {

					int kapasite = (int) d.get("kapasite");
					String ad = (String) d.get("ad");

					if (!kullanilamaz.contains(ad) && kapasite >= kalan) {

						secilenDerslikler.add(ad);
						kalan = 0;
						tekBasinaAlindi = true;
						break;
					}
				}

				if (kalan > 0 && !tekBasinaAlindi) {

					while (kalan > 0) {

						Map<String, Object> secilen = null;
						int minFazla = Integer.MAX_VALUE;

						for (Map<String, Object> d : derslikler) {

							String ad = (String) d.get("ad");

							if (kullanilamaz.contains(ad) || secilenDerslikler.contains(ad))
								continue;

							int kapasite = (int) d.get("kapasite");
							int fazla = kapasite - kalan;

							if (fazla >= 0 && fazla < minFazla) {

								minFazla = fazla;
								secilen = d;
							}
						}

						if (secilen == null) {

							Map<String, Object> enBuyuk = null;
							int enBuyukK = -1;

							for (Map<String, Object> d : derslikler) {

								String ad = (String) d.get("ad");

								if (kullanilamaz.contains(ad) || secilenDerslikler.contains(ad))
									continue;

								int kapasite = (int) d.get("kapasite");

								if (kapasite > enBuyukK) {
									enBuyukK = kapasite;
									enBuyuk = d;
								}
							}

							if (enBuyuk == null)
								break;
							secilen = enBuyuk;
						}

						secilenDerslikler.add((String) secilen.get("ad"));
						kalan -= (int) secilen.get("kapasite");
					}
				}

				if (kalan > 0) {

					LocalTime enSonBitis = LocalTime.MIN;
					Map<String, String> gunSinavlari = sinavlarVeSaatleri.getOrDefault(gun, Map.of());

					for (Map.Entry<String, String> entry : gunSinavlari.entrySet()) {

						String[] zaman = entry.getValue().split(" - ");

						try {

							LocalTime bitis = LocalTime.parse(zaman[1], saatFormat);
							if (bitis.isAfter(enSonBitis))
								enSonBitis = bitis;

						} catch (Exception ex) {

						}
					}

					LocalTime yeniBaslangic = enSonBitis.plusMinutes(beklemeSuresi);
					LocalTime yeniBitis = yeniBaslangic.plusMinutes(sinavSuresi);
					String yeniSaatAraligi = yeniBaslangic.format(saatFormat) + " - " + yeniBitis.format(saatFormat);

					sinavlarVeSaatleri.get(gun).put(dersKodu, yeniSaatAraligi);

					kullanilamaz = new HashSet<>();

					secilenDerslikler.clear();
					kalan = ogrenciSayisi;

					tekBasinaAlindi = false;

					for (Map<String, Object> d : derslikler) {

						int kapasite = (int) d.get("kapasite");
						String ad = (String) d.get("ad");

						if (kapasite >= kalan) {

							secilenDerslikler.add(ad);
							kalan = 0;
							tekBasinaAlindi = true;
							break;
						}
					}

					if (kalan > 0 && !tekBasinaAlindi) {

						while (kalan > 0) {

							Map<String, Object> secilen = null;
							int minFazla = Integer.MAX_VALUE;

							for (Map<String, Object> d : derslikler) {

								String ad = (String) d.get("ad");

								if (secilenDerslikler.contains(ad))
									continue;

								int kapasite = (int) d.get("kapasite");
								int fazla = kapasite - kalan;

								if (fazla >= 0 && fazla < minFazla) {

									minFazla = fazla;
									secilen = d;
								}
							}

							if (secilen == null) {

								Map<String, Object> enBuyuk = null;
								int enBuyukK = -1;

								for (Map<String, Object> d : derslikler) {

									String ad = (String) d.get("ad");

									if (secilenDerslikler.contains(ad))
										continue;

									int kapasite = (int) d.get("kapasite");

									if (kapasite > enBuyukK) {

										enBuyukK = kapasite;
										enBuyuk = d;
									}
								}

								if (enBuyuk == null)
									break;

								secilen = enBuyuk;
							}

							secilenDerslikler.add((String) secilen.get("ad"));
							kalan -= (int) secilen.get("kapasite");
						}
					}
				}

				if (kalan > 0) {
					kapasiteYetmeyenDersler.add(dersKodu + " (" + ogrenciSayisi + " öğrenci)");
				}

				sinavlarVeDerslikleri.put(dersKodu, secilenDerslikler);
				saatVeKullanilanDerslikler.put(saatKey, new HashSet<>(secilenDerslikler));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (!kapasiteYetmeyenDersler.isEmpty()) {
			StringBuilder metin = new StringBuilder();
			metin.append("Aşağıdaki dersler için derslik kapasiteleri yetersiz:\n\n");
			for (String d : kapasiteYetmeyenDersler)
				metin.append("- ").append(d).append("\n");

			JTextArea hataMesajiAlani = new JTextArea(metin.toString());
			hataMesajiAlani.setEditable(false);
			hataMesajiAlani.setLineWrap(true);
			hataMesajiAlani.setWrapStyleWord(true);

			JScrollPane kaydirmaCubugu = new JScrollPane(hataMesajiAlani);
			kaydirmaCubugu.setPreferredSize(new java.awt.Dimension(400, 300));

			JOptionPane.showMessageDialog(null, kaydirmaCubugu, "Kapasite Hatası", JOptionPane.WARNING_MESSAGE);
			return null;
		}

		return sinavlarVeDerslikleri;
	}

	private static void sinavPrograminiVeritabaninaKaydet(List<String> tablodakiDerslerinKodlari,
			Map<String, List<String>> tarihlerVeSinavlar, Map<String, Map<String, String>> sinavlarVeSaatleri,
			Map<String, List<String>> derslikAtamalari) {

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sql1 = "INSERT INTO sinav_programi (sinav_tarihi, sinav_saati, ders_adi, ogretim_elemani, derslik_adi) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement sorgu1 = baglanti.prepareStatement(sql1);

			Map<String, String[]> dersBilgileri = new HashMap<>();

			String sql2 = "SELECT ders_kodu, ders_adi, ders_hocasi FROM dersler WHERE ders_kodu = ?";
			PreparedStatement sorgu2 = baglanti.prepareStatement(sql2);

			for (String kod : tablodakiDerslerinKodlari) {

				sorgu2.setString(1, kod);

				try (ResultSet sonuc = sorgu2.executeQuery()) {

					if (sonuc.next())
						dersBilgileri.put(kod,
								new String[] { sonuc.getString("ders_adi"), sonuc.getString("ders_hocasi") });
				}
			}

			for (String gun : tarihlerVeSinavlar.keySet()) {

				List<String> dersler = tarihlerVeSinavlar.get(gun);

				if (dersler == null || dersler.isEmpty())
					continue;

				java.sql.Date tarihSQL = java.sql.Date
						.valueOf(LocalDate.parse(gun, DateTimeFormatter.ofPattern("dd.MM.yyyy")));

				for (String dersKodu : dersler) {

					String sinavSaati = sinavlarVeSaatleri.getOrDefault(gun, Collections.emptyMap())
							.getOrDefault(dersKodu, "-");

					String[] bilgiler = dersBilgileri.getOrDefault(dersKodu,
							new String[] { "Bilinmeyen Ders", "Bilinmeyen Hoca" });
					String dersAdi = bilgiler[0];
					String ogretimElemani = bilgiler[1];

					List<String> derslikler = derslikAtamalari.getOrDefault(dersKodu, List.of("Atanmadı"));
					String derslikAdi = String.join(", ", derslikler);

					sorgu1.setDate(1, tarihSQL);
					sorgu1.setString(2, sinavSaati);
					sorgu1.setString(3, dersAdi);
					sorgu1.setString(4, ogretimElemani);
					sorgu1.setString(5, derslikAdi);

					sorgu1.addBatch();
				}
			}

			sorgu1.executeBatch();
			SinavProgramiGoruntulemeEkrani.getSinavProgramiGoruntulemeEkrani().sinavProgramiTablosunuYenile();
			SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().getSinavProgramiOlusturmaPaneli()
					.setVisible(false);
			SinavProgramiGoruntulemeEkrani.getSinavProgramiGoruntulemeEkrani().getSinavProgramiGoruntulemePaneli()
					.setVisible(true);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void sinavProgramiVerileriniTemizle() {

		String sql = "DELETE FROM sinav_programi";

		try (Connection baglanti = DbBaglantisi.connect(); PreparedStatement sonuc = baglanti.prepareStatement(sql)) {

			sonuc.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void dersCikarButonuKontrol() {

		int[] seciliSatirlar = SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().getDersTablosu()
				.getSelectedRows();
		DefaultTableModel model = (DefaultTableModel) SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani()
				.getDersTablosu().getModel();

		if (seciliSatirlar.length == 0) {

			JOptionPane.showMessageDialog(null, "Lütfen çıkarmak için önce ders seçiniz", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		for (int i = seciliSatirlar.length - 1; i >= 0; i--)
			model.removeRow(seciliSatirlar[i]);

		JOptionPane.showMessageDialog(null, "Çıkarma işlemi başarıyla tamamlandı", "İşlem Başarılı",
				JOptionPane.INFORMATION_MESSAGE);

		SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().getDersTablosu().clearSelection();
	}

	public static void sureDegistirmeButonuKontrol() {

		int[] seciliSatirlar = SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().getDersTablosu()
				.getSelectedRows();

		if (seciliSatirlar.length == 0) {
			JOptionPane.showMessageDialog(null, "Lütfen süresini değiştirmek için önce ders seçiniz", "Hatalı giriş",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		JDialog dialog = new JDialog();
		dialog.setTitle("Sınav Süresi Değiştir");
		dialog.setSize(300, 200);
		dialog.setLayout(null);
		dialog.setModal(true);
		dialog.setLocationRelativeTo(null);

		JLabel etiket = new JLabel("Yeni sınav süresi (dk):");
		etiket.setBounds(40, 30, 200, 30);
		etiket.setFont(new Font("Serif", Font.BOLD, 16));
		dialog.add(etiket);

		JSpinner sureSpinner = new JSpinner(new SpinnerNumberModel(75, 10, 300, 5));
		sureSpinner.setBounds(40, 70, 100, 30);
		sureSpinner.setFont(new Font("Serif", Font.BOLD, 16));
		((JSpinner.DefaultEditor) sureSpinner.getEditor()).getTextField().setEditable(true);
		dialog.add(sureSpinner);

		JButton onaylaButonu = new JButton("Onayla");
		onaylaButonu.setBounds(160, 70, 90, 30);
		onaylaButonu.setFocusable(false);
		dialog.add(onaylaButonu);

		onaylaButonu.addActionListener(e -> {
			int yeniSure = (int) sureSpinner.getValue();

			try (Connection baglanti = DbBaglantisi.connect()) {

				String bolumAdi = GirisEkrani.getGirisEkrani().getAktifKullaniciBolumu();

				DefaultTableModel model = (DefaultTableModel) SinavProgramiOlusturmaEkrani
						.getSinavProgramiOlusturmaEkrani().getDersTablosu().getModel();

				String sql = "UPDATE sinav_sureleri SET sinav_suresi = ? WHERE ders_kodu = ? AND bolum_adi = ?";

				try (PreparedStatement sorgu = baglanti.prepareStatement(sql)) {

					for (int satir : seciliSatirlar) {

						String dersKodu = model.getValueAt(satir, 0).toString();

						sorgu.setInt(1, yeniSure);
						sorgu.setString(2, dersKodu);
						sorgu.setString(3, bolumAdi);
						sorgu.executeUpdate();
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			dialog.dispose();
		});

		dialog.setVisible(true);
	}

	public static void geriDonButonuKontrol() {

		SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().getSinavProgramiOlusturmaPaneli()
				.setVisible(false);
		KullaniciEkrani.getKullaniciEkrani().getKullaniciPaneli().setVisible(true);
		SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().sinavProgramiOlusturmaEkraniTemizle();
		SinavProgramiOlusturmaEkrani.getSinavProgramiOlusturmaEkrani().dersTablosunuYenile();

		try (Connection baglanti = DbBaglantisi.connect()) {

			String sql = "UPDATE sinav_sureleri SET sinav_suresi = 75";

			try (PreparedStatement sorgu = baglanti.prepareStatement(sql)) {

				sorgu.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
}