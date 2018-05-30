package Home;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;



public class CheckUser implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		Controller c = ai.getController();
		String s = c.getRequest().getRequestURI();
		if (!s.equals("/") 
				&& !s.equals("/check")
				&& !s.equals("/server/upyuncardno")
				&& !s.equals("/carset")) {
//			Users user = c.getSessionAttr("_user");
//			
//			IF (USER == NULL) {
//
//				C.REDIRECT("/");// 如果将LOGIN命名为其他字符串就会死循环
//                RETURN;
//			}
		}
		ai.invoke();

	}

}
