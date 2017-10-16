package dbservice;

import model.AddressDataSet;
import model.DataSet;
import model.PhoneDataSet;
import model.UserDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Function;

public class HibernateDBService implements DBService {
    private final SessionFactory sessionFactory;

    public HibernateDBService() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", "project");
        configuration.setProperty("hibernate.connection.password", "project");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    public void save(DataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            session.save(dataSet);
        }
    }

    @Override
    public <T extends DataSet> DataSet read(long id, Class clazz) {
        try (Session session = sessionFactory.openSession()) {
            return (T) session.load(clazz, id);
        }
    }

    public DataSet readByName(String name, Class clazz) {
        return runInSession(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserDataSet> criteria = builder.createQuery(clazz);
            Root<UserDataSet> from = criteria.from(UserDataSet.class);
            criteria.where(builder.equal(from.get("name"), name));
            Query<UserDataSet> query = session.createQuery(criteria);
            return query.uniqueResult();
        });
    }

    public List<? extends DataSet> readAll(Class clazz) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<DataSet> criteria = builder.createQuery(clazz);
            criteria.from(UserDataSet.class);
            return session.createQuery(criteria).list();
        }
    }

    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            session.flush();
            transaction.commit();
            return result;
        }
    }
}

