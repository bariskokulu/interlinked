package bariss26.baglanti;

public class GenclikVeSporBakani {

	public static void baglantiKuruldu(DinleyicimVar dv, Baglanti b) {
		try {
			dv.dinleyiciler.forEach(d -> d.baglantiKuruldu(b));
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void baglantiHatasi(DinleyicimVar dv, Baglanti b, Exception e) {
		try {
			dv.dinleyiciler.forEach(d -> d.baglantiHatasi(b, e));
		} catch (Exception ee) { ee.printStackTrace(); }
	}
	
}
