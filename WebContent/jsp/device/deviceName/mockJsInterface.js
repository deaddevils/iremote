/**
 * 
 */
if ( typeof(JSInterface) == "undefined" )
{
	window.JSInterface = {
			sumbit : function()
			{
				var url = "/iremote/webconsole/listappliance?deviceid=" + deviceid;
				window.location.href=url;
			},
			back : function()
			{
				var url = "/iremote/webconsole/listappliance?deviceid=" + deviceid;
				window.location.href=url;
			}
	};	
}