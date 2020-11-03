package cn.tedu.sp11.filter;

import cn.tedu.web.util.JsonResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccessFilter extends ZuulFilter {
    //  配置过滤器类型:pre,routes,post,error
    @Override
    public String filterType() {
//        return "pre";
        return FilterConstants.PRE_TYPE;
    }

    //当前过滤器添加到那个位置,返回一个序列号
    @Override
    public int filterOrder() {
        /*
            前置过滤其中已经存在5个默认的过滤器,
            在第5个过滤器中,向上下文对象添加了"service-id"属性
         */
        return 6;
    }

    //针对当前请求进行判断,是否执行过滤代码(run方法)
    @Override
    public boolean shouldFilter() {
        //当前请求,调用的是否是 item-service
        //如果请求item,执行过滤代码
        //否则跳过过滤代码

        //获得正在调用的服务id
        RequestContext currentContext = RequestContext.getCurrentContext(); //zuul请求上下文
        String serviceId = (String)currentContext.get(FilterConstants.SERVICE_ID_KEY); //从上下文对象获取"服务ID"

        return "item-service".equalsIgnoreCase(serviceId);
    }

    //  过滤方法,权限判断写在这里
    @Override
    public Object run() throws ZuulException {
        //http://localhost:3001/item-service/faaf   没有登录不允许访问
        //http://localhost:3001/item-service/faaf?token=fafafaf   有token认为已登录,可以访问

        //获得request
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        //用 request 接受token参数
        String token = request.getParameter("token");

        //如果token参数为空,组织继续访问,返回登录提示
        if(StringUtils.isEmpty(token)){
            //组织继续访问
            currentContext.setSendZuulResponse(false);
            String json = JsonResult.err().msg("not login in").data(JsonResult.NOT_LOGIN).toString();
            currentContext.setResponseStatusCode(JsonResult.NOT_LOGIN);
            currentContext.setResponseBody(json);
        }
        return null; //当前zuul版本中,这个返回值没有使用,不起任何作用
    }
}
