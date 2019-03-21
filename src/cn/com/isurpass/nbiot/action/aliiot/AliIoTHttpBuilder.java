package cn.com.isurpass.nbiot.action.aliiot;

import cn.com.isurpass.nbiot.action.aliiot.request.AsyncApiClient;
import cn.com.isurpass.nbiot.action.aliiot.request.IoTApiClientBuilderParams;
import cn.com.isurpass.nbiot.action.aliiot.request.IoTApiRequest;
import cn.com.isurpass.nbiot.action.aliiot.request.SyncApiClient;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.iremote.common.ServerRuntime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;

public class AliIoTHttpBuilder {
    private static Log log = LogFactory.getLog(AliIoTHttpBuilder.class);

    private static final String URL = "api.link.aliyun.com";
    private static final String GET_TOKEN = "/cloud/token";
    static final String THING_SERVICE_INVOKE = "/cloud/thing/service/invoke";
    static final String API_VER = "1.0.0";

    public static void asyncPost(String uri, IoTApiRequest request){
        AsyncApiClient syncClient = new AsyncApiClient(getIoTApiClientBuilderParams());

        try {
            syncClient.postBody(URL, uri, request, true);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static ApiResponse syncPost(String uri, IoTApiRequest request){
        SyncApiClient syncClient = new SyncApiClient(getIoTApiClientBuilderParams());

        try {
            return syncClient.postBody(URL, uri, request, true);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String getToken(){
        ApiResponse apiResponse = syncPost(GET_TOKEN, createGetTokenIoTApiRequest());
        if (apiResponse == null) {
            return null;
        }
        String body;
        try {
            body = new String(apiResponse.getBody(), "UTF-8");
            log.info(body);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        if (apiResponse.getCode() != 200) {
            return null;
        }
        return body;
    }

    private static IoTApiRequest createGetTokenIoTApiRequest() {
        IoTApiRequest request = new IoTApiRequest();
        request.setApiVer(API_VER);
        request.putParam("grantType", "project");
        request.putParam("res", ServerRuntime.getInstance().getAliiotprojectres());
        return request;
    }

    private static IoTApiClientBuilderParams getIoTApiClientBuilderParams() {
        IoTApiClientBuilderParams builderParams = new IoTApiClientBuilderParams();
        builderParams.setAppKey(ServerRuntime.getInstance().getAliiotappkey());
        builderParams.setAppSecret(ServerRuntime.getInstance().getAliiotappsecret());
        return builderParams;
    }
}
