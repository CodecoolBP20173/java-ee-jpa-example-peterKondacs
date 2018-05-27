package com.codecool.jpaexample;

import com.codecool.jpaexample.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JPAExample {

    public static void populateDb(EntityManager em) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate1 = Calendar.getInstance().getTime();
        Date birthDate2 = Calendar.getInstance().getTime();
        try {
            birthDate1 = sdf.parse("1997-07-21");
            birthDate2 = sdf.parse("1993-12-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CCLocation ccLocation = CCLocation.BUDAPEST;
        Klass classBp2 = new Klass("Budapest 2016-2", ccLocation);
        Address address = new Address("Hungary", "1234", "Budapest", "Macskakő út 5.");
        List<String> studentPhoneNumbers = Arrays.asList("06-1-111-1111", "06-30-111-1111");
        Student student = new Student("Ödön", "odon@tokodon.hu", birthDate1, address, studentPhoneNumbers);
        classBp2.addStudent(student);

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(classBp2);
        transaction.commit();
        System.out.println("Budapest 2016-2 class saved.");

        transaction.begin();
        em.persist(address);
        em.persist(student);
        transaction.commit();
        System.out.println("Ödön saved.");

        Address address2 = new Address("Hungary", "6789", "Budapest", "Harap u. 3.");
        List<String> student2PhoneNumbers = Arrays.asList("06-1-222-2222", "06-30-222-2222");
        Student student2 = new Student("Aladár", "ktyfl@gmail.com", birthDate2, address2, student2PhoneNumbers);
        classBp2.addStudent(student2);

        transaction.begin();
        em.persist(student2);
        em.persist(address2);
        transaction.commit();
        System.out.println("Aladár saved.");
    }

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaexamplePU");
        EntityManager em = emf.createEntityManager();

        populateDb(em);

        Student foundStudent1 = em.find(Student.class, 1L);
        System.out.println("--Found student #1");
        System.out.println("----name----" + foundStudent1.getName());
        System.out.println("----address of student----" + foundStudent1.getAddress());

        Student foundStudent2 = em.find(Student.class, 2L);
        System.out.println("--Found student #2");
        System.out.println("----name----" + foundStudent2.getName());
        System.out.println("----address of student----" + foundStudent2.getAddress());

        Address foundAddress1 = em.find(Address.class, 1L);
        System.out.println("--Found address #1");
        System.out.println("----address----" + foundAddress1.getAddr());

        Address foundAddress2 = em.find(Address.class, 2L);
        System.out.println("--Found address #2");
        System.out.println("----address----" + foundAddress2.getAddr());

        em.close();
        emf.close();

    }
}
