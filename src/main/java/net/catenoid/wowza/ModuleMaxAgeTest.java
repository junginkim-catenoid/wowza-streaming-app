package net.catenoid.wowza;

import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.http.IHTTPProvider;
import com.wowza.wms.http.IHTTPRequest;
import com.wowza.wms.http.IHTTPResponse;
import com.wowza.wms.httpstreamer.cupertinostreaming.httpstreamer.HTTPStreamerApplicationContextCupertinoStreamer;
import com.wowza.wms.httpstreamer.model.IHTTPStreamerRequestContext;
import com.wowza.wms.httpstreamer.model.IHTTPStreamerSession;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.stream.IMediaStream;
import com.wowza.wms.vhost.IVHost;

public class ModuleMaxAgeTest extends ModuleBase {

	private IApplicationInstance appInstance = null;

	public void onAppStart(IApplicationInstance appInstance) {
		this.appInstance = appInstance;
		getLogger().info("max-age-test2-app App Start: START");
	}

	public void onAppStop(IApplicationInstance appInstance) {
		getLogger().info("max-age-test2-app App STOP: STOP");
	}

	public void onStreamCreate(IMediaStream stream) {
		getLogger().info("max-age-test2-app-1 ModulePublishOnTextData#PublishThread.run["+stream.getContextStr()+"]: START");
		getLogger().info("ModulePublishOnTextData#PublishThread.run["+stream.getContextStr()+"]: START");
		getLogger().info(" ======================== 1 " + appInstance.getHTTPStreamerProperties());
		appInstance.getLiveStreamPacketizerProperties().setProperty("cupertinoChunkDurationTarget", 5000);	// 5000
		appInstance.getLiveStreamPacketizerProperties().setProperty("cupertinoPlaylistChunkCount", 4);	// 4


		IHTTPStreamerSession httpSession = stream.getHTTPStreamerSession();
		if (httpSession != null) {
			getLogger().info("max-age-test2-app-2 http session exist ");
			httpSession.setUserHTTPHeader("Cache-Control", "max-age=2");
		} else {
			getLogger().info("max-age-test2-app-2 http session not exist ");
		}

		HTTPStreamerApplicationContextCupertinoStreamer appContext = (HTTPStreamerApplicationContextCupertinoStreamer) appInstance.getHTTPStreamerApplicationContext("cupertinostreaming", false);
		if (appContext != null) {
			getLogger().info("max-age-test2-app-2 HTTPStreamerApplicationContextCupertinoStreamer exist ");
			appContext.setCacheControlPlaylist("max-age=2");
		} else {
			getLogger().info("max-age-test2-app-2 HTTPStreamerApplicationContextCupertinoStreamer not exist ");
		}



//		appInstance.getHTTPStreamerProperties().setProperty("cupertinoCacheControlPlaylist", "max-age=2");		// max-age=2
		getLogger().info(" ======================== 2 " + appInstance.getHTTPStreamerProperties());
	}


	public void onHTTPSessionCreate(IHTTPStreamerSession httpSession) {
		getLogger().info("max-age-test2-app-2 before onHTTPSessionCreate ======================== 3 " + httpSession.getUserHTTPHeaders().toString());
//		httpSession.setUserHTTPHeader("Cache-Control", "max-age=2");

		httpSession.setUserHTTPHeader("Custom-Cache-Control", "max-age=2");
		getLogger().info("max-age-test2-app-2 after ======================== 3 " + httpSession.getUserHTTPHeaders().toString());
	}

	/*
	public void onHTTPRequest(IVHost ivHost, IHTTPRequest req, IHTTPResponse res) {

		try {
			getLogger().error("wowza-text-app-demo onHTTPRequest start===================================1");
			res.setHeader("Cache-Control", "max-age=2");

			getLogger().info("wowza-text-app-demo-onHTTPRequest ======================== 3 " + res.getHeaders());
		} catch (Exception e) {
			getLogger().error("wowza-text-app-demo onHTTPRequest error===================================");
		}
	}
	 */



}
