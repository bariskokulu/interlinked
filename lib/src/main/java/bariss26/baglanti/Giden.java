package bariss26.baglanti;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Iterator;

public class Giden extends Thread {

	Baglanti b;
	DataOutputStream yol;
	
	public Giden(Baglanti b) {
		super(b.s.getInetAddress()+"-giden");
		this.b = b;
		try {
			this.yol = new DataOutputStream(b.s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			b.kapat();
		}
	}
	
	@Override
	public void run() {
		Paket p;
		ByteKumesi bytelar = new ByteKumesi();
		try {
			yol.write(UlastirmaVeAltyapiBakani.paketAyraciBytelar);
		} catch (IOException e) {
			b.kapat();
			throw new RuntimeException(e);
		}
		while(!b.bitti) {
			Iterator<Paket> i = b.gidecek.iterator();
			while(i.hasNext()) {
				bytelar.sifirla();
				p = i.next();
				if(p.___ulasti||p.___deneme>=p.maxDeneme()) {
					i.remove();
					continue;
				}
				try {
					yol.writeInt(UlastirmaVeAltyapiBakani.paketler.get(p.getClass()));
					yol.writeLong(p.___kimlik);
					p.yaz(bytelar);
					yol.writeInt(bytelar.boyut());
					yol.write(bytelar.kume());
					yol.write(UlastirmaVeAltyapiBakani.paketAyraciBytelar);
					p.___deneme++;
				} catch (Exception e) {
					e.printStackTrace();
					if(e instanceof SocketException) b.kapat();
				}
			}
		}
		b.gidecek.clear();
	}
	
}
