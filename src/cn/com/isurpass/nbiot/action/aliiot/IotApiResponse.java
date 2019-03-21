package cn.com.isurpass.nbiot.action.aliiot;

import com.alibaba.cloudapi.sdk.model.ApiCallback;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;

public class IotApiResponse implements ApiCallback {
    private static Log log = LogFactory.getLog(IotApiResponse.class);
    private String id;

    public IotApiResponse(String id) {
        this.id = id;
    }

    @Override
    public void onFailure(ApiRequest apiRequest, Exception e) {
        log.info(id);
        log.info(e.getMessage(), e);
    }

    @Override
    public void onResponse(ApiRequest apiRequest, ApiResponse apiResponse) {
        log.info(id);
        try {
            log.info(new String(apiResponse.getBody(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
