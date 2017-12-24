package com.rgowdah.hibernate.demo;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.rgowdah.hibernate.entity.Course;
import com.rgowdah.hibernate.entity.Instructor;
import com.rgowdah.hibernate.entity.Instructor_Detail;

public class FetchJoinDemo {
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
			//get instructor from db
			//option2: Hibernate query with HQL
			int id=1;
			Query<Instructor> query=session.createQuery("select i from Instructor i "+"JOIN FETCH i.courses "+
					"where i.id=:theInstructorId"
					, Instructor.class);
			//set parameter on query
			query.setParameter("theInstructorId", id);	
			//execute query and get instructor
			Instructor instructor=query.getSingleResult();
			System.out.println("Instructor: "+instructor);			
			//commit transaction
			session.getTransaction().commit();
			//close the session
			session.close();
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
