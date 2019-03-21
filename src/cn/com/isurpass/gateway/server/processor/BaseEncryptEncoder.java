package cn.com.isurpass.gateway.server.processor;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;

import cn.com.isurpass.gateway.server.NettyConnectionWrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public abstract class BaseEncryptEncoder  extends MessageToMessageEncoder<CommandTlv> 
{
	private static Set<String> ENCRPYT_COMMAND = new HashSet<String>();
	private static Log log = LogFactory.getLog(BaseEncryptEncoder.class);
	
	static 
	{
		ENCRPYT_COMMAND.add(makekey(TagDefine.COMMAND_CLASS_DEVICE,TagDefine.COMMAND_SUB_CLASS_DEVICE_COMMAND));
		ENCRPYT_COMMAND.add(makekey(TagDefine.COMMAND_CLASS_DEVICE,TagDefine.COMMAND_SUB_CLASS_DEVICE_GROUP_COMMAND));
		ENCRPYT_COMMAND.add(makekey(TagDefine.COMMAND_CLASS_DEVICE,TagDefine.COMMAND_SUB_CLASS_DEVICE_BATCH_COMMAND));
	}
	
	
	@Override
	protected void encode(ChannelHandlerContext ctx, CommandTlv content, List<Object> out) throws Exception 
	{
		if ( content == null)
			return ;
		
		if ( ! needEncrypte(content) )
		{
			out.add(content.getByte());
			return ;
		}
		
		SecureRandom sr = new SecureRandom();
		content.addUnit(new TlvIntUnit(TagDefine.TAG_RANDOM , sr.nextInt() , 4));
		content.addUnit(new TlvIntUnit(TagDefine.TAG_RANDOM , (int)(System.currentTimeMillis()/1000) , 4));
		if ( content.getUnitbyTag(TagDefine.TAG_TIME) == null )
			content.addOrReplaceUnit(new TlvIntUnit(TagDefine.TAG_TIME,(int)(System.currentTimeMillis()/1000) , 4));
		
		NettyConnectionWrap ncw = new NettyConnectionWrap(ctx);
		byte[] cb = content.getByte();
		if ( log.isInfoEnabled())
			Utils.print(String.format("Send data to %s(%d)" , ncw.getDeviceid() , ncw.getConnectionHashCode()),cb );

		byte[] rst = encrypt( ncw.getAttachment() ,cb );
		if ( rst == null )
			return ;

		CommandTlv ct = new CommandTlv(TagDefine.COMMAND_CLASS_ENCRYPT , TagDefine.COMMAND_SUB_CLASS_ENCRYPT_REQUEST);
		ct.addUnit(new TlvByteUnit(getEncryptTag() , rst));
						
		out.add(ct.getByte());
	}
	
	protected abstract int getEncryptTag();
	
	protected abstract byte[] encrypt(Remoter remoter , byte[] content);

	protected boolean needEncrypte(CommandTlv content)
	{
		return ENCRPYT_COMMAND.contains(makekey(content.getCommandclass() , content.getCommandsubclass()));
	}
	
	private static String makekey(int commandclass , int subcommandclass)
	{
		return String.format("%d_%d", commandclass ,subcommandclass );
	}
}
