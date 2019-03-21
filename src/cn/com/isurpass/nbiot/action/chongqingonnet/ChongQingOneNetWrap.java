package cn.com.isurpass.nbiot.action.chongqingonnet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.JWStringUtils;
import com.iremote.common.Utils;
import com.iremote.common.http.AsyncHttpResponse;
import com.iremote.infraredtrans.tlv.CommandTlv;

import cn.com.isurpass.nbiot.action.NoiotConnectionWrap;

public class ChongQingOneNetWrap extends NoiotConnectionWrap
{
	private static Log log = LogFactory.getLog(ChongQingOneNetWrap.class);
	
	public ChongQingOneNetWrap(String nbiotdeviceid)
	{
		super(nbiotdeviceid);
	}

	@Override
	public void sendData(CommandTlv ct)
	{
		if ( open == false )
			return ;
		ChongQingOneNetHttpBuilder hb = new ChongQingOneNetHttpBuilder();
		hb.setImei(this.nbiotdeviceid);

		JSONObject json = new JSONObject();
				
		byte[] b = ct.getByte();
		
		if ( log.isInfoEnabled())
			Utils.print(String.format("Send data to %s ", this.getDeviceid()) , b);
		
		int c = 0 ;
		for ( int i = 0 ; i < b.length - 1 ; i ++ )
			c =  ( c ^ b[i]);
		b[b.length -1] = (byte)c;
		
		json.put("args", JWStringUtils.toHexString(b));

		String rs = json.toJSONString();
		log.info(rs);
		
		hb.postJson(rs , new AsyncHttpResponse());
	}


}
