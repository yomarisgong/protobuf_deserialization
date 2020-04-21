package com.RpcRequestHeaderProto;

import com.amRegisterRequestRM.RegisterApplicationMasterRequestProtobuf;
import com.google.protobuf.InvalidProtocolBufferException;
import com.proto.Transfer;

public class Main {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        String requestHex = "08011000182822106b6c4489ba94444faf2a2a3639f1c88028003a2a0a286d725f617474656d70745f313538323137363836383034345f303031355f725f3030303030305f30";
        Transfer transfer = new Transfer();
        byte[] byteArray = transfer.HexToByte(requestHex);
        RpcRequestHeaderProtobuf.RpcRequestHeaderProto player = RpcRequestHeaderProtobuf.RpcRequestHeaderProto.parseFrom(byteArray);
        System.out.println(player.getRpcKind());//使用的Rpc类型
        System.out.println(player.getCallerContext());

    }
}
