package net.catenoid.wowza;

import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.httpstreamer.cupertinostreaming.httpstreamer.HTTPStreamerApplicationContextCupertinoStreamer;
import com.wowza.wms.httpstreamer.model.IHTTPStreamerSession;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.stream.IMediaStream;

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
		getLogger().info("max-age-test2-app-1 ModulePublishOnTextData#PublishThread.run[" + stream.getContextStr() + "]: START");
		getLogger().info("ModulePublishOnTextData#PublishThread.run[" + stream.getContextStr() + "]: START");
		getLogger().info(" ======================== 1 " + appInstance.getHTTPStreamerProperties());
		appInstance.getLiveStreamPacketizerProperties().setProperty("cupertinoChunkDurationTarget", 5000);    // 5000
		appInstance.getLiveStreamPacketizerProperties().setProperty("cupertinoPlaylistChunkCount", 4);    // 4


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
		getLogger().info(" ======================== 1 " + appInstance.getHTTPStreamerProperties());
	}


	public void onHTTPSessionCreate(IHTTPStreamerSession httpSession) {
		getLogger().info("max-age-test2-app-2 before onHTTPSessionCreate ======================== 2 " + httpSession.getUserHTTPHeaders().toString());

		getLogger().info("max-age-test2-app-2 after ======================== 2 " + httpSession.getUserHTTPHeaders().toString());


		// stream 구분자
		IMediaStream stream = httpSession.getStream();
		String streamName = "is null stream";
		if (stream != null) {
			streamName = stream.getName();
		}
		getLogger().info(String.format("max-age-test2-app-2 Logging '%s' '%s' ", httpSession.getSessionId(), streamName));

		switch (streamName) {
			case "myStream":
				httpSession.setUserHTTPHeader("Custom-Cache-Control", "max-age=2");
				break;
			case "max-age-test":
				httpSession.setUserHTTPHeader("Custom-Cache-Control", "max-age=3");
				break;
			default:
				httpSession.setUserHTTPHeader("Custom-Cache-Control", "max-age=4");
				break;
		}
	}
}

