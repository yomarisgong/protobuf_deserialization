package com.proto;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.Arrays;

public class Deserlization {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        String dataHex = "0a3c0a02081412012a1a2b08800810011a140a096d656d6f72792d6d621080081a024d6920001a0e0a0676636f72657310011a00200020013a04080110010a480a020814120d2f64656661756c742d7261636b1a2b08800810011a140a096d656d6f72792d6d621080081a024d6920001a0e0a0676636f72657310011a00200020013a04080110010a3f0a0208141204677979321a2b08800810011a140a096d656d6f72792d6d621080081a024d6920001a0e0a0676636f72657310011a00200020013a04080110011a0020012dcdcc4c3d";
        Transfer transfer = new Transfer();
        byte[] byteArray = transfer.HexToByte(dataHex);
        System.out.println(Arrays.toString(byteArray));
        amTOrm.AllocateRequestProto newPlayerr = amTOrm.AllocateRequestProto.parseFrom(byteArray);
        System.out.println("response: " + newPlayerr.getResponseId()); //得到了反序列化结果response: 99999
        System.out.println("progress: " + newPlayerr.getProgress());
        System.out.println(newPlayerr.getAskOrBuilder(0).getPriority());
        System.out.println("ask: " + newPlayerr.getAsk(0));
        System.out.println("ask: " + newPlayerr.getAsk(1));
        System.out.println("ask: " + newPlayerr.getAsk(2));
    }
}
