package com.iremote.action.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.City;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Province;
import com.iremote.domain.Region;
import com.iremote.service.CityService;
import com.iremote.service.ProvinceService;
import com.iremote.service.RegionService;
import com.iremote.vo.CityVO;
import com.iremote.vo.ProvinceVO;
import com.iremote.vo.RegionVO;
import com.opensymphony.xwork2.Action;

public class QueryLocationDataAction {

	private int resultCode = ErrorCodeDefine.SUCCESS;
	private PhoneUser phoneuser;
	List<RegionVO> regions = new ArrayList<RegionVO>(); 
	private static Log log = LogFactory.getLog(QueryLocationDataAction.class);
	
	public String execute(){
		ResourceBundle rb;
		String language = phoneuser.getLanguage();
		if (IRemoteConstantDefine.DEFAULT_LANGUAGE.equals(language)) {
			rb = ResourceBundle.getBundle("citydata", Locale.SIMPLIFIED_CHINESE);
		} else if (IRemoteConstantDefine.DEFAULT_ZHHK_LANGUAGE.equals(language)) {
			rb = ResourceBundle.getBundle("citydata", Locale.TRADITIONAL_CHINESE);
		} else {
			rb = ResourceBundle.getBundle("citydata", Locale.US);
		}
	    
		RegionService rs = new RegionService();
		ProvinceService ps = new ProvinceService();
		CityService cs = new CityService();
		List<Region> allregion = rs.queryAllRegion();
		
		for(Region r : allregion){
			RegionVO reg = new RegionVO();
			List<Province> provinceunregion = ps.queryByRegionid(r.getRegionid());
			List<ProvinceVO> pro = new ArrayList<ProvinceVO>();
			for(Province p : provinceunregion){
				ProvinceVO provincevo = new ProvinceVO();
				List<City> cityunprovince = cs.queryByProvinceid(p.getProvinceid());
				List<CityVO> cit = new ArrayList<CityVO>();
				for(City c:cityunprovince){
					CityVO cityvo = new CityVO();
					cityvo.setCityid(c.getCityid());
					try {
						cityvo.setName(rb.getString(c.getCode()));
					} catch (Exception e) {
						cityvo.setName(c.getName());
						log.error(e.getMessage());
					}
					cit.add(cityvo);
				}
				provincevo.setProvinceid(p.getProvinceid());
				try {
					provincevo.setName(rb.getString(p.getCode()));
				} catch (Exception e) {
					provincevo.setName(p.getName());
					log.error(e.getMessage());
				}
				provincevo.setCities(cit);
				pro.add(provincevo);
			}
			reg.setRegionid(r.getRegionid());
			try {
				reg.setName(rb.getString(r.getCode()));
			} catch (Exception e) {
				reg.setName(r.getName());
				log.error(e.getMessage());
			}
			reg.setProvinces(pro);
			regions.add(reg);
		}
		
	       
	    return Action.SUCCESS;
	}
	
	
	public int getResultCode() {
		return resultCode;
	}
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}


	public List<RegionVO> getRegions() {
		return regions;
	}

}
