//comment added
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Client {

	private static SessionFactory factory;

	public static void getConfiguration() {

		try {
			Configuration config = new Configuration().configure();
			StandardServiceRegistryBuilder build = new StandardServiceRegistryBuilder()
					.applySettings(config.getProperties());
			factory = config.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("failed to create session factory object" + ex);
			throw new ExceptionInInitializerError();

		}

	}

	public int insertEmployee(Integer eid, String ename) throws HibernateException {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		Employee e1 = new Employee(eid, ename);
		int i = (Integer) session.save(e1);
		t.commit();
		session.close();
		return i;

	}

	public void displayEmployee() {

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		List empList = session.createQuery("From EmployeeAssign2").list();
		for (Iterator iterator = empList.iterator(); iterator.hasNext();) {
			Employee emp = (Employee) iterator.next();
			System.out.println("id::" + emp.getId());
			System.out.println("Name::" + emp.getName());
		}

		session.close();

	}

	public void updateEmployee(Integer eid, String ename) {

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Employee e1 = session.get(Employee.class, eid);
		e1.setName(ename);
		session.update(e1);
		t.commit();
		session.close();

	}

	public void deleteEmployee(Integer eid) {

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Employee e1 = session.get(Employee.class, eid);
		session.delete(e1);
		t.commit();
		session.close();

	}

	public static void main(String[] args) {

		getConfiguration();

		Client c = new Client();
		int i = c.insertEmployee(24, "Julie");
		System.out.println("Inserted record "+i);
		factory.close();

	}

}
