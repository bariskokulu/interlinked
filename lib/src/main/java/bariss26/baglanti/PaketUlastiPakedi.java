package bariss26.baglanti;

public class PaketUlastiPakedi extends Paket {

	public long ulasanPaket;

	@Override
	public void yaz(ByteKumesi b) {
		b.longYaz(ulasanPaket);
	}

	@Override
	public void oku(ByteKumesi b) {
		ulasanPaket = b.longOku();
	}

	@Override
	public void isle(Baglanti b) {
		for(Paket p : b.gidecek) {
			if(p.___kimlik==ulasanPaket) p.___ulasti = true;
		}
	}

}
