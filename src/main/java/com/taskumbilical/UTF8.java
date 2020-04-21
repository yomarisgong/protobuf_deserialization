package com.taskumbilical;

import java.io.DataInput;
import java.io.IOException;
import java.io.UTFDataFormatException;

public class UTF8 {
    private static final ThreadLocal<DataOutputBuffer> OBUF_FACTORY =
            new ThreadLocal<DataOutputBuffer>(){
                @Override
                protected DataOutputBuffer initialValue() {
                    return new DataOutputBuffer();
                }
            };
    public static String readString(DataInput in) throws IOException {
        int bytes = in.readUnsignedShort();
        StringBuilder buffer = new StringBuilder(bytes);
        readChars(in, buffer, bytes);
        return buffer.toString();
    }
    private static char highSurrogate(int codePoint) {
        return (char) ((codePoint >>> 10)
                + (Character.MIN_HIGH_SURROGATE - (Character.MIN_SUPPLEMENTARY_CODE_POINT >>> 10)));
    }

    private static char lowSurrogate(int codePoint) {
        return (char) ((codePoint & 0x3ff) + Character.MIN_LOW_SURROGATE);
    }

    private static void readChars(DataInput in, StringBuilder buffer, int nBytes)
            throws UTFDataFormatException, IOException {
        DataOutputBuffer obuf = OBUF_FACTORY.get();
        obuf.reset();
        obuf.write(in, nBytes);
        byte[] bytes = obuf.getData();
        int i = 0;
        while (i < nBytes) {
            byte b = bytes[i++];
            if ((b & 0x80) == 0) {
                // 0b0xxxxxxx: 1-byte sequence
                buffer.append((char)(b & 0x7F));
            } else if ((b & 0xE0) == 0xC0) {
                if (i >= nBytes) {
                    throw new UTFDataFormatException("Truncated UTF8 at " +
                            StringUtils.byteToHexString(bytes, i - 1, 1));
                }
                // 0b110xxxxx: 2-byte sequence
                buffer.append((char)(((b & 0x1F) << 6)
                        | (bytes[i++] & 0x3F)));
            } else if ((b & 0xF0) == 0xE0) {
                // 0b1110xxxx: 3-byte sequence
                if (i + 1 >= nBytes) {
                    throw new UTFDataFormatException("Truncated UTF8 at " +
                            StringUtils.byteToHexString(bytes, i - 1, 2));
                }
                buffer.append((char)(((b & 0x0F) << 12)
                        | ((bytes[i++] & 0x3F) << 6)
                        |  (bytes[i++] & 0x3F)));
            } else if ((b & 0xF8) == 0xF0) {
                if (i + 2 >= nBytes) {
                    throw new UTFDataFormatException("Truncated UTF8 at " +
                            StringUtils.byteToHexString(bytes, i - 1, 3));
                }
                // 0b11110xxx: 4-byte sequence
                int codepoint =
                        ((b & 0x07) << 18)
                                | ((bytes[i++] & 0x3F) <<  12)
                                | ((bytes[i++] & 0x3F) <<  6)
                                | ((bytes[i++] & 0x3F));
                buffer.append(highSurrogate(codepoint))
                        .append(lowSurrogate(codepoint));
            } else {
                // The UTF8 standard describes 5-byte and 6-byte sequences, but
                // these are no longer allowed as of 2003 (see RFC 3629)

                // Only show the next 6 bytes max in the error code - in case the
                // buffer is large, this will prevent an exceedingly large message.
                int endForError = Math.min(i + 5, nBytes);
                throw new UTFDataFormatException("Invalid UTF8 at " +
                        StringUtils.byteToHexString(bytes, i - 1, endForError));
            }
        }
    }
}
