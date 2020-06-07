package cn.itcast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 登录验证的过滤器
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1.强转，因为ServletRequest不能满足需求
        HttpServletRequest request= (HttpServletRequest) req;
        //2.获取请求资源路劲
        String uri = request.getRequestURI();
        //3.判断是否包含相关资源路径

        if(uri.contains("/loginServlet") || uri.contains("/login.jsp")  || uri.contains("/css/") || uri.contains("/js/")
                || uri.contains("/fonts/") || uri.contains("/checkCodeServlet")){
            //包含，则用户想登录，放行
            System.out.println("包含，则用户想登录，放行");
            chain.doFilter(req, resp);
        }else{
            //3.获取是否包含用户
            Object loginUser = request.getSession().getAttribute("loginUser");
            if(loginUser != null){
                //4.用户已登录，放行
                chain.doFilter(req, resp);
            }else{
                request.setAttribute("login_msg","用户未登录，请登录");
                request.getRequestDispatcher("login.jsp").forward(request,resp);
            }
        }

        //chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }


}
