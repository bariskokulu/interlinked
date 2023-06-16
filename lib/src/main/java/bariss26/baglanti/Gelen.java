package bariss26.baglanti;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class Gelen extends Thread {

	Baglanti b;
	DataInputStream yol;
	ByteKumesi paketAyraci = new ByteKumesi();
	int ayracIlerleme;
	ByteKumesi bytelar = new ByteKumesi();
	int hata;

	public Gelen(Baglanti b) {
		super(b.s.getInetAddress()+"-gelen");
		this.b = b;
		try {
			yol = new DataInputStream(b.s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			b.kapat();
		}
	}

	@Override
	public void run() {
		Paket p = null;
		byte bb;
		int pakettur;
		while(!b.bitti) {
			try {
				bb = yol.readByte();
				bytelar.byteYaz(bb);	
				if(bytelar.boyut()>=UlastirmaVeAltyapiBakani.paketAyraciBytelar.length&&ayrac()) {
					try {
						for(int a = 0; a<UlastirmaVeAltyapiBakani.paketAyraciBytelar.length; a++) {
							bytelar.bytelar.remove(bytelar.boyut()-1);
						}
						if((pakettur = bytelar.intOku())==0) continue;
						p = UlastirmaVeAltyapiBakani.paketlerTers.get(pakettur).getDeclaredConstructor().newInstance();
						p.___kimlik = bytelar.longOku();
						p.___uzunluk = bytelar.intOku();
						p.oku(bytelar);
						p.isle(b); 
					} catch (Exception e) {
						e.printStackTrace();
					}
					bytelar.sifirla();
				}
			} catch(Exception e) {
				e.printStackTrace();
				hata++;
				b.kapat();
			}
		}
	}

	boolean ayrac() {
		for(int i = 0; i<UlastirmaVeAltyapiBakani.paketAyraciBytelar.length; i++) {
			if(bytelar.bytelar.get(bytelar.boyut()-i-1)!=UlastirmaVeAltyapiBakani.paketAyraciBytelar[UlastirmaVeAltyapiBakani.paketAyraciBytelar.length-i-1]) {
				return false;
			}
		}
		return true;
	}

}
