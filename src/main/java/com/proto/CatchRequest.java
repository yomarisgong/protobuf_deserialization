package com.proto;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.RpcRequestHeaderProto.RpcRequestHeaderProtobuf;
import com.google.protobuf.InvalidProtocolBufferException;
import com.taskumbilical.ByteBufferDataReader;
import com.taskumbilical.TaskUmbilical;
import design.RpcPackage;


public class CatchRequest {
    public static int i = 8;

    public String addZero(int len){
        String temp = Integer.toBinaryString(len);
        while(temp.length()!=7){
            temp = "0" + temp;
        }
        return temp;
    }

    public Map<String,Integer> findLen(String target , int i){
        Map<String,Integer> finalResult = new HashMap<String, Integer>();
        String Length = target.substring(i,i+2);
        int Len = Integer.parseInt(Length,16);
        String[] buffer = new String[8];
        int position = 0;
        CatchRequest cr = new CatchRequest();
        while(true){
            if ((Len & ~0x7F) == 0) { //value<128，直接输出
                //System.out.println(Integer.toBinaryString(headLen));
                buffer[position++] = Integer.toBinaryString(Len);
                break;
            } else {
                buffer[position++] = cr.addZero(Len & 0x7F);
                i+=2;
                Length = target.substring(i,i+2);
                Len = Integer.parseInt(Length,16);
            }
        }
        String result = "";
        for(int j = position-1;j>=0;j--){
            result += buffer[j];
        }
       //System.out.println(result);
        result = Integer.valueOf(result,2).toString();//2进制转化为10进制
        finalResult.put("result",Integer.valueOf(result));
        finalResult.put("i",i);
        return finalResult;
    }

    public RpcPackage start(String dataHex) throws IOException {
        i = 8;
        dataHex = dataHex.toLowerCase();
        //String totalLength = dataHex.substring(0, 8);
        //int totalLen = (int)Long.parseLong(totalLength, 16) * 2 + 8;
        int totalLen = dataHex.length();
        CatchRequest cr1 = new CatchRequest();
        RpcPackage rp = new RpcPackage();
        Map<String,Integer> result;
        String rpcKind = "";
        if(i < totalLen){//header
            result = cr1.findLen(dataHex, i);
            int headLen = result.get("result");
            i = result.get("i");
            i += 2;
            String header = dataHex.substring(i, i + headLen * 2);
            rp.setHead(header);
            i += headLen * 2;
            Transfer transfer = new Transfer();
            byte[] byteArray = transfer.HexToByte(header);
            RpcRequestHeaderProtobuf.RpcRequestHeaderProto player = RpcRequestHeaderProtobuf.RpcRequestHeaderProto.parseFrom(byteArray);
            rpcKind = player.getRpcKind().toString();

        }
        //System.out.println("i"+i);
        if(rpcKind.equals("RPC_PROTOCOL_BUFFER")){
            if(i < totalLen){//rpc
                result = cr1.findLen(dataHex, i);
                int amLen = result.get("result");
                i = result.get("i");
                //System.out.println("amlen"+amLen);

                i += 2;
                System.out.println("ii"+i);
                String rpc = dataHex.substring(i, i + amLen * 2);
                rp.setRpcMethod(rpc);
                i += amLen * 2;
            }
            if(i < totalLen){
                result = cr1.findLen(dataHex, i);
                int requestLen = result.get("result");
                i = result.get("i");
                i += 2;
                String request = dataHex.substring(i,totalLen);
                rp.setRpcParameter(request);
            }
        }
        else if(rpcKind.equals("RPC_WRITABLE")){
            if(i < totalLen){
                String request = dataHex.substring(i,totalLen);
                rp.setRpcParameter(request);
                //TaskUmbilical tu = new TaskUmbilical();
                //DataInput in = new ByteBufferDataReader(request);
                //tu.readFields(in);
            }
        }
        return rp;
    }
    public ArrayList<String> partition(String dataHex){
        dataHex = dataHex.toLowerCase();
        int len = dataHex.length();
        ArrayList<String> list = new ArrayList<String>();
        int lower = 0;
        int upper = 0;
        while(len != upper){
            lower = upper;
            upper = lower + ((int) Long.parseLong(dataHex.substring(lower,lower+8), 16))* 2 + 8;
            //list.add(start(dataHex.substring(lower,upper)));
            list.add(dataHex.substring(lower,upper));
        }
        return list;
    }

   // public static void main(String[] args) {

        /*String dataHex = "0000071c1a08021000185022102faadcc8dc5e4577a38b90f8b1edb5ab28004d0a117375626d69744170706c69636174696f6e12366f72672e6170616368652e6861646f6f702e7961726e2e6170692e4170706c69636174696f6e436c69656e7450726f746f636f6c50421801b10d0aae0d0a09080110ea9c98e2f12d120a776f726420636f756e741a0764656661756c742ac10c0a93010a1e6a6f625375626d69744469722f6a6f622e73706c69746d657461696e666f12710a620a046864667312046779797918a84622512f746d702f6861646f6f702d7961726e2f73746167696e672f6861646f6f702f2e73746167696e672f6a6f625f313537363732373335303839305f303030312f6a6f622e73706c69746d657461696e666f101218c39da7e2f12d200228030a89010a076a6f622e6a6172127e0a580a046864667312046779797918a84622472f746d702f6861646f6f702d7961726e2f73746167696e672f6861646f6f702f2e73746167696e672f6a6f625f313537363732373335303839305f303030312f6a6f622e6a61721085a71318cb97a7e2f12d200328033213283f3a636c61737365732f7c6c69622f292e2a0a83010a166a6f625375626d69744469722f6a6f622e73706c697412690a5a0a046864667312046779797918a84622492f746d702f6861646f6f702d7961726e2f73746167696e672f6861646f6f702f2e73746167696e672f6a6f625f313537363732373335303839305f303030312f6a6f622e73706c6974106118fc9ca7e2f12d200228030a740a076a6f622e786d6c12690a580a046864667312046779797918a84622472f746d702f6861646f6f702d7961726e2f73746167696e672f6861646f6f702f2e73746167696e672f6a6f625f313537363732373335303839305f303030312f6a6f622e786d6c10ae9b0b18a5a2a7e2f12d20022803122648445453000001154d617052656475636553687566666c65546f6b656e08e5578dbcd8bb254d22120a055348454c4c12092f62696e2f6261736822390a0f4c445f4c4942524152595f504154481226245057443a7b7b4841444f4f505f434f4d4d4f4e5f484f4d457d7d2f6c69622f6e617469766522d3040a09434c4153535041544812c504245057443a2f7573722f6c6f63616c2f6861646f6f702f6574632f6861646f6f703a2f7573722f6c6f63616c2f6861646f6f702f73686172652f6861646f6f702f636f6d6d6f6e2f6c69622f2a3a2f7573722f6c6f63616c2f6861646f6f702f73686172652f6861646f6f702f636f6d6d6f6e2f2a3a2f7573722f6c6f63616c2f6861646f6f702f73686172652f6861646f6f702f686466733a2f7573722f6c6f63616c2f6861646f6f702f73686172652f6861646f6f702f686466732f6c69622f2a3a2f7573722f6c6f63616c2f6861646f6f702f73686172652f6861646f6f702f686466732f2a3a2f7573722f6c6f63616c2f6861646f6f702f73686172652f6861646f6f702f6d61707265647563652f6c69622f2a3a2f7573722f6c6f63616c2f6861646f6f702f73686172652f6861646f6f702f6d61707265647563652f2a3a2f7573722f6c6f63616c2f6861646f6f702f73686172652f6861646f6f702f7961726e3a2f7573722f6c6f63616c2f6861646f6f702f73686172652f6861646f6f702f7961726e2f6c69622f2a3a2f7573722f6c6f63616c2f6861646f6f702f73686172652f6861646f6f702f7961726e2f2a3a244841444f4f505f4d41505245445f484f4d452f73686172652f6861646f6f702f6d61707265647563652f2a3a244841444f4f505f4d41505245445f484f4d452f73686172652f6861646f6f702f6d61707265647563652f6c69622f2a3a6a6f622e6a61722f2a3a6a6f622e6a61722f636c61737365732f3a6a6f622e6a61722f6c69622f2a3a245057442f2a2ac502244a4156415f484f4d452f62696e2f6a617661202d446a6176612e696f2e746d706469723d245057442f746d70202d446c6f67346a2e636f6e66696775726174696f6e3d636f6e7461696e65722d6c6f67346a2e70726f70657274696573202d447961726e2e6170702e636f6e7461696e65722e6c6f672e6469723d3c4c4f475f4449523e202d447961726e2e6170702e636f6e7461696e65722e6c6f672e66696c6573697a653d30202d446861646f6f702e726f6f742e6c6f676765723d494e464f2c434c41202d446861646f6f702e726f6f742e6c6f6766696c653d7379736c6f6720202d586d78313032346d206f72672e6170616368652e6861646f6f702e6d61707265647563652e76322e6170702e4d524170704d617374657220313e3c4c4f475f4449523e2f7374646f757420323e3c4c4f475f4449523e2f7374646572722032050801120120320508021201203001400252094d41505245445543458a01380a02080012012a1a2b08800c10011a140a096d656d6f72792d6d6210800c1a024d6920001a0e0a0676636f72657310011a00200020012801";
        String totalLength = dataHex.substring(0,8);
        System.out.println(totalLength);
        long totalLen = Long.parseLong(totalLength, 16);
        System.out.println(totalLen);//代表的是后面还有361个字节，长度为361*2=722
        CatchRequest cr1 = new CatchRequest();

        Map<String,Integer> result = cr1.findLen(dataHex,i);
        int headLen = result.get("result");
        i = result.get("i");
        System.out.println(headLen);
        i+=2;
        String header = dataHex.substring(i,i+headLen*2);
        i+=headLen*2;

        result = cr1.findLen(dataHex,i);
        int amLen = result.get("result");
        i = result.get("i");
        System.out.println(amLen);
        i+=2;
        String amRequest = dataHex.substring(i,i+amLen*2);
        i+=amLen*2;

        result = cr1.findLen(dataHex,i);
        int requestLen = result.get("result");
        i = result.get("i");
        System.out.println(requestLen);
        i+=2;
        String request = dataHex.substring(i);

        System.out.println("header:" + header);//requestHex
        System.out.println("amRequest:" + amRequest);
        System.out.println("request:" + request);*/

  //  }
}
