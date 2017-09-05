package ru.otus.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configurable
public class TimerServlet extends HttpServlet {

    private static final String REFRESH_VARIABLE_NAME = "refreshPeriod";
    private static final String TIME_VARIABLE_NAME = "time";
    private static final String TIMER_PAGE_TEMPLATE = "timer.html";

    private static final int PERIOD_MS = 1000;
    //Не сработало надо поискать
    @Autowired
    private TimeService timeService;

    @Autowired
    private ApplicationContext context;

    /*
    public void init() {
        //TODO: Create one context for the application. Inject beans.

        if (context == null) {
            System.out.println("context was null");
            context = new ClassPathXmlApplicationContext("SpringBeans.xml");
        }
        timeService = (TimeService) context.getBean("timeService");
    }
    */

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        if (timeService == null) {
            System.out.println("timeService was null");
            timeService = (TimeService) context.getBean("timeService");
        }

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(REFRESH_VARIABLE_NAME, String.valueOf(PERIOD_MS));
        pageVariables.put(TIME_VARIABLE_NAME, timeService.getTime());

        response.getWriter().println(TemplateProcessor.instance().getPage(TIMER_PAGE_TEMPLATE, pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }


}
