package com.ismyblue.web.filter;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.ismyblue.entity.User;
import com.ismyblue.field.http.SessionAttr;
import com.ismyblue.service.UserService;

/**
 * 
*    
* 项目名称：Blog   
* 类名称：EncodingFilter   
* 类描述：   通用解决 get 和 post乱码过滤器 
* 使用此过滤器之后，不必再考虑http请求与响应的编码问题
* @version    
*
 */
public class EncodingFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {		
		
		User loguser = new UserService().findUser(18);		
		((HttpServletRequest) request).getSession().setAttribute(SessionAttr.USER_STRING, loguser);
		
		// 处理请求乱码
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletRequest myRequest = new MyRequest(httpServletRequest);		
		if(myRequest.getServletPath().equals("/api/captcha") || myRequest.getServletPath().contains(".htm")){
			chain.doFilter(myRequest, response);
			return ;
		}			
		
		// 处理响应乱码
		response.setContentType("text/html;charset=utf-8");
		chain.doFilter(myRequest, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}

// 自定义request对象 使用包装器
class MyRequest extends HttpServletRequestWrapper {

	private HttpServletRequest request;

	private boolean hasEncode;

	public MyRequest(HttpServletRequest request) {
		super(request);// super必须写
		this.request = request;
	}

	// 对需要增强方法 进行覆盖
	@Override
	public Map<String,String[]> getParameterMap() {
		
		// 先获得请求方式
		String method = request.getMethod();
		if (method.equalsIgnoreCase("post")) {
			// post请求
			try {
				// 处理post乱码
				request.setCharacterEncoding("utf-8");
				return request.getParameterMap();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else if (method.equalsIgnoreCase("get")) {
			// get请求
			Map<String, String[]> parameterMap = request.getParameterMap();
			if (!hasEncode) { // 确保get手动编码逻辑只运行一次
				for (String parameterName : parameterMap.keySet()) {
					String[] values = parameterMap.get(parameterName);
					if (values != null) {
						for (int i = 0; i < values.length; i++) {
							try {
								// 处理get乱码
								values[i] = new String(values[i]
										.getBytes("ISO-8859-1"), "utf-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
					}
				}
				hasEncode = true;
			}
			return parameterMap;
		}
		return super.getParameterMap();
	}

	@Override
	public String getParameter(String name) {
		Map<String, String[]> parameterMap = getParameterMap();
		String[] values = parameterMap.get(name);
		if (values == null) {
			return null;
		}
		return values[0]; // 取回参数的第一个值
	}

	@Override
	public String[] getParameterValues(String name) {
		Map<String, String[]> parameterMap = getParameterMap();
		String[] values = parameterMap.get(name);
		return values;
	}

}
