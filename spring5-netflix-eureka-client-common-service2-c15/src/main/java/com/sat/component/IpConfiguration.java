package com.sat.component;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//由于server.port设置的0，这里这个方法就是来获取实际运行的port
@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent> {
	private static int port;
	
	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		this.port = event.getWebServer().getPort();
	}
	
	public static int getPort() {
		return port;
	}
	
}
