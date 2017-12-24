package com.rgowdah.hibernate.demo;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rgowdah.hibernate.entity.Course;
import com.rgowdah.hibernate.entity.Instructor;
import com.rgowdah.hibernate.entity.Instructor_Detail;

public class EagerLazyDemo {
	public static void main(String[] args) {
		//create session factory
		SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").
				addAnnotatedClass(Instructor.class).addAnnotatedClass(Instructor_Detail.class).
				addAnnotatedClass(Course.class).buildSessionFactory();
		//create session
		Session session=sessionFactory.getCurrentSession();
		try{			
			//start transaction
			session.beginTransaction();
			//option1 call getter method while session is open
			//get instructor from db
			int id=1;
			Instructor instructor=session.get(Instructor.class, id);
			System.out.println("Instructor: "+instructor);
			System.out.println("courses: "+instructor.getCourses());			
			//commit transaction
			session.getTransaction().commit();
			//close the session
			session.close();
			//since code is called before there wont be any exception
			System.out.println("Session is closed");
			System.out.println("LazyLoading:The session is closed and we are accessing the data");
			System.out.println("If it wasnt accessed before then exception will occur");
			System.out.println("courses: "+instructor.getCourses());
			System.out.println("Done!");
		}catch (Exception exc) {
			exc.printStackTrace();
		}finally{
			session.close();
			sessionFactory.close();
		}
	}
}
