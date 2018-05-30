package Home;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

import controller.DataParamerController;






public class HomeConfig extends JFinalConfig {

	public void configConstant(Constants me) {
		PropKit.use("config.properties");
		me.setEncoding("utf-8");  
	    me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setDevMode(Boolean.parseBoolean(PropKit.get("devMode")));
		me.setError404View("/error.jsp");
		me.setError500View("error.jsp");
		me.setViewType(ViewType.JSP);
	}

	public void configRoute(Routes me) {
		me.add("/", DataParamerController.class);
		
	}

	public void configPlugin(Plugins me) {
		
		C3p0Plugin cp = new C3p0Plugin(PropKit.get("jdbcUrl_CCSTV4"), PropKit.get("user"), PropKit.get("password"));
		cp.setInitialPoolSize(1);
		cp.setMaxIdleTime(10);
		cp.setMinPoolSize(3);
		me.add(cp);
//		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		 ActiveRecordPlugin arp = new ActiveRecordPlugin("ccstv4",cp);
		me.add(arp);

		
		C3p0Plugin cp2= new C3p0Plugin(PropKit.get("jdbcUrl_ccstv4gzgy"), PropKit.get("user"), PropKit.get("password"));
		cp2.setInitialPoolSize(1);
		cp2.setMaxIdleTime(10);
		cp2.setMinPoolSize(3);
		me.add(cp2);
		 ActiveRecordPlugin arp2 = new ActiveRecordPlugin("ccstv4gzgy",cp2);
		me.add(arp2);
		
		C3p0Plugin cp3 = new C3p0Plugin(PropKit.get("jdbcUrl_ccstv4jsnt"), PropKit.get("user"), PropKit.get("password"));
		cp3.setInitialPoolSize(1);
		cp3.setMaxIdleTime(10);
		cp3.setMinPoolSize(3);
		me.add(cp3);
		 ActiveRecordPlugin arp3 = new ActiveRecordPlugin("ccstv4jsnt",cp3);
		me.add(arp3);
		
		C3p0Plugin cp4 = new C3p0Plugin(PropKit.get("jdbcUrl_ccstv4lndd"), PropKit.get("user"), PropKit.get("password"));
		cp4.setInitialPoolSize(1);
		cp4.setMaxIdleTime(10);
		cp4.setMinPoolSize(3);
		me.add(cp4);
		 ActiveRecordPlugin arp4 = new ActiveRecordPlugin("ccstv4lndd",cp4);
		me.add(arp4);
		
		C3p0Plugin cp5 = new C3p0Plugin(PropKit.get("jdbcUrl_ccstv4lngdg"), PropKit.get("user"), PropKit.get("password"));
		cp5.setInitialPoolSize(1);
		cp5.setMaxIdleTime(10);
		cp5.setMinPoolSize(3);
		me.add(cp5);
		 ActiveRecordPlugin arp5 = new ActiveRecordPlugin("ccstv4lngdg",cp5);
		me.add(arp5);
		
		C3p0Plugin cp6 = new C3p0Plugin(PropKit.get("jdbcUrl_ccstv4zjwz"), PropKit.get("user"), PropKit.get("password"));
		cp6.setInitialPoolSize(1);
		cp6.setMaxIdleTime(10);
		cp6.setMinPoolSize(3);
		me.add(cp6);
		 ActiveRecordPlugin arp6 = new ActiveRecordPlugin("ccstv4zjwz",cp6);
		me.add(arp6);
		
		C3p0Plugin cp7 = new C3p0Plugin(PropKit.get("jdbcUrl_qy_mobclouddb"), PropKit.get("user"), PropKit.get("password"));
		cp6.setInitialPoolSize(1);
		cp6.setMaxIdleTime(10);
		cp6.setMinPoolSize(3);
		me.add(cp7);
		 ActiveRecordPlugin arp7 = new ActiveRecordPlugin("qymobclouddb",cp7);
		me.add(arp7);
		
//		_MappingKit.mapping(arp);

	}

	public void configInterceptor(Interceptors me) {
//		me.add(new CheckUser());
	}

	public void configHandler(Handlers me) {
	}
}
