package com.taskumbilical;

import java.io.DataInput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.Arrays;

public class ObjectWritable {
    private byte[] bytes;
    private int length;

    public static int readVIntInRange(DataInput stream, int lower, int upper)
            throws IOException {
        long n = readVLong(stream);
        if (n < lower) {
            if (lower == 0) {
                throw new IOException("expected non-negative integer, got " + n);
            } else {
                throw new IOException("expected integer greater than or equal to " +
                        lower + ", got " + n);
            }
        }
        if (n > upper) {
            throw new IOException("expected integer less or equal to " + upper +
                    ", got " + n);
        }
        return (int)n;
    }

    public static String readString(DataInput in) throws IOException {
        return readString(in, Integer.MAX_VALUE);
    }

    /** Read a UTF8 encoded string with a maximum size
     */
    public static String readString(DataInput in, int maxLength)
            throws IOException {
        int length = readVIntInRange(in, 0, maxLength);
        byte [] bytes = new byte[length];
        in.readFully(bytes, 0, length);
        String s = new String(bytes,"ascii");
        return s;
    }

    public static boolean isNegativeVInt(byte value) {
        return value < -120 || (value >= -112 && value < 0);
    }

    public static int decodeVIntSize(byte value) {
        if (value >= -112) {
            return 1;
        } else if (value < -120) {
            return -119 - value;
        }
        return -111 - value;
    }

    public static long readVLong(DataInput stream) throws IOException {
        byte firstByte = stream.readByte();
        int len = decodeVIntSize(firstByte);
        if (len == 1) {
            return firstByte;
        }
        long i = 0;
        for (int idx = 0; idx < len-1; idx++) {
            byte b = stream.readByte();
            i = i << 8;
            i = i | (b & 0xFF);
        }
        return (isNegativeVInt(firstByte) ? (i ^ -1L) : i);
    }

    public static int readVInt(DataInput stream) throws IOException {
        long n = readVLong(stream);
        if ((n > Integer.MAX_VALUE) || (n < Integer.MIN_VALUE)) {
            throw new IOException("value too long to fit in integer");
        }
        return (int)n;
    }
    public void readWithKnownLength(DataInput in, int len) throws IOException {
        setCapacity(len, false);
        in.readFully(bytes, 0, len);
        length = len;
    }
    private void setCapacity(int len, boolean keepData) {
        if (bytes == null || bytes.length < len) {
            if (bytes != null && keepData) {
                bytes = Arrays.copyOf(bytes, Math.max(len,length << 1));
            } else {
                bytes = new byte[len];
            }
        }
    }
    public String readFields(DataInput in) throws IOException {
        int attempt_id = in.readInt();
        String attempt_ids = String.format("%01d", attempt_id);
        int task_id = in.readInt();
        String task_ids = String.format("%06d", task_id);
        int job_id = in.readInt();
        String job_ids = String.format("%04d", job_id);
        int newLength = ObjectWritable.readVInt(in);
        readWithKnownLength(in, newLength);
        String jobtracker =new String(bytes,"ascii");
        String type = readString(in, Integer.MAX_VALUE);
        String result = "";
        if(type.equals("REDUCE"))
            result = "mr_attempt_"+jobtracker+"_"+job_ids+"_r"+"_"+task_ids+"_"+attempt_ids;
        else if(type.equals("MAP"))
            result = "mr_attempt_"+jobtracker+"_"+job_ids+"_m"+"_"+task_ids+"_"+attempt_ids;
        else
            result = "";
        return result;
    }
    public static String readObject(DataInput in, ObjectWritable objectWritable)
            throws IOException {
        String className = UTF8.readString(in);
        String str = UTF8.readString(in);
        return objectWritable.readFields(in);
    }
}
