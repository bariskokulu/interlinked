package bariss26.baglanti;

import java.io.DataInputStream;
import java.util.ArrayList;

import com.google.common.primitives.Bytes;

public class ByteKumesi {

	public ArrayList<Byte> bytelar = new ArrayList<Byte>();
	
	public void sifirla() {
		ilerleme = 0;
		bytelar.clear();
	}

	
	
	
	/*
	 * 
	 * 		YAZ
	 * 
	 */
	
	
	
    public synchronized void yaz(byte b) {
    	bytelar.add(b);
    }

    public synchronized void yaz(byte b[], int off, int len) {
    	for(int i = off; i<off+len&&i<b.length; i++) bytelar.add(b[i]);
    }

    public final void booleanYaz(boolean v) {
    	bytelar.add(v ? (byte)1 : (byte)0);
    }

    public final void byteYaz(byte v) {
    	bytelar.add(v);
    }

    public final void shortYaz(short s) {
    	bytelar.add((byte)(s >>> 8));
    	bytelar.add((byte)(s >>> 0));
    }

    public final void charYaz(char c) {
    	bytelar.add((byte)(c >>> 8));
    	bytelar.add((byte)(c >>> 0));
    }

    public final void intYaz(int i) {
    	bytelar.add((byte)(i >>> 24));
    	bytelar.add((byte)(i >>> 16));
    	bytelar.add((byte)(i >>>  8));
    	bytelar.add((byte)(i >>>  0));
    }

    public final void longYaz(long l) {
    	bytelar.add((byte)(l >>> 56));
    	bytelar.add((byte)(l >>> 48));
    	bytelar.add((byte)(l >>> 40));
    	bytelar.add((byte)(l >>> 32));
    	bytelar.add((byte)(l >>> 24));
    	bytelar.add((byte)(l >>> 16));
    	bytelar.add((byte)(l >>>  8));
    	bytelar.add((byte)(l >>>  0));
    }

    public final void floatYaz(float f) {
        intYaz(Float.floatToIntBits(f));
    }

    public final void doubleYaz(double d) {
        longYaz(Double.doubleToLongBits(d));
    }

    public final void bytelariYaz(String s) {
        int len = s.length();
        for (int i = 0 ; i < len ; i++) {
        	bytelar.add((byte)s.charAt(i));
        }
    }

    public final void charlariYaz(String s) {
        int len = s.length();
        for (int i = 0 ; i < len ; i++) {
            int c = s.charAt(i);
        	bytelar.add((byte)(c >>> 8));
        	bytelar.add((byte)(c >>> 0));
        }
    }

    public final void utfYaz(String str) {
    	utfYaz(str, bytelar);
    }

    static int utfYaz(String str, ArrayList<Byte> bytelar) {
    	if(str==null) str = "";
        final int strlen = str.length();
        int utflen = strlen; // optimized for ASCII

        for (int i = 0; i < strlen; i++) {
            int c = str.charAt(i);
            if (c >= 0x80 || c == 0)
                utflen += (c >= 0x800) ? 2 : 1;
        }

        // TODO
        if (utflen > 65535 || /* overflow */ utflen < strlen) return 0;
//            throw new UTFDataFormatException(tooLongMsg(str, utflen));

        final byte[] bytearr;
//        if (out instanceof DataOutputStream dos) {
//            if (dos.bytearr == null || (dos.bytearr.length < (utflen + 2)))
//                dos.bytearr = new byte[(utflen*2) + 2];
//            bytearr = dos.bytearr;
//        } else {
            bytearr = new byte[utflen + 2];
//        }

        int count = 0;
        bytearr[count++] = (byte) ((utflen >>> 8) & 0xFF);
        bytearr[count++] = (byte) ((utflen >>> 0) & 0xFF);

        int i = 0;
        for (i = 0; i < strlen; i++) { // optimized for initial run of ASCII
            int c = str.charAt(i);
            if (c >= 0x80 || c == 0) break;
            bytearr[count++] = (byte) c;
        }

        for (; i < strlen; i++) {
            int c = str.charAt(i);
            if (c < 0x80 && c != 0) {
                bytearr[count++] = (byte) c;
            } else if (c >= 0x800) {
                bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                bytearr[count++] = (byte) (0x80 | ((c >>  6) & 0x3F));
                bytearr[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
            } else {
                bytearr[count++] = (byte) (0xC0 | ((c >>  6) & 0x1F));
                bytearr[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
            }
        }
        for(int bbb = 0; bbb<utflen+2; bbb++) bytelar.add(bytearr[bbb]);
        return utflen + 2;
    }
    
    
    
    
    /*
     * 
     * 		OKU
     * 
     */
    
    
    int ilerleme;
//    private byte bytearr[] = new byte[80];
//    private char chararr[] = new char[80];
    
    
    public boolean okunabilir() {
    	return ilerleme<bytelar.size();
    }
    
    public byte okuIlerle() {
    	if(okunabilir()) {
    		return bytelar.get(ilerleme++);
    	}
    	return 0;
    }
    
    
    
    
    

//    public final int read(byte b[]) {
//        return in.read(b, 0, b.length);
//    }
//
//    public final int read(byte b[], int off, int len) {
//        return in.read(b, off, len);
//    }
//
//    public final void hepsiniOku(byte b[]) {
//        hepsiniOku(b, 0, b.length);
//    }
//
    public final void hepsiniOku(byte b[], int off, int len) {
    	for(int i = 0; i<len&&off+i<b.length; i++) {
    		b[off+i] = okuIlerle();
    	}
    	
//        Objects.checkFromIndexSize(off, len, b.length);
//        int n = 0;
//        while (n < len) {
//            int count = in.read(b, off + n, len - n);
//            if (count < 0)
//                throw new EOFException();
//            n += count;
//        }
    }

    public final int byteAtla(int n) {
    	int atlanan = 0;
    	while(n>0&&ilerleme<bytelar.size()) {
    		ilerleme++;
    		atlanan++;
    	}
//        int total = 0;
//        int cur = 0;
//
//        while ((total<n) && ((cur = (int) in.skip(n-total)) > 0)) {
//            total += cur;
//        }
//
        return atlanan;
    }

    public final boolean booleanOku() {
        return (okuIlerle() != 0);
    }

    public final byte byteOku() {
        return okuIlerle();
    }

    public final int isaretsizByteOku() {
        return okuIlerle();
    }

    public final short shortOku() {
        return (short)(((okuIlerle() & 0xFF) << 8) + ((okuIlerle() & 0xFF) << 0));
    }

    public final int isaretsizShortOku() {
        return ((okuIlerle()&0xFF) << 8) + ((okuIlerle() & 0xFF) << 0);
    }

    public final char charOku() {
        return (char)(((okuIlerle() & 0xFF) << 8) + ((okuIlerle() & 0xFF) << 0));
    }

    public final int intOku() {
        return (((okuIlerle() & 0xFF) << 24) + ((okuIlerle() & 0xFF) << 16) + ((okuIlerle() & 0xFF) << 8) + ((okuIlerle() & 0xFF) << 0));
    }

//    private byte readBuffer[] = new byte[8];

    public final long longOku() {
        return (((long)okuIlerle() << 56) +
                ((long)(okuIlerle() & 255) << 48) +
                ((long)(okuIlerle() & 255) << 40) +
                ((long)(okuIlerle() & 255) << 32) +
                ((long)(okuIlerle() & 255) << 24) +
                ((okuIlerle() & 255) << 16) +
                ((okuIlerle() & 255) <<  8) +
                ((okuIlerle() & 255) <<  0));
    }

    public final float floatOku() {
        return Float.intBitsToFloat(intOku());
    }

    public final double doubleOku() {
        return Double.longBitsToDouble(longOku());
    }

//    private char lineBuffer[];
//
//    @Deprecated
//    public final String readLine() {
//        char buf[] = lineBuffer;
//
//        if (buf == null) {
//            buf = lineBuffer = new char[128];
//        }
//
//        int room = buf.length;
//        int offset = 0;
//        int c;
//
//loop:   while (true) {
//            switch (c = in.read()) {
//              case -1:
//              case '\n':
//                break loop;
//
//              case '\r':
//                int c2 = in.read();
//                if ((c2 != '\n') && (c2 != -1)) {
//                    if (!(in instanceof PushbackInputStream)) {
//                        this.in = new PushbackInputStream(in);
//                    }
//                    ((PushbackInputStream)in).unread(c2);
//                }
//                break loop;
//
//              default:
//                if (--room < 0) {
//                    buf = new char[offset + 128];
//                    room = buf.length - offset - 1;
//                    System.arraycopy(lineBuffer, 0, buf, 0, offset);
//                    lineBuffer = buf;
//                }
//                buf[offset++] = (char) c;
//                break;
//            }
//        }
//        if ((c == -1) && (offset == 0)) {
//            return null;
//        }
//        return String.copyValueOf(buf, 0, offset);
//    }

    public final String utfOku() {
        return utfOku(this);
    }

    public static final String utfOku(ByteKumesi bk) {
        int utflen = bk.isaretsizShortOku();
        byte[] bytearr = null;
        char[] chararr = null;
//        if (in instanceof DataInputStream dis) {
//            if (dis.bytearr.length < utflen){
//                dis.bytearr = new byte[utflen*2];
//                dis.chararr = new char[utflen*2];
//            }
//            chararr = dis.chararr;
//            bytearr = dis.bytearr;
//        } else {
            bytearr = new byte[utflen];
            chararr = new char[utflen];
//        }

        int c, char2, char3;
        int count = 0;
        int chararr_count=0;

        bk.hepsiniOku(bytearr, 0, utflen);

        while (count < utflen) {
            c = (int) bytearr[count] & 0xff;
            if (c > 127) break;
            count++;
            chararr[chararr_count++]=(char)c;
        }

        while (count < utflen) {
            c = (int) bytearr[count] & 0xff;
            switch (c >> 4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                    /* 0xxxxxxx*/
                    count++;
                    chararr[chararr_count++]=(char)c;
                    break;
            case 12:
            case 13:
                /* 110x xxxx   10xx xxxx*/
                count += 2;
                // TODO
                if (count > utflen) break;
//                    throw new UTFDataFormatException(
//                        "malformed input: partial character at end");
                char2 = (int) bytearr[count-1];
                if ((char2 & 0xC0) != 0x80)  break;
//                    throw new UTFDataFormatException(
//                        "malformed input around byte " + count);
                chararr[chararr_count++]=(char)(((c & 0x1F) << 6) |
                                                (char2 & 0x3F));
            	break;
            case 14:
                    /* 1110 xxxx  10xx xxxx  10xx xxxx */
                    count += 3;
                    // TODO
                    if (count > utflen) break;
//                        throw new UTFDataFormatException(
//                            "malformed input: partial character at end");
                    char2 = (int) bytearr[count-2];
                    char3 = (int) bytearr[count-1];
                    if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))  break;
//                        throw new UTFDataFormatException(
//                            "malformed input around byte " + (count-1));
                    chararr[chararr_count++]=(char)(((c     & 0x0F) << 12) |
                                                    ((char2 & 0x3F) << 6)  |
                                                    ((char3 & 0x3F) << 0));
                    break;
                default:
                	break;
                    /* 10xx xxxx,  1111 xxxx */
//                    throw new UTFDataFormatException(
//                        "malformed input around byte " + count);
            }
        }
        // The number of chars produced may be less than utflen
        return new String(chararr, 0, chararr_count);
    }




	public int boyut() {
		return bytelar.size();
	}
	
	public byte[] kume() {
		return Bytes.toArray(bytelar);
	}

}
