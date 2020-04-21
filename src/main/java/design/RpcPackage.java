package design;

public class RpcPackage {
    private String head; //RpcRequestHeader
    private String rpcMethod; //RequestHeader
    private String rpcParameter; //Request

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getRpcMethod() {
        return rpcMethod;
    }

    public void setRpcMethod(String rpcMethod) {
        this.rpcMethod = rpcMethod;
    }

    public String getRpcParameter() {
        return rpcParameter;
    }

    public void setRpcParameter(String rpcParameter) {
        this.rpcParameter = rpcParameter;
    }

    @Override
    public String toString() {
        return "RpcPackage{" +
                "head='" + head + '\'' +
                ", rpcMethod='" + rpcMethod + '\'' +
                ", rpcParameter='" + rpcParameter + '\'' +
                '}';
    }

}
