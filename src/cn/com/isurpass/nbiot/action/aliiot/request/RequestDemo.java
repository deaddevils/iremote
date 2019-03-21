package cn.com.isurpass.nbiot.action.aliiot.request;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.com.isurpass.nbiot.action.aliiot.AliIoTAccessTokenManager;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.JWStringUtils;

public class RequestDemo {

    public static void main(String[] args) throws UnsupportedEncodingException {
        postRequestDemo();
//        getRequestDemo();

//        postRequestDemo2();
    }

    private static void postRequestDemo2() throws UnsupportedEncodingException {
        IoTApiClientBuilderParams builderParams = new IoTApiClientBuilderParams();
        builderParams.setAppKey("25628111");
        builderParams.setAppSecret("8c08196b63703f1cd34d9200a1dd8156");
        SyncApiClient syncClient = new SyncApiClient(builderParams);

        /*IoTApiRequest request = new IoTApiRequest();

        request.setApiVer("1.0.0");

        request.setCloudToken("dbe64e2161304838a04069f2d933d00f");

//        request.putParam("xxxx", "xxxxx");

        request.putParam("grantType", "project");
        request.putParam("res", "a124i3n4aC83piIK");
*/
        //path¡¢request
        String host = "api.link.aliyun.com";
        String path = "/cloud/thing/properties/set";
        ApiResponse response = syncClient.postBody(host, path,createIoTApiRequest(new byte[]{1,1,23,45}) , true);

        System.out.println(
                "response code = " + response.getCode() + " response content = " + new String(response.getBody(),
                        "utf-8"));
    }

    private static IoTApiRequest createIoTApiRequest(byte[] bs) {
        IoTApiRequest request = new IoTApiRequest();
        request.setApiVer("1.0.0");
        request.setCloudToken("9665c044955944c0935bf9ee28413a17");

        JSONObject json = new JSONObject();
        JSONObject json1 = new JSONObject();
        json1.put("value", JWStringUtils.toHexString(bs));
        json.put("data", json1.toJSONString());

//        request.putParam("iotId", "1180361");
        request.putParam("productKey", "a1ZZ8sJPDNx");
        request.putParam("deviceName", "Q0Aw02RU6DE9sJenWChX");
        request.putParam("items", json.toJSONString());
        return request;
    }

    public static void postRequestDemo() throws UnsupportedEncodingException {
        IoTApiClientBuilderParams builderParams = new IoTApiClientBuilderParams();
        builderParams.setAppKey("25628111");
        builderParams.setAppSecret("8c08196b63703f1cd34d9200a1dd8156");
        SyncApiClient syncClient = new SyncApiClient(builderParams);

        IoTApiRequest request = new IoTApiRequest();
        request.setApiVer("1.0.0");

//        request.setIotToken("xxxxxxxxxxxxxxx");

//        request.putParam("xxxx", "xxxxx");

        request.putParam("grantType", "project");
        request.putParam("res", "a124i3n4aC83piIK");

        String host = "api.link.aliyun.com";
        String path = "/cloud/token";
        ApiResponse response = syncClient.postBody(host, path, request, true);

        System.out.println(
            "response code = " + response.getCode() + " response content = " + new String(response.getBody(),
                "utf-8"));
    }

    public static void getRequestDemo() throws UnsupportedEncodingException {
        IoTApiClientBuilderParams builderParams = new IoTApiClientBuilderParams();
        builderParams.setAppKey("xxxx");
        builderParams.setAppSecret("xxxx");
        SyncApiGetClient syncClient = new SyncApiGetClient(builderParams);

        Map<String, String> headers = new HashMap<>(8);

        Map<String, String> querys = new HashMap<>(8);
        // *
        querys.put("apiVer", "1.0.0");
        querys.put("id", UUID.randomUUID().toString());
        // optional
        querys.put("iotToken", "XXXXXXXXXX");
        querys.put("xxxxx", "xxxxxx");

        //path¡¢request
        String host = "xxx.xxx.xxx:8080";
        String path = "/xxxx/xxxx/xxxx";
        ApiResponse response = syncClient.doGet(host, path, true, headers, querys);

        System.out.println(
            "response code = " + response.getCode() + " response content = " + new String(response.getBody(),
                "utf-8"));
    }
}
