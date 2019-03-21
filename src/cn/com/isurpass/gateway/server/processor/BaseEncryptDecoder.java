package cn.com.isurpass.gateway.server.processor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.TlvWrap;

import cn.com.isurpass.gateway.server.NettyConnectionWrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public abstract class BaseEncryptDecoder extends MessageToMessageDecoder<byte[]> 
{

	private static Set<String> ENCRPYT_COMMAND = new HashSet<String>();
	
	static 
	{
		ENCRPYT_COMMAND.add(makekey(TagDefine.COMMAND_CLASS_GATEWAY_LOGIN,TagDefine.COMMAND_SUB_CLASS_GATEWAY_LOGIN_RESPONSE));
		ENCRPYT_COMMAND.add(makekey(TagDefine.COMMAND_CLASS_ENCRYPT,TagDefine.COMMAND_SUB_CLASS_SECURITYKEY_RESPONSE));
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, byte[] content, List<Object> out) throws Exception 
	{
		if ( content == null || content.length < 2 )
			return ; 
		
		if ( ENCRPYT_COMMAND.contains(makekey(content[0] , content[1]))) // this command should encrypt , discard all not encrypt data.
			return ;
		
		if ( content[0] != TagDefine.COMMAND_CLASS_ENCRYPT || content[1] != TagDefine.COMMAND_SUB_CLASS_ENCRYPT_REQUEST)
		{
			out.add(content);
			return ;
		}
		
		Utils.print("Receive encrypt data", content);
		
		byte[] ce = TlvWrap.readTag(content, getEncryptTag(), 4);
		
		if ( ce == null )
			return ;
		
		NettyConnectionWrap ncw = new NettyConnectionWrap(ctx);
		byte[] rst = decipher( getCurrentkey(ncw), ce);
		
		if ( rst != null )
			out.add(rst);
	}

	private byte[] getCurrentkey(NettyConnectionWrap ncw)
	{
		if ( ncw.getAttachment().isHaslogin() == false )
			return ncw.getAttachment().getKey1();
		else if ( ncw.getAttachment().getKey3() != null )
			return ncw.getAttachment().getKey3();
		return ncw.getAttachment().getKey2();
	}
	
	protected abstract int getEncryptTag();
	
	protected abstract byte[] decipher(byte[] key , byte[] content);

	private static String makekey(int commandclass , int subcommandclass)
	{
		return String.format("%d_%d", commandclass ,subcommandclass );
	}
}
