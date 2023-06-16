package bariss26.baglanti;

public abstract class Paket {

	public long ___kimlik;
	
	public boolean ___ulasti;
	
	public long ___songonderim;
	
	public int ___deneme;

	public int ___uzunluk;
	
	public ByteKumesi ___bytelar;
	
	public abstract void yaz(ByteKumesi b);
	
	public abstract void oku(ByteKumesi b);
	
	public abstract void isle(Baglanti b);
	
	public long gonderimlerArasi() {
		return 1000L;
	}

	public int maxDeneme() {
		return 1;
	}
	
}
