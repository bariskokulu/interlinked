package bariss26.baglanti;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Sunucu extends DinleyicimVar {

	public int port;
	public ServerSocket ss;
	public HashMap<String, Baglanti> baglantilar = new HashMap<String, Baglanti>();

	public Sunucu(int port, boolean gelen, boolean giden) {
		firla(this.port=port, gelen, giden);
	}
	
	void firla(int port, boolean gelen, boolean giden) {
		Thread t = new Thread("sunucu-"+port) {
			@Override
			public void run() {
				try {
					ss = new ServerSocket(port);
					Baglanti diger;
					while(true) {
						Baglanti b = null;
						try {
							Iterator<Entry<String, Baglanti>> it = baglantilar.entrySet().iterator();
							Map.Entry<String, Baglanti> e;
							b = new Baglanti(ss.accept(), gelen, giden);
							while(it.hasNext()) {
								e = (Entry<String, Baglanti>) it.next();
								if(e.getValue().bitti||e.getKey().equals(b.s.getInetAddress().toString())) {
									diger = e.getValue();
									diger.bitti = true;
									it.remove();
								}
							}
							baglantilar.put(b.s.getInetAddress().toString(), b);
							GenclikVeSporBakani.baglantiKuruldu(Sunucu.this, b);
						} catch (Exception e) {
							e.printStackTrace();
							GenclikVeSporBakani.baglantiHatasi(Sunucu.this, b, e);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(ss!=null) {
					try {
						ss.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				baglantilar.clear();
				firla(port, gelen, giden);
			}
		};
		t.start();
	}
	
	public void yeniDinleyici(Dinleyici d) {
		dinleyiciler.add(d);
	}
	
	
}
