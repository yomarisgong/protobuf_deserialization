package com.proto;

import com.google.protobuf.InvalidProtocolBufferException;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class AMTORMMain {
    public static void main(String[] args) throws InvalidProtocolBufferException, UnsupportedEncodingException {
        amTOrm.AllocateRequestProto.Builder builder = amTOrm.AllocateRequestProto.newBuilder();
        builder.setResponseId(99999);  ///我往这个对象里设置responseid为99999
        amTOrm.AllocateRequestProto player = builder.build();
        System.out.println(player);
        byte[] byteArray = player.toByteArray();
        System.out.println(Arrays.toString(byteArray));  //得到了序列化结果为[32, -97, -115, 6]
        byte[] byteArray2 = {0, 0, 0, -97, 79, 8, 2, 16, 0, 24, 34, 34, 16, 43, 0, 20, -87, -24, -38, 78, -17, -81, -52, -103, -63, 73, 67, -10, 23, 40, 0, 58, 51, 10, 49, 109, 114, 95, 97, 112, 112, 109, 97, 115, 116, 101, 114, 95, 97, 112, 112, 97, 116, 116, 101, 109, 112, 116, 95, 49, 53, 55, 54, 51, 48, 57, 53, 57, 51, 56, 50, 51, 95, 48, 48, 48, 49, 95, 48, 48, 48, 48, 48, 49, 68, 10, 8, 97, 108, 108, 111, 99, 97, 116, 101, 18, 54, 111, 114, 103, 46, 97, 112, 97, 99, 104, 101, 46, 104, 97, 100, 111, 111, 112, 46, 121, 97, 114, 110, 46, 97, 112, 105, 46, 65, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 77, 97, 115, 116, 101, 114, 80, 114, 111, 116, 111, 99, 111, 108, 80, 66, 24, 1, 9, 26, 0, 32, 0, 45, 0, 0, 0, 0};
        //byte[] byteArray2 = {10, 60, 10, 2, 8, 10, 18, 1, 42, 26, 43, 8, -128, 8, 16, 1, 26, 20, 10, 9, 109, 101, 109, 111, 114, 121, 45, 109, 98, 16, -128, 8, 26, 2, 77, 105, 32, 0, 26, 14, 10, 6, 118, 99, 111, 114, 101, 115, 16, 1, 26, 0, 32, 0, 32, 0, 58, 4, 8, 1, 16, 1, 26, 0, 32, 37, 45, 0, 0, 0, 63};
        //System.out.println("ss: " + player.toString());
        //player.toByteString();
        amTOrm.AllocateRequestProto newPlayer = amTOrm.AllocateRequestProto.parseFrom(byteArray2);
        System.out.println("response: " + newPlayer.getResponseId()); //得到了反序列化结果response: 99999
        System.out.println("progress: " + newPlayer.getProgress());
        System.out.println(newPlayer.getAskOrBuilder(0).getPriority());

        System.out.println("ask: " + newPlayer.getAsk(1));


        //字符串转化
        Transfer transfer = new Transfer();
        //16进制转byte
        String hexString = "000c29b30859000c290921d208004500013c04e340004006dc86c0a86b81c0a86b80c350ec3ce2797f7762a5715230ea3e7895c2a7bbeb2d8001008a01020800";
        System.out.println(Arrays.toString(transfer.HexToByte(hexString)));

        byte[] b = {0, 12, 41, -77, 8, 89, 0, 12, 41, 9, 33, -46, 8, 0, 69, 0, 1, 60, 4, -29, 64, 0, 64, 6, -36, -122, -64, -88, 107, -127, -64, -88, 107, -128, -61, 80, -20, 60, -30, 121, 127, 119, 98, -91, 113, 82, 48, -22, 62, 120, -107, -62, -89, -69, -21, 45, -128, 1, 0, -118, 1, 2, 8, 0};
        System.out.println(transfer.ByteArrayToAscii(b));
        //byte数组转十六进制
        System.out.println("函数：");
        System.out.println(transfer.ByteArrayToHex(b));
        //16进制转ascii
        String cc = "0802100018742210cdb39f66f0e7446ebf9a2192126a6b5028003a330a316d725f6170706d61737465725f617070617474656d70745f313537353436393032373435385f303030315f303030303031"; //1801d201
        System.out.println("函数：");
        System.out.println(transfer.HexToAscii(cc));

        //加：
        String tryy = "0a3c0a02081412012a1a2b08800810011a140a096d656d6f72792d6d621080081a024d6920001a0e0a0676636f72657310011a00200020013a04080110010a480a020814120d2f64656661756c742d7261636b1a2b08800810011a140a096d656d6f72792d6d621080081a024d6920001a0e0a0676636f72657310011a00200020013a04080110010a3f0a0208141204677979321a2b08800810011a140a096d656d6f72792d6d621080081a024d6920001a0e0a0676636f72657310011a00200020013a04080110011a0020002dcdcc4c3d";
        System.out.println(transfer.AddColon(tryy));

        //amTOrm.AllocateRequestProto newPlayer = amTOrm.AllocateRequestProto.parseFrom(byteArray);
        //System.out.println("response: " + newPlayer.getResponseId()); //得到了反序列化结果response: 99999


    }
}
