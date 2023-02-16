package net.catenoid.wowza;

import com.wowza.wms.amf.AMFDataList;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.IClient;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.request.RequestFunction;
import com.wowza.wms.stream.IMediaStream;

public class ModuleMaxAgeTest extends ModuleBase {

	private IApplicationInstance appInstance = null;

	public void onAppStart(IApplicationInstance appInstance) {
		this.appInstance = appInstance;

		getLogger().info("wowza-text-app-demo App Start: START");
	}

	public void onAppStop(IApplicationInstance appInstance) {
		getLogger().info("wowza-text-app-demo App STOP: STOP");
	}

	public void onStreamCreate(IMediaStream stream) {
		getLogger().info("wowza-text-app-demo ModulePublishOnTextData#PublishThread.run["+stream.getContextStr()+"]: START");
		getLogger().info("ModulePublishOnTextData#PublishThread.run["+stream.getContextStr()+"]: START");
		getLogger().info(" ======================== 1 " + appInstance.getHTTPStreamerProperties());
		appInstance.getLiveStreamPacketizerProperties().setProperty("cupertinoChunkDurationTarget", 5000);	// 5000
		appInstance.getLiveStreamPacketizerProperties().setProperty("cupertinoPlaylistChunkCount", 4);	// 4
		appInstance.getHTTPStreamerProperties().setProperty("cupertinoCacheControlPlaylist", "max-age=2");		// max-age=2
		getLogger().info(" ======================== 2 " + appInstance.getHTTPStreamerProperties());
	}

	public void onStreamDestroy(IMediaStream stream) {

	}

	public void publish(IClient client, RequestFunction function, AMFDataList params) {

	}
}
