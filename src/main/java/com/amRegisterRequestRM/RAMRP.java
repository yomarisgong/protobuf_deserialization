package com.amRegisterRequestRM;

import com.google.protobuf.InvalidProtocolBufferException;
import com.proto.Transfer;

public class RAMRP {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        String requestHex = "0a067469616e3130108caf021a13687474703a2f2f7469616e31303a3435353531";
        Transfer transfer = new Transfer();
        byte[] byteArray = transfer.HexToByte(requestHex);
        RegisterApplicationMasterRequestProtobuf.RegisterApplicationMasterRequestProto player = RegisterApplicationMasterRequestProtobuf.RegisterApplicationMasterRequestProto.parseFrom(byteArray);
        System.out.println(player.getHost());//主机名
        System.out.println(player.getRpcPort());//rpc端口
        System.out.println(player.getTrackingUrl());//url
        System.out.println(player.getPlacementConstraintsList());

        System.out.println(transfer.HexToAscii("0802100018142210c898014f84fe480586462367efc1d2da28003a330a316d725f6170706d61737465725f617070617474656d70745f313538323137363836383034345f303031355f303030303031"));
    }
}
