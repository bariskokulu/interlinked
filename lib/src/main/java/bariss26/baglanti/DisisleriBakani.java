package bariss26.baglanti;

import java.util.ArrayList;

public class DisisleriBakani {

	public static ArrayList<Musteri> musteriler = new ArrayList<Musteri>();
	public static ArrayList<Sunucu> sunucular = new ArrayList<Sunucu>();

	public static Sunucu sunucu(int port, boolean gelen, boolean giden) {
		Sunucu s;
		sunucular.add(s = new Sunucu(port, gelen, giden));
		return s;
	}

	public static Musteri musteri(String ip, int port, boolean gelen, boolean giden) {
		Musteri m;
		musteriler.add(m = new Musteri(ip, port, gelen, giden));
		return m;
	}

}
