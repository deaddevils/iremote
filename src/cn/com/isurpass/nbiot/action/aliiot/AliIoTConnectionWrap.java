package cn.com.isurpass.nbiot.action.aliiot;

import cn.com.isurpass.nbiot.action.NoiotConnectionWrap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.JWStringUtils;
import com.iremote.common.ServerRuntime;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.CommandTlv;
import cn.com.isurpass.nbiot.action.aliiot.request.IoTApiRequest;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AliIoTConnectionWrap extends NoiotConnectionWrap {
    private static Log log = LogFactory.getLog(AliIoTConnectionWrap.class);

    public AliIoTConnectionWrap(String nbiotdeviceid) {
        super(nbiotdeviceid);
    }

    @Override
    public void sendData(CommandTlv ct) {
        if (!checkParameters()){
            return;
        }

        byte[] b = ct.getByte();
        if (log.isInfoEnabled()) {
            Utils.print(String.format("Send data to %s ", this.getDeviceid()), b);
        }

        int c = 0 ;
        for ( int i = 0 ; i < b.length - 1 ; i ++ )
            c =  ( c ^ b[i]);
        b[b.length -1] = (byte)c;

        IoTApiRequest request = createIoTApiRequest(b);
        log.info(JSON.toJSONString(request));

        AliIoTHttpBuilder.asyncPost(AliIoTHttpBuilder.THING_SERVICE_INVOKE, request);
    }

    protected boolean checkParameters() {
        if (!open || StringUtils.isBlank(nbiotdeviceid)) {
            return false;
        }
        if (StringUtils.isBlank(ServerRuntime.getInstance().getAliiotappkey())
                || StringUtils.isBlank(ServerRuntime.getInstance().getAliiotappsecret())
                || StringUtils.isBlank(ServerRuntime.getInstance().getAliiotprojectres())) {
            log.info("ali IoT key isn't configure in database");
            return false;
        }
        return true;
    }

    private IoTApiRequest createIoTApiRequest(byte[] bs) {
        IoTApiRequest request = new IoTApiRequest();
        request.setApiVer(AliIoTHttpBuilder.API_VER);
        request.setCloudToken(AliIoTAccessTokenManager.getInstance().getAuthorization());

        JSONObject json = new JSONObject();
        json.put("RawData", JWStringUtils.toHexString(bs));

        request.putParam("iotId", nbiotdeviceid);
        request.putParam("identifier", "Control");
        request.putParam("args", json);
        return request;
    }
}
