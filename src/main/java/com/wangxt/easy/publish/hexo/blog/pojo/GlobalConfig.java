package com.wangxt.easy.publish.hexo.blog.pojo;

import org.springframework.context.ApplicationContext;

public class GlobalConfig {

	private static ApplicationContext context = null;

	private static GlobalConfig instance;

	public static ApplicationContext getContext() {
		//超时时间2分钟
		int remainTime = 120000;
		while (context == null) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			remainTime -= 2000;
			if (remainTime <= 0) {
				throw new RuntimeException("ApplicationContext is null");
			}
		}
		return context;
	}

	public static void setContext(ApplicationContext context) {
		GlobalConfig.context = context;
	}

	public static GlobalConfig getInstance() {
		if (instance == null) {
			ApplicationContext wac = getContext();
			instance = wac.getBean(GlobalConfig.class);
		}
		return instance;
	}

}
