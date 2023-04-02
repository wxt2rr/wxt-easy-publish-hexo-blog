package com.wangxt.easy.publish.hexo.blog;

import com.wangxt.easy.publish.hexo.blog.handle.MainService;
import com.wangxt.easy.publish.hexo.blog.pojo.GlobalConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WxtEasyPublishHexoBlogApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WxtEasyPublishHexoBlogApplication.class, args);
		GlobalConfig.setContext(context);

		MainService service = new MainService();
		try {
			service.handle(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
