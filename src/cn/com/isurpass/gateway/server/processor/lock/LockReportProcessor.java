package cn.com.isurpass.gateway.server.processor.lock;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.ProcessorStore;
import com.iremote.infraredtrans.ReportProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;

public class LockReportProcessor extends ReportProcessor 
{
	private TeaGatewaySecurityKey skutil = new TeaGatewaySecurityKey();
	
	@Override
	protected IRemoteRequestProcessor getProcessor(byte[] b) 
	{
		IRemoteRequestProcessor pro = LockProcessorStore.getInstance().getProcessor(b, 0);
		if ( pro != null )
			return pro;
		
		pro = ProcessorStore.getInstance().getProcessor(b , 0);

		return pro ;
	}

	@Override
	protected void sendLoginRequest(IConnectionContext nbc) 
	{
		if ( nbc.isOpen() == false )
			return ;
		
		CommandTlv ct = new CommandTlv(100 , 1);
		
		nbc.getAttachment().setToken(Utils.createsecuritytoken(32));
		nbc.getAttachment().setTime1((int)(System.currentTimeMillis()/1000));
		nbc.getAttachment().setKey1(Utils.createsecuritykey(16));
		
		ct.addUnit(new TlvByteUnit(100,nbc.getAttachment().getToken().getBytes()));
		ct.addUnit(new TlvIntUnit(104 , nbc.getAttachment().getTime1() , 4));
		ct.addUnit(new TlvIntUnit(31 , nbc.getAttachment().getSequence() , 1));
		ct.addUnit(new TlvIntUnit(105 , 60 , 2));
		ct.addUnit(new TlvByteUnit(23 ,skutil.encryptKey1(nbc.getAttachment().getKey1(), nbc.getAttachment().getToken(), nbc.getAttachment().getTime1()) ));

		nbc.sendData(ct);
	}

	
}
