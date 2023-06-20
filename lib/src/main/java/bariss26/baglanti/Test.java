package bariss26.baglanti;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
		ByteKumesi bk = new ByteKumesi();
		for(int i = 0; i<1000; i++) {
			char a;
			char c;
			boolean b;
			bk.charYaz(a = (char)-new Random().nextInt());
			b = (c=bk.charOku())==a;
			if(!b) {
				System.out.println((int)a+" "+(int)c);
			}
		}
		for(int i = 0; i<1000; i++) {
			byte a;
			byte c;
			boolean b;
			bk.byteYaz(a = (byte)-new Random().nextInt());
			b = (c=bk.byteOku())==a;
			if(!b) {
				System.out.println((int)a+" "+(int)c);
			}
		}
		for(int i = 0; i<1000; i++) {
			short a;
			short c;
			boolean b;
			bk.shortYaz(a = (short)-new Random().nextInt());
			b = (c=bk.shortOku())==a;
			if(!b) {
				System.out.println((int)a+" "+(int)c);
			}
		}
		for(int i = 0; i<1000; i++) {
			double a;
			double c;
			boolean b;
			bk.doubleYaz(a = -new Random().nextDouble());
			b = (c=bk.doubleOku())==a;
			if(!b) {
				System.out.println((int)a+" "+(int)c);
			}
		}
		for(int i = 0; i<1000; i++) {
			float a;
			float c;
			boolean b;
			bk.floatYaz(a = -new Random().nextFloat());
			b = (c=bk.floatOku())==a;
			if(!b) {
				System.out.println((int)a+" "+(int)c);
			}
		}
		for(int i = 0; i<1000; i++) {
			long a;
			long c;
			boolean b;
			bk.longYaz(a = -new Random().nextLong());
			b = (c=bk.longOku())==a;
			if(!b) {
				System.out.println((int)a+" "+(int)c);
			}
		}
		for(int i = 0; i<1000; i++) {
			int a;
			int c;
			boolean b;
			bk.intYaz(a = -new Random().nextInt());
			b = (c=bk.intOku())==a;
			if(!b) {
				System.out.println((int)a+" "+(int)c);
			}
		}
	}
	
}
