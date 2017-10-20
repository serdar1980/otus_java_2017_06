package ru.otus.web.servlet;

import cacheengine.CacheEngine;
import cacheengine.CacheEngineImpl;
import dbservice.CacheableDBService;
import dbservice.HibernateDBService;
import model.AddressDataSet;
import model.DataSet;
import model.PhoneDataSet;
import model.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private static final int SIZE = 5;

    private static final CacheEngine<Long, DataSet> cacheEngine =
            new CacheEngineImpl<>(SIZE, 0, 0, false);

    private static final CacheableDBService dbServiceForCache =
            new CacheableDBService(new HibernateDBService(), cacheEngine);
    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";


    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request, DataSet user) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());
        pageVariables.put("cacheHit", dbServiceForCache.getHitCount());
        pageVariables.put("cacheMiss", dbServiceForCache.getMissCount());
        pageVariables.put("userInfo", (user != null) ? user.toString() : null);
        //let's get login from session
        String login = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER_NAME);
        pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);

        return pageVariables;
    }

    private static void someWorkDb() {
        List<PhoneDataSet> testerPhones = new ArrayList<>();
        testerPhones.add(new PhoneDataSet("89080980"));
        testerPhones.add(new PhoneDataSet("84324234"));
        testerPhones.add(new PhoneDataSet("875756756"));

        UserDataSet testerUser = new UserDataSet("tester", 90);
        AddressDataSet address = new AddressDataSet("Москва Петровско-разумовская 24 кв 35");
        testerUser.setAddress(address);
        testerUser.setPhones(testerPhones);

        for (Long i = 2L; i < 10; i++) {
            testerUser = new UserDataSet("tester" + i, 90 + i.intValue());
            address = new AddressDataSet("Москва Петровско-разумовская 24 кв 35");
            testerUser.setAddress(address);
            testerUser.setPhones(testerPhones);

            dbServiceForCache.save(testerUser);
        }
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        someWorkDb();
        Map<String, Object> pageVariables = createPageVariablesMap(request, null);

        response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String paramUserId = request.getParameter("userId");
        Long searchId = Long.valueOf(paramUserId);
        DataSet user1 = dbServiceForCache.read(searchId, UserDataSet.class);
        Map<String, Object> pageVariables = createPageVariablesMap(request, user1);

        response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}