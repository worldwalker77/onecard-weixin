package cn.worldwalker.onecard.weixin.common.interceptor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import cn.worldwalker.onecard.weixin.common.utils.RequestUtil;

public class LogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
            RequestUtil.setRequset(request);
            RequestUtil.setResponse(response);
            filterChain.doFilter(request, response);
    }
    
}
