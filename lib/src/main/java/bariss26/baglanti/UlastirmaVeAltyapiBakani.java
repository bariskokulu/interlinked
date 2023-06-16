package bariss26.baglanti;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UlastirmaVeAltyapiBakani {

	public static HashMap<Class<? extends Paket>, Integer> paketler = new HashMap<Class<? extends Paket>, Integer>();
	public static Map<Integer, Class<? extends Paket>> paketlerTers = new HashMap<Integer, Class<? extends Paket>>();
	public static final String paketAyraci = "#paketAyraci---abcçdefgğhıijklmnöoprsştuüvyz-daha-ne-kadar-uzun-olmasi-gerekiyor-ki-bunun-anlayamiyorum-ABCÇDEFGĞHIİKLMNOÖPRSŞTUÜVYZ---paketAyraci*";
	public static byte[] paketAyraciBytelar;
	
	public static void firla() {
		paketlerTers = paketler.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
		paketAyraciBytelar = paketAyraci.getBytes();
	}
	
	public static void paketKaydet(int paketTur, Class<? extends Paket> c) {
		paketler.put(c, paketTur);
	}
	
}
