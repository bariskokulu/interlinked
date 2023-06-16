package bariss26.baglanti;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Baglanti {

	public Socket s;
	public ConcurrentLinkedQueue<Paket> gidecek = new ConcurrentLinkedQueue<Paket>();
	public Thread gelen, giden;
	public boolean bitti, tekrarbaglan;
	public Object[] bilgi = new Object[16];
	public DataInputStream gel;
	public DataOutputStream git;
	public long tekrarbaglanmadanbekle;
	public Musteri m;

	public Baglanti(Socket s, boolean gelen, boolean giden) {
		this.s = s;
		if(gelen) gelen();
		if(giden) giden();
		try {
			gel = new DataInputStream(s.getInputStream());
			git = new DataOutputStream(s.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			kapat();
			m.tekrarBaglanmakIstiyonMu();
		}
	}

	public Baglanti gelen() {
		gelen = new Gelen(this);
		gelen.start();
		gelen.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				if(!bitti) gelen.start();
			}
		});
		return this;
	}

	public Baglanti giden() {
		giden = new Giden(this);
		giden.start();
		giden.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				if(!bitti) giden.start();
			}
		});
		return this;
	}

	public <P extends Paket> void paketGonder(P p) {
		p.___kimlik = System.currentTimeMillis();
		gidecek.add(p);
	}

	public Object bilgi(int i) {
		return bilgi[i];
	}

	public Object bilgi(int i, Object o) {
		return bilgi[i] = o;
	}

	public void kapat() {
		try {
			bitti = true;
			if(s!=null) {
				s.close();
			}
		} catch (Exception e) {}
	}

}
