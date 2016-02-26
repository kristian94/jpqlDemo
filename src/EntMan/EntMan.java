/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntMan;

import Entity.QueryHelper;
import Entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Kristian Nielsen
 */
public class EntMan {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpqlDemoPU");
        EntityManager em = emf.createEntityManager();
        QueryHelper qh = new QueryHelper();
        
        TypedQuery<Student> q = em.createNamedQuery("Student.findAll", Student.class);
        List<Student> students = q.getResultList();
        for(Student s: students){
            System.out.println(s.getFirstname());
        }
        
        
        Student s = (Student) qh.findBy("Firstname", "jan", em);
        System.out.println(s.getFirstname() + " " + s.getLastname());
        
        
        Student s2 = (Student) (Student) qh.findBy("Lastname", "Olsen", em);
        System.out.println(s2.getFirstname() + " " + s2.getLastname());
        
        System.out.println("totals for " + s2.getLastname() + ": " +qh.getTotalById(s2, em));
        System.out.println("totals for all students: " +qh.getAllTotals(em));
        System.out.println(qh.getHighestTotal(em));
        System.out.println(qh.getLowestTotal(em));
        //List<Integer> l = qh.getHighestTotal(em);
        //System.out.println(l.get(0));
        //System.out.println(l.get(1));
        
        List<Student> bestStuds = qh.getHighestScoringStudents(em);
        List<Student> worstStuds = qh.getLowestScoringStudents(em);
        System.out.println("best student = "+bestStuds.get(0).getFirstname());
        System.out.println("worst student = "+worstStuds.get(0).getFirstname());
        
    }
}
