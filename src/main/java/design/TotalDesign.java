package design;
import com.RequestHeaderProto.RequestHeaderProtobuf;
import com.RpcRequestHeaderProto.RpcRequestHeaderProtobuf;
import com.amRequestNMContainer.StartContainersRequestProtobuf;
import com.google.protobuf.InvalidProtocolBufferException;
import com.proto.*;
import com.submitApplication.*;
import com.cat.*;
import com.sun.xml.internal.ws.util.xml.CDATA;

import java.io.*;
import java.util.ArrayList;

import com.taskumbilical.ByteBufferDataReader;
import com.taskumbilical.TaskUmbilical;
import design.RpcPackage;
public class TotalDesign {

    public Transfer transfer = new Transfer();

    public void start(String dataHex) throws InvalidProtocolBufferException{
        CatchRequest cr = new CatchRequest();

        /*if(cr.start(dataHex).get("am").equals("true")){
            String request = cr.start(dataHex).get("rm");//最后需要的payload
            //进行反序列化
            Transfer transfer = new Transfer();
            byte[] byteArray = transfer.HexToByte(request);
            submitApplicationSubmission.SubmitApplicationRequestProto player = submitApplicationSubmission.SubmitApplicationRequestProto.parseFrom(byteArray);
            //System.out.println(player.getApplicationSubmissionContext());

            String jobXml = "";
            int requestLen = player.getApplicationSubmissionContextOrBuilder().getAmContainerSpecOrBuilder().getLocalResourcesOrBuilderList().size();
            for(int  i = 0;i < requestLen;i++){
                if(player.getApplicationSubmissionContextOrBuilder().getAmContainerSpecOrBuilder().getLocalResourcesOrBuilder(i).getKey().equals("job.xml")){
                    jobXml = player.getApplicationSubmissionContextOrBuilder().getAmContainerSpecOrBuilder().getLocalResourcesOrBuilder(i).getValue().getResource().getFile();
                }
            }
            if(jobXml.length()>0){
                jobXml = "hdfs dfs -cat " + jobXml;  // dfs://gyyy:9000/tmp/hadoop-yarn/staging/hadoop/.staging/job_1576727350890_0001/job.xml
                System.out.println("jobXML: " + jobXml);
                proBuild pb = new proBuild();
                pb.testProcessBuilder(jobXml);
            }
        }*/

    }

    public String protoKind(String requestHex) throws InvalidProtocolBufferException {
        //Transfer transfer = new Transfer();
        byte[] byteArray = transfer.HexToByte(requestHex);
        RpcRequestHeaderProtobuf.RpcRequestHeaderProto player = RpcRequestHeaderProtobuf.RpcRequestHeaderProto.parseFrom(byteArray);
        String rpcKind = player.getRpcKind().toString();
        return rpcKind;
    }

    public String requestHeader(String requestHex) throws InvalidProtocolBufferException {
        //Transfer transfer = new Transfer();
        byte[] byteArray = transfer.HexToByte(requestHex);
        RequestHeaderProtobuf.RequestHeaderProto rhp = RequestHeaderProtobuf.RequestHeaderProto.parseFrom(byteArray);
        String methodName = rhp.getMethodName();
        String protocolName = rhp.getDeclaringClassProtocolName();
        return protocolName+" "+methodName;
    }
    public void submitApplication(String requestHex) throws InvalidProtocolBufferException {
        //Transfer transfer = new Transfer();
        byte[] byteArray = transfer.HexToByte(requestHex);
        submitApplicationSubmission.SubmitApplicationRequestProto player = submitApplicationSubmission.SubmitApplicationRequestProto.parseFrom(byteArray);
        System.out.println(player.getApplicationSubmissionContext());
    }

    public void startContainers(String requestHex) throws InvalidProtocolBufferException {
        //Transfer transfer = new Transfer();
        byte[] byteArray = transfer.HexToByte(requestHex);
        StartContainersRequestProtobuf.StartContainersRequestProto player = StartContainersRequestProtobuf.StartContainersRequestProto.parseFrom(byteArray);
        System.out.println(player.getStartContainerRequestList());
    }

    public static void main(String[] args) throws IOException {
        TotalDesign td = new TotalDesign();
        File file = new File("/Users/gongyuanyuan/Documents/学习/Interllij/InterllijProject/protocolbuffer/src/main/java/design/data.txt");
        InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader bf = new BufferedReader(inputReader);
        // 按行读取字符串
        String str;
        while ((str = bf.readLine()) != null) {
            //String dataHex = "0000010346080110001810221080bb2861634c4a99afcb80221341679e28003a2a0a286d725f617474656d70745f313538323137363836383034345f303033305f6d5f3030303030305f300000000000000002002e6f72672e6170616368652e6861646f6f702e6d61707265642e5461736b556d62696c6963616c50726f746f636f6c0004646f6e650000000000000015e780bc030000000100266f72672e6170616368652e6861646f6f702e6d61707265642e5461736b417474656d7074494400266f72672e6170616368652e6861646f6f702e6d61707265642e5461736b417474656d7074494400000000000000030000001f0d31353832313736383638303434034d4150";
            //newd.start("0000008d4f0802100018052210cff66c3a14e1479d907d3f5c1942e2ec28013a330a316d725f6170706d61737465725f617070617474656d70745f313538323137363836383034345f303031315f3030303030313c12001a386f72672e6170616368652e6861646f6f702e7961726e2e6170692e436f6e7461696e65724d616e6167656d656e7450726f746f636f6c5042000007f84f08021000182c2210cff66c3a14e1479d907d3f5c1942e2ec28003a330a316d725f6170706d61737465725f617070617474656d70745f313538323137363836383034345f303031315f3030303030314d0a0f7374617274436f6e7461696e65727312386f72672e6170616368652e6861646f6f702e7961726e2e6170692e436f6e7461696e65724d616e6167656d656e7450726f746f636f6c50421801d80e0ad50e0a860d0a740a076a6f622e786d6c12690a580a046864667312066d617374657218a84622452f746d702f6861646f6f702d7961726e2f73746167696e672f7469616e2f2e73746167696e672f6a6f625f313538323137363836383034345f303031312f6a6f622e786d6c10a2a30b18eece99aa872e200228030a89010a076a6f622e6a6172127e0a580a046864667312066d617374657218a84622452f746d702f6861646f6f702d7961726e2f73746167696e672f7469616e2f2e73746167696e672f6a6f625f313538323137363836383034345f303031312f6a6f622e6a617210fca813189ccc99aa872e200328033213283f3a636c61737365732f7c6c69622f292e2a128101484454530001084a6f62546f6b656e17166a6f625f313538323137363836383034345f3030313114cc321520b7f870dd9a097e47dc23685ce18ff9850d6d61707265647563652e6a6f62166a6f625f313538323137363836383034345f3030313101154d617052656475636553687566666c65546f6b656e08c5fbdae42c2dda8a1a5b0a116d61707265647563655f73687566666c65124617166a6f625f313538323137363836383034345f3030313108c5fbdae42c2dda8a0d6d61707265647563652e6a6f62166a6f625f313538323137363836383034345f3030313122260a125354444f55545f4c4f4746494c455f454e5612103c4c4f475f4449523e2f7374646f757422120a055348454c4c12092f62696e2f6261736822390a0f4c445f4c4942524152595f504154481226245057443a7b7b4841444f4f505f434f4d4d4f4e5f484f4d457d7d2f6c69622f6e617469766522220a124841444f4f505f524f4f545f4c4f47474552120c494e464f2c636f6e736f6c6522260a125354444552525f4c4f4746494c455f454e5612103c4c4f475f4449523e2f73746465727222540a124841444f4f505f4d41505245445f484f4d45123e2f686f6d652f7469616e2f486f2f6861646f6f702d332e312e312d7372632f6861646f6f702d646973742f7461726765742f6861646f6f702d332e312e3122a9030a09434c41535350415448129b03245057443a244841444f4f505f434f4e465f4449523a244841444f4f505f434f4d4d4f4e5f484f4d452f73686172652f6861646f6f702f636f6d6d6f6e2f2a3a244841444f4f505f434f4d4d4f4e5f484f4d452f73686172652f6861646f6f702f636f6d6d6f6e2f6c69622f2a3a244841444f4f505f484446535f484f4d452f73686172652f6861646f6f702f686466732f2a3a244841444f4f505f484446535f484f4d452f73686172652f6861646f6f702f686466732f6c69622f2a3a244841444f4f505f5941524e5f484f4d452f73686172652f6861646f6f702f7961726e2f2a3a244841444f4f505f5941524e5f484f4d452f73686172652f6861646f6f702f7961726e2f6c69622f2a3a244841444f4f505f4d41505245445f484f4d452f73686172652f6861646f6f702f6d61707265647563652f2a3a244841444f4f505f4d41505245445f484f4d452f73686172652f6861646f6f702f6d61707265647563652f6c69622f2a3a6a6f622e6a61722f2a3a6a6f622e6a61722f636c61737365732f3a6a6f622e6a61722f6c69622f2a3a245057442f2a22160a124841444f4f505f434c49454e545f4f50545312002ab503244a4156415f484f4d452f62696e2f6a617661202d446a6176612e6e65742e70726566657249507634537461636b3d74727565202d446861646f6f702e6d6574726963732e6c6f672e6c6576656c3d5741524e2020202d586d783832306d202d446a6176612e696f2e746d706469723d245057442f746d70202d446c6f67346a2e636f6e66696775726174696f6e3d636f6e7461696e65722d6c6f67346a2e70726f70657274696573202d447961726e2e6170702e636f6e7461696e65722e6c6f672e6469723d3c4c4f475f4449523e202d447961726e2e6170702e636f6e7461696e65722e6c6f672e66696c6573697a653d30202d446861646f6f702e726f6f742e6c6f676765723d494e464f2c434c41202d446861646f6f702e726f6f742e6c6f6766696c653d7379736c6f67206f72672e6170616368652e6861646f6f702e6d61707265642e5961726e4368696c64203137322e31362e3130302e313020333637353620617474656d70745f313538323137363836383034345f303031315f6d5f3030303030305f30203220313e3c4c4f475f4449523e2f7374646f757420323e3c4c4f475f4449523e2f73746465727220320508011201203205080212012012c9010a8b010a11120d0a09080b10ccaddc88862e10011802120c7469616e31303a33343232321a047469616e222b08804010011a140a096d656d6f72792d6d621080401a024d6920001a0e0a0676636f72657310011a00200028f3d0beaa872e3087a59c8bfeffffffff0138ccaddc88862e4202081448b7f999aa872e5a0060026801700078ffffffffffffffffff011214061b024d05b0db3cea38fd56e14e2227cab2d0b51a0e436f6e7461696e6572546f6b656e22133137322e31362e3130302e31303a3334323232");
            System.out.println(str.length());
            CatchRequest cr = new CatchRequest();
            ArrayList<String> list = cr.partition(str);
            ArrayList<RpcPackage> rplist = new ArrayList<RpcPackage>();
            for(int i = 0 ; i < list.size() ; i++){
                rplist.add(cr.start(list.get(i)));
                System.out.println(rplist.get(i).toString());
                String rpcKind = td.protoKind(rplist.get(i).getHead());
                if(rpcKind.equals("RPC_PROTOCOL_BUFFER")){ //判断proto类型
                    if(rplist.get(i).getRpcParameter()!=null){//不为空的时候才有意义
                        String proto_method_name = td.requestHeader(rplist.get(i).getRpcMethod());
                        String rpcParameter = rplist.get(i).getRpcParameter();
                        if(proto_method_name.equals("org.apache.hadoop.yarn.api.ApplicationClientProtocolPB submitApplication")){
                            td.submitApplication(rpcParameter);
                        }else if(proto_method_name.equals("org.apache.hadoop.yarn.api.ContainerManagementProtocolPB startContainers")){
                            td.startContainers(rpcParameter);
                        }else{
                            System.out.println("it's not my target");
                        }
                    }
                }else if(rpcKind.equals("RPC_WRITABLE")){
                    TaskUmbilical tu = new TaskUmbilical();
                    DataInput in = new ByteBufferDataReader(rplist.get(i).getRpcParameter());
                    tu.readFields(in);
                }

            }
        }

      }


}

