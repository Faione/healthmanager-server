package stu.swufe.healthmanager.config;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@Log4j
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    @Value("${server.port}")
    private int serverPort;



    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            log.info("SwaggerUI Starting At: " + "http://"+inetAddress.getHostAddress()+":"+serverPort+"/swagger-ui.html");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }
}
