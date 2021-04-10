package stu.swufe.healthmanager.config;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import stu.swufe.healthmanager.util.TestUtil;
import stu.swufe.healthmanager.util.TextUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@Log4j
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    @Value("${server.port}")
    private int serverPort;

    // 增加 ‘:’ ，当为配置值时，默认值为 ‘:’ 之后的值
    @Value("${server.address:}")
    private String serverAddress;


    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        try {
            // 没有配置外网地址，则使用内网地址
            if(TextUtils.isEmpty(serverAddress)){
                InetAddress inetAddress = InetAddress.getLocalHost();
                serverAddress = inetAddress.getHostAddress();
            }

            log.info("SwaggerUI Starting At: " + "http://"+serverAddress+":"+serverPort+"/swagger-ui.html");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }
}
