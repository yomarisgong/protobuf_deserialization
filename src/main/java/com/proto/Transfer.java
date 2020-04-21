package com.proto;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Transfer {
    public byte[] HexToByte(String hexString){//十六进制转字节数组
        hexString = hexString.toLowerCase();
        byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public String ByteArrayToHex(byte[] byteArray){//字节数组转16进制
        String temp = "";
        for(int  i= 0;i<byteArray.length;i++){
            temp+=String.format("%02x",byteArray[i]);
        }
        return temp;
    }

    public String ByteArrayToAscii(byte[] byteArray) throws UnsupportedEncodingException {//字节数组转ascii
        String asciiString =new String(byteArray,"ascii");//第二个参数指定编码方式
        return asciiString;
    }

    public String HexToAscii(String hexString){//16进制转ascii
        StringBuilder sb = new StringBuilder();
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hexString.length()-1; i+=2 ){
            //grab the hex in pairs
            String output = hexString.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            if(decimal<128 && decimal>=32)
                sb.append((char)decimal);
            else//不在此范围内的都用.来表示
                sb.append('.');
        }
        return sb.toString();
    }

    public String AddColon(String HexString){//0a3c0a02->0a:3c:0a:02
        String temp = "";
        for(int i=0;i<HexString.length();i+=2){
            String output = HexString.substring(i, (i + 2));
            temp+=output;
            temp+=":";
        }
        return temp;
    }

    public static void main(String[] args) {
        String target = "000001034608011000180022106b6c4489ba94444faf2a2a3639f1c88028003a2a0a286d725f617474656d70745f313538323137363836383034345f303031355f725f3030303030305f30" +
                "0000000000000002002e6f72672e6170616368652e6861646f6f702e6d61707265642e5461736b556d62696c6963616c50726f746f636f6c00076765745461736b0000000000000015e780bc030000000100236f72672e6170616368652e6861646f6f702e6d61707265642e4a766d436f6e7465787400236f72672e6170616368652e6861646f6f702e6d61707265642e4a766d436f6e7465787400000000000000030000000f0d3135383231373638363830343400052d31303030";
        //total package'data
        String target2 = "0000000000000002" +
                "002e" +
                "6f72672e6170616368652e6861646f6f702e6d61707265642e5461736b556d62696c6963616c50726f746f636f6c" +
                "0007" +
                "6765745461736b" +
                "0000000000000015" +
                "e780bc03" +
                "00000001" +
                "0023" +
                "6f72672e6170616368652e6861646f6f702e6d61707265642e4a766d436f6e74657874" +
                "0023" +
                "6f72672e6170616368652e6861646f6f702e6d61707265642e4a766d436f6e74657874" +
                "0000000000000003" +
                "0000000f" +
                "0d" +
                "31353832313736383638303434" +
                "00" +
                "05" +
                "2d31303030";
        System.out.println(target.length());
        String protocolname = "";
        String targett = "6f72672e6170616368652e6861646f6f702e6d61707265642e5461736b556d62696c6963616c50726f746f636f6c00076765745461736b0000000000000015e780bc030000000100236f72672e6170616368652e6861646f6f702e6d61707265642e4a766d436f6e7465787400236f72672e6170616368652e6861646f6f702e6d61707265642e4a766d436f6e7465787400000000000000030000000f0d3135383231373638363830343400052d31303030";
        for(int i = 0; i < 92; i ++){
            protocolname += targett.charAt(i);
        }
        System.out.println(protocolname);
        System.out.println(protocolname.length());
        Transfer t = new Transfer();
        System.out.println(Arrays.toString(t.HexToByte(target2)));
        System.out.println(t.HexToAscii(target2));
        System.out.println(t.HexToAscii(protocolname));
        System.out.println("6f72672e6170616368652e6861646f6f702e6d61707265642e4a766d436f6e7465787400236f72672e6170616368652e6861646f6f702e6d61707265642e4a766d436f6e74657874".length());
        //4字节总长|header长度|header内容|
        String getclass = "";
        targett = "6f72672e6170616368652e6861646f6f702e6d61707265642e4a766d436f6e7465787400236f72672e6170616368652e6861646f6f702e6d61707265642e4a766d436f6e74657874";
        for(int i = 0; i < 70; i ++){
            getclass += targett.charAt(i);
        }
        System.out.println(getclass);
        System.out.println(getclass.length());
        System.out.println(t.HexToAscii("000001064608011000182822106b6c4489ba94444faf2a2a3639f1c88028003a2a0a286d725f617474656d70745f313538323137363836383034345f303031355f725f3030303030305f300000000000000002002e6f72672e6170616368652e6861646f6f702e6d61707265642e5461736b556d62696c6963616c50726f746f636f6c0004646f6e650000000000000015e780bc030000000100266f72672e6170616368652e6861646f6f702e6d61707265642e5461736b417474656d7074494400266f72672e6170616368652e6861646f6f702e6d61707265642e5461736b417474656d7074494400000000000000000000000f0d3135383231373638363830343406524544554345"));
        System.out.println("4d4150".length());
    }
}
