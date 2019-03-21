package com.iremote.action.infraredcode2;

import com.iremote.domain.InfreredDeviceProductor;
import com.iremote.service.InfreredDeviceProductorService;
import com.iremote.vo.ProductorListVO;
import com.opensymphony.xwork2.Action;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class QueryProductor {

    private static Log log = LogFactory.getLog(QueryProductor.class);
    private String devicetype;
    private String language = "zh_CN";
    private int resultCode;
    private List<ProductorListVO> productorlist = new ArrayList<ProductorListVO>();

    public String execute() 
    {
    	InfreredDeviceProductorService svr = new InfreredDeviceProductorService();
    	
    	List<InfreredDeviceProductor> lst = svr.queryByDeviceType(devicetype);
    	if ( lst == null || lst.size() == 0 )
    		return Action.SUCCESS;
    	
    	for ( InfreredDeviceProductor idp : lst )
    	{
    		ProductorListVO productorListVO = null;
            if ("zh_CN".equalsIgnoreCase(language)) {
                productorListVO  = new ProductorListVO(idp.getName(), idp.getProductor());
            } else/* if ("en_US".equalsIgnoreCase(language)) */{
                productorListVO = new ProductorListVO(idp.getProductor(), idp.getProductor());
            } /*else {
                resultCode = ErrorCodeDefine.PARMETER_ERROR;
                return Action.SUCCESS;
            }*/
            productorlist.add(productorListVO);
    	}
    	
        return Action.SUCCESS;
    }

    public int getResultCode()
    {
        return resultCode;
    }

    public List<ProductorListVO> getProductorlist() {
        return productorlist;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
