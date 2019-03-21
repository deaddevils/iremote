package com.iremote.action.amazon.smarthome;

import com.alibaba.fastjson.JSON;
import com.iremote.test.db.Db;

public class AmazonRequestActionTest {

	public static void main(String arg[])
	{
		Db.init();
		String str = "{\"directive\": {\"header\": {\"namespace\": \"Alexa.Discovery\", \"name\": \"Discover\", \"payloadVersion\": \"3\", \"messageId\": \"1bd5d003-31b9-476f-ad03-71d471922820\"}, \"payload\": {\"scope\": {\"type\": \"BearerToken\", \"token\": \"access-token-from-skill\"}}}}";
		String str_opt = "{\"directive\": {\"header\": {\"namespace\": \"Alexa.PowerController\", \"name\": \"TurnOn\", \"payloadVersion\": \"3\", \"messageId\": \"0c2e330f-c681-41e1-b7da-d3f063cf7a9f\", \"correlationToken\": \"AAAAAAAAAAAYF7CskcyLEI4IxaiK3zG8cAEAAAAAAAAu6uBo7NbqBAEwC21XWY/6dkYtG/Et1MmXYJF2nmbBSj93P2ein6mq9lkyRQqVlAOEJwqjAgwxQW6INU9ozkfwRWKud2sRyaIqLoz44JX8L6d46NjAySBDIvJxEToVYjN+3dJivwsLyRHrod++tvofUUWePBBiJcTbyIYthtFK/2yky12r8PXuExiWvuagKD4L835pYymy4IxcMt5ziIkcBj7wllLxE98asMJh6Nv7Nv7VRGIP+ZsiYEtYsqMdgr7bsGWrXG77H+LIDavxwaLEGazOGl1Xx1l7h8bgBQpHGv/2Nh4yJ+bkHOi5PTEli7Mt/CAGl89tD7BC8ZzcoAh6BIQXaa4LKAsqzEI/60FXxFBkOXEzXRQi8aW7Wf/skxgR4lgqMOLsZGNU9D8MYDCTsrmYwfj3RVJ+IUoH20y/eogQHLfK6KXNtirMZ5N5YibEXpdAPFnNdGivSa1VDNy28964LKMh+wVD5Q+zT4zp/A==\"}, \"endpoint\": {\"scope\": {\"type\": \"BearerToken\", \"token\": \"hellotoken1513413781185\"}, \"endpointId\": \"zwave_1234\", \"cookie\": {}}, \"payload\": {}}}";
		String str_dim = "{\"directive\": {\"header\": {\"namespace\": \"Alexa.BrightnessController\", \"name\": \"AdjustBrightness\", \"payloadVersion\": \"3\", \"messageId\": \"d6df75c9-b6ce-436c-be01-2005a0176529\", \"correlationToken\": \"AAAAAAAAAADpmfyKRJagIVMhabuySEg3BAIAAAAAAAClOGAY3+1LGCqWhPUV1H8MBAEdejhtav9kqHpV6vJU2aQuvqIVFGgc9Q+MjdoZTjLY8eb+x3diseQ96+fhGit2vxtj9exAu5g6+EdOgy0uiRmnl671fTw1avDFB2j6MUTCkWFD/yEqjwo44+2EpSwUNV0CcDRvh1ETlxvBmQRlNLcaCan6msBVSk8zHsaJ/3WhCmB6oKmKnLvpFZQlXObtBkvLWzrwC8Jc0shY7TYg4O15dcHbLpHHQUurCqouJlestG6XcBvTKET+eavpfdm83IDTfA6jI09v7CYJUkO5Ro2V/Hng4r3ZeJwdcV/C9yIYY6IuB/Gxy3H8sxKyu+/tXVrCigmg6KIJ1ud0YU2WDy+Gvmak3cuYgUVx/ucN3HvtEiaVqw6pIGhIynbknpXImmffVK/upABGFhClW+sb6VMKdOthKPc3XGpfwoHWhlAVQGltZfGP9zqCJVEkuFmJtJT67s3JLM96m4Kc1zvem/Mpu0HdoYQ93/Dk0hByVSwn4Vf5CrZOO0ckHzSpDazl1D5j9guj7fU3lzpG7tKcKTj0NxHlBt2KyBTzkWZ5S2beoy5bhkamQ35YY9TPNBYflltaksTa2stFwzmbgqXh62p7Q01hO2RLm8/XFIs7KkqRS2iDUpa4SiFcNrsz/9tmYb2cF+Kx8WEj5X/rfKWV5RhGudM=\"}, \"endpoint\": {\"scope\": {\"type\": \"BearerToken\", \"token\": \"70e09a03-fb4c-46a0-8cef-5e553ce952d4\"}, \"endpointId\": \"zwave_11189_0\", \"cookie\": {}}, \"payload\": {\"brightnessDelta\": -10}}}";
		
		AmazonRequestAction a = new AmazonRequestAction();
		a.execute(str_dim);
		System.out.println(JSON.toJSONString(a));
		
		Db.commit();
	}
}
