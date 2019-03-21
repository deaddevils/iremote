package cn.com.isurpass.nbiot.action.aliiot;

import cn.com.isurpass.gateway.server.processor.gateway.GatewayReportProcessor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteOnlineEvent;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class AliIoTDeviceReportAction {
    private static Log log = LogFactory.getLog(AliIoTDeviceReportAction.class);

    private String appKey;
    private String sign;
    private String message;
    private String msgCode;

    public String execute() {
        if (StringUtils.isBlank(message)) {
            return Action.SUCCESS;
        }
        JSONObject json = JSONObject.parseObject(message);
        String eventCode = json.getString("eventCode");
        if (StringUtils.isBlank(eventCode) || !"Report".equals(eventCode)) {
            return Action.SUCCESS;
        }
        String value = json.getString("value");
        log.info(value);
        String data = JSON.parseObject(value).getString("RawData");
        if (StringUtils.isBlank(data)) {
            return Action.SUCCESS;
        }
        byte[] dataByte = JWStringUtils.hexStringtobyteArray(data);

        String deviceId = TlvWrap.readString(dataByte, TagDefine.TAG_GATEWAY_DEVICE_ID, TagDefine.TAG_HEAD_LENGTH);
        if (StringUtils.isBlank(deviceId)) {
            log.info("deviceId is blank.");
            return Action.SUCCESS;
        }
        IConnectionContext cc = ConnectionManager.getConnection(deviceId);

        AliIoTConnectionWrap nbc;
        if (cc != null && cc instanceof AliIoTConnectionWrap) {
            nbc = (AliIoTConnectionWrap) cc;
            nbc.refresh();
        } else {
            nbc = new AliIoTConnectionWrap(json.getString("iotId"));

            Remoter r = new Remoter();
            r.setHaslogin(true);  // udp device need not login
            r.setUuid(deviceId);

            nbc.setAttachment(r);

            ConnectionManager.addConnection(deviceId, nbc);
            JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE, new RemoteOnlineEvent(deviceId , new Date() ,false, 0));
            JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_UDP_GATEWAY_ONLINE, new RemoteOnlineEvent(deviceId, new Date(), true, false, dataByte, System.currentTimeMillis()));

            JMSUtil.commitmessage();
        }

        if (log.isInfoEnabled()) {
            Utils.print(String.format("Receive nbiot data from %s", deviceId), dataByte);
        }

        new GatewayReportProcessor().processRequest(dataByte, nbc);
        return Action.SUCCESS;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public int getCode() {
        return 200;
    }

    public String getMessage() {
        return "success";
    }

    public String getData() {
        return "OK";
    }
}
