package com.iremote.common.sms;

import com.iremote.action.helper.OemProductorHelper;
import com.iremote.common.JWStringUtils;
import com.iremote.common.constant.OemProductorAttributeCode;
import com.iremote.domain.OemProductor;
import com.iremote.domain.OemProductorAttribute;
import com.iremote.service.OemProductorService;
import io.netty.util.IllegalReferenceCountException;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.IRemoteConstantDefine;

public class SMSSendThread implements Runnable
{
	private static Log log = LogFactory.getLog(SMSSendThread.class);
	
	SMSVo vo;

	public SMSSendThread(SMSVo vo) {
		super();
		this.vo = vo;
	}

	@Override
	public void run() 
	{
		if ( vo != null && StringUtils.isNotBlank(vo.getMessage().getMessage()) )
		{
			if (checkPhoneNumber(vo)) {
				if (log.isInfoEnabled())
					log.info(String.format("send SMS %s to %s ", vo.getMessage().getMessageforLog(), vo.getPhonenumber()));
				SMSInterface.sendSMS(vo.getCountrycode(), vo.getPhonenumber(), vo.getMessage(), vo.getPlatform());
			} else {
				if (log.isInfoEnabled())
					log.info(String.format("can't send SMS %s to %s", vo.getMessage().getMessageforLog(), vo.getPhonenumber()));
			}
		}
	}

	private boolean checkPhoneNumber(SMSVo vo) {
		if (equalsCountryCode(vo.getCountrycode(), IRemoteConstantDefine.INTERNATIONAL_DIALING_CODE_CHINE)) {
			return true;
		}
		if (IRemoteConstantDefine.PLATFORM_SINGAPORE == vo.getPlatform()
				&& equalsCountryCode(vo.getCountrycode(), IRemoteConstantDefine.INTERNATIONAL_DIALING_CODE_INDIA)) {
			return true;
		}

		return checkOemProdctorSetting(vo.getPlatform());
	}

	private boolean checkOemProdctorSetting(int platform) {
		OemProductor oemProductor = new OemProductorService().querybyplatform(platform);
		if (oemProductor == null || JWStringUtils.isEmpty(oemProductor.getOemproductorattributelist())) {
			return false;
		}
		return OemProductorHelper.hasAbroadSmsPermission(oemProductor);
	}

	private boolean equalsCountryCode(String countryCode, String s) {
		return s.equals(countryCode) || ("+" + s).equals(countryCode);
	}


}
