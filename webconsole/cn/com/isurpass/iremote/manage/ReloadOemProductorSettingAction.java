package cn.com.isurpass.iremote.manage;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.iremote.action.camera.lechange.LeChangeRequestManagerStore;
import com.iremote.common.GatewayUtils;
import com.iremote.common.push.PushMessageThread;
import com.iremote.common.sms.SMSInterface;
import com.iremote.domain.OemProductor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.iremote.action.helper.OemProductorHelper;
import com.iremote.common.ServerRuntime;
import com.iremote.service.SystemParameterService;
import com.opensymphony.xwork2.Action;

import net.sf.json.JSONArray;

public class ReloadOemProductorSettingAction
{
	private static Log log = LogFactory.getLog(ReloadOemProductorSettingAction.class);
	public String execute()
	{
		OemProductorHelper.initOemProducotr();

		try{
			SystemParameterService systemParameterService = new SystemParameterService();
			String neoproductor = systemParameterService.getStringValue("neoproductor");
			Set<String> neoproductorset = new TreeSet<>();
			if("".equals(neoproductor)||neoproductor==null){
				log.error("The productor data of NEO load failed, loading the initial data from the code!");
				List<String> asList = Arrays.asList("025800030083","025800031083","025800032083","025800033083","025800034083","025800035083","025800036083","025800037083","025800038083","02580003008d","02580003108d","02580003208d","02580003308d","02580003408d","02580003508d","02580003608d","02580003708d","02580003808d","025800030023","025800031023","025800032023","025800033023","025800034023","025800035023","025800036023","025800037023","025800030086");
				neoproductorset = new TreeSet<>(asList);
			}else{
				JSONArray json = JSONArray.fromObject(neoproductor);
				neoproductorset.addAll(json);
			}
			ServerRuntime.getInstance().setNeoproductorset(neoproductorset);
			
			String leeproductor = systemParameterService.getStringValue("leedarsonproductor");
			Set<String> leeproductorset = new TreeSet<>();
			if("".equals(leeproductor)||leeproductor==null){
				log.error("The productor data of Leedarson load failed, loading the initial data from the code!");
				List<String> asList = Arrays.asList("030002000009","038402000009");
				leeproductorset = new TreeSet<>(asList);
			}else{
				JSONArray leejson = JSONArray.fromObject(leeproductor);
				leeproductorset.addAll(leejson);
			}
			ServerRuntime.getInstance().setLeedarsonproductorset(leeproductorset);
			
			String sirenproductor = systemParameterService.getStringValue("sirenproductor");
			Set<String> sirenproductorset = new TreeSet<>();
			if("".equals(sirenproductor)||sirenproductor==null){
				log.error("The productor data of Siren load failed, loading the initial data from the code!");
				List<String> asList = Arrays.asList("025800030088","025800031088","025800032088","025800033088","025800034088","025800036088","025800037088","025800038088");
				sirenproductorset = new TreeSet<>(asList);
			}else{
				JSONArray leejson = JSONArray.fromObject(sirenproductor);
				sirenproductorset.addAll(leejson);
			}
			ServerRuntime.getInstance().setSirenproductorset(sirenproductorset);
		}catch(Exception e){
			log.error(e.getMessage() , e);
		}
		return Action.SUCCESS;
	}
}
