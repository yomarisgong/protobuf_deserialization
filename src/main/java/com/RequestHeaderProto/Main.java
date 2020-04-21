package com.RequestHeaderProto;

import com.RpcRequestHeaderProto.RpcRequestHeaderProtobuf;
import com.google.protobuf.InvalidProtocolBufferException;
import com.proto.Transfer;

public class Main {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        String requestHex = "0a0f7374617274436f6e7461696e65727312386f72672e6170616368652e6861646f6f702e7961726e2e6170692e436f6e7461696e65724d616e6167656d656e7450726f746f636f6c50421801";
        Transfer transfer = new Transfer();
        byte[] byteArray = transfer.HexToByte(requestHex);
        RequestHeaderProtobuf.RequestHeaderProto player = RequestHeaderProtobuf.RequestHeaderProto.parseFrom(byteArray);
        System.out.println(player.getDeclaringClassProtocolName());//使用的protocol name
        System.out.println(player.getMethodName());
        System.out.println(transfer.HexToAscii(requestHex));

    }
}
