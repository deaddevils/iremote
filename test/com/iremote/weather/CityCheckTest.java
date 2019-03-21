package com.iremote.weather;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.phoneuser.QuerySunriseOrSunsetTimeHelper;
import com.iremote.common.http.HttpUtil;
import com.iremote.domain.City;
import com.iremote.domain.Province;
import com.iremote.domain.Region;
import com.iremote.service.CityService;
import com.iremote.service.ProvinceService;
import com.iremote.service.RegionService;
import com.iremote.test.db.Db;

public class CityCheckTest {

	public static void main(String[] args) {
		Db.init();
		
		long s = QuerySunriseOrSunsetTimeHelper.querySunriseOrSunsetTime(7, 1);
		System.out.println(s);
		Db.commit();
	}
	public static void amain(String[] args) {
		Db.init();
		RegionService rs = new RegionService();
		ProvinceService ps = new ProvinceService();
		CityService cs = new CityService();
		List<Region> allregion = rs.queryAllRegion();
		String key = "aV3UzkErYHEF1Khi34hrpfINLJGGuIsd";
		List<String> unusecity = new ArrayList<>();
		for (Region r : allregion) {
			List<Province> provinceunregion = ps.queryByRegionid(r.getRegionid());
			for (Province p : provinceunregion) {
				// Province p = ps.queryByProvinceid(13);

				List<City> cityunprovince = cs.queryByProvinceid(p.getProvinceid());
				for (City c : cityunprovince) {
					String url = "http://dataservice.accuweather.com/locations/v1/cities/CA/" + p.getAdmincode()
							+ "/search";
					JSONObject jsonparameter = new JSONObject();
					jsonparameter.put("apikey", key);
					jsonparameter.put("q", c.getName());
					jsonparameter.put("language", "en-us");
					jsonparameter.put("details", true);
					String resultjson = HttpUtil.httpGet(url, jsonparameter, null);
					if ("[]".equals(resultjson)) {
						System.out.println("provinceid:" + c.getProvinceid() + ";cityid:" + c.getCityid() + ";code:"
								+ c.getCode() + ";name:" + c.getName());
						unusecity.add("provinceid:" + c.getProvinceid() + ";cityid:" + c.getCityid() + ";code:"
								+ c.getCode() + ";name:" + c.getName());
					}
				}

			}
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("d:\\aaabbbccc.txt"));
			for (String s : unusecity) {
				bw.write(s);
				bw.newLine();
				bw.flush();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
