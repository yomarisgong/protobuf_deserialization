package com.taskumbilical;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteBufferDataReader implements DataInput {
    private ByteBuffer byteBuffer;

    public ByteBufferDataReader(String hexString) {
        hexString = hexString.toLowerCase();
        byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        byteBuffer = ByteBuffer.wrap(byteArray);
    }

    @Override
    public void readFully(byte[] b) throws IOException {
        byteBuffer.get(b, 0, b.length);
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        byteBuffer.get(b, off, len);
    }

    @Override
    public int skipBytes(int n) throws IOException {
        final int remains = byteBuffer.remaining();
        final int skip = (remains < n) ? remains : n;
        final int current = byteBuffer.position();
        byteBuffer.position(current + skip);
        return skip;
    }

    @Override
    public boolean readBoolean() throws IOException {
        return (byteBuffer.get() == 1) ? true : false;
    }

    @Override
    public byte readByte() throws IOException {
        return byteBuffer.get();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        final int ch = byteBuffer.get();
        if (ch < 0) {
            throw new EOFException();
        }
        return ch;
    }

    @Override
    public short readShort() throws IOException {
        return byteBuffer.getShort();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return byteBuffer.getShort();
    }

    @Override
    public char readChar() throws IOException {
        return byteBuffer.getChar();
    }

    @Override
    public int readInt() throws IOException {
        return byteBuffer.getInt();
    }

    @Override
    public long readLong() throws IOException {
        return byteBuffer.getLong();
    }

    @Override
    public float readFloat() throws IOException {
        return byteBuffer.getFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return byteBuffer.getDouble();
    }

    @SuppressWarnings("deprecation")
    @Override
    public String readLine() throws IOException {
        return null;
    }

    @Override
    public final String readUTF() throws IOException {
        return null;
    }

}
