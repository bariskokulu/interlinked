package bariss26.baglanti;

import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

public class Musteri extends DinleyicimVar {

	public String ip;
	public int port;
	public Baglanti b;
	public boolean tekrarbaglan, gelen, giden;
	public long tekrarbaglanmadanbekle;

	public Musteri(String ip, int port, boolean gelen, boolean giden) {
		this.ip = ip;
		this.port = port;
		this.gelen = gelen;
		this.giden = giden;
		firla();
	}

	void firla() {
		new Thread("musteri-"+ip+":"+port) {
			@Override
			public void run() {
				try {
					if(b!=null) {
						b.bitti = true;
						b.s.close();
					}
					b = new Baglanti(new Socket(ip, port), gelen, giden);
					GenclikVeSporBakani.baglantiKuruldu(Musteri.this, b);
				} catch (Exception e) {
					e.printStackTrace();
					try {
						if(b!=null) b.kapat();
					} catch (Exception ee) {
						ee.printStackTrace();
					}
					GenclikVeSporBakani.baglantiHatasi(Musteri.this, b, e);
					tekrarBaglanmakIstiyonMu();
				}
			}
		}.start();
	}
	
	public void tekrarBaglan(long tekrarbaglanmadanbekle) {
		this.tekrarbaglan = true;
		this.tekrarbaglanmadanbekle = tekrarbaglanmadanbekle;
	}
	
	public void tekrarBaglanmakIstiyonMu() {
		if(tekrarbaglan) {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					if(!suAnBagliMisin()) firla();
				}
			}, tekrarbaglanmadanbekle);
		}
	}
	
	public boolean suAnBagliMisin() {
		return b!=null&&!b.bitti&&b.s!=null&&!b.s.isClosed()&&b.s.isConnected();
	}

	public void yeniDinleyici(Dinleyici d) {
		dinleyiciler.add(d);
	}


}
