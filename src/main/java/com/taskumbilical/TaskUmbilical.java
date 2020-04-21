package com.taskumbilical;

import com.proto.CatchRequest;
import com.proto.Transfer;
import design.RpcPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class TaskUmbilical {
    private String methodName;
    private String[] parameterClasses;
    private Object[] parameters;
    private String conf;
    private long clientVersion;
    private int clientMethodsHash;
    private String declaringClassProtocolName;
    private long rpcVersion;

    public  void readFields(DataInput in) throws IOException {
        rpcVersion = in.readLong();
        //System.out.println(rpcVersion);
        declaringClassProtocolName = UTF8.readString(in);
        methodName = UTF8.readString(in);
        if(declaringClassProtocolName.equals("org.apache.hadoop.mapred.TaskUmbilicalProtocol") && methodName.equals("done")){
            clientVersion = in.readLong();
            clientMethodsHash = in.readInt();
            parameters = new String[in.readInt()];
            //System.out.println(declaringClassProtocolName);
            //System.out.println(methodName);
            //System.out.println(clientVersion);
            //System.out.println(clientMethodsHash);
            //parameterClasses = new Class[parameters.length];
            ObjectWritable objectWritable = new ObjectWritable();
            for (int i = 0; i < parameters.length; i++) {
                parameters[i] =
                        ObjectWritable.readObject(in, objectWritable);
                //parameterClasses[i] = objectWritable.getDeclaredClass();
                System.out.println(parameters[i]);
            }
        }

    }

    public static void main(String[] args) throws IOException {
        String hex = "0000010346080110001810221080bb2861634c4a99afcb80221341679e28003a2a0a286d725f617474656d70745f313538323137363836383034345f303033305f6d5f3030303030305f300000000000000002002e6f72672e6170616368652e6861646f6f702e6d61707265642e5461736b556d62696c6963616c50726f746f636f6c0004646f6e650000000000000015e780bc030000000100266f72672e6170616368652e6861646f6f702e6d61707265642e5461736b417474656d7074494400266f72672e6170616368652e6861646f6f702e6d61707265642e5461736b417474656d7074494400000000000000030000001f0d31353832313736383638303434034d4150";
        CatchRequest cr = new CatchRequest();
        ArrayList<String> list = cr.partition(hex);
        for(int j = 0; j < list.size() ; j++){
            int i = 8;
            hex = list.get(j).toLowerCase();
            int totalLen = list.get(j).length();
            CatchRequest cr1 = new CatchRequest();
            RpcPackage rp = new RpcPackage();
            Map<String,Integer> result;
            if(i < totalLen){//header
                result = cr1.findLen(hex, i);
                int headLen = result.get("result");
                i = result.get("i");
                i += 2;
                String header = hex.substring(i, i + headLen * 2);
                rp.setHead(header);
                i += headLen * 2;
            }
            if(i < totalLen){
                String request = hex.substring(i,totalLen);
                rp.setRpcParameter(request);
                TaskUmbilical tu = new TaskUmbilical();
                DataInput in = new ByteBufferDataReader(request);
                tu.readFields(in);
                System.out.println();
            }
        }
        Transfer t = new Transfer();
        System.out.println(t.HexToAscii(hex));

    }
}
