/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Kristian Nielsen
 */
public class QueryHelper {

    public Object findBy(String dataType, String name, EntityManager em) {
        Query q2 = em.createNamedQuery("Student.findBy" + dataType);
        q2.setParameter(dataType.toLowerCase(), name);
        return q2.getSingleResult();
    }

    public Long getTotalById(Student s, EntityManager em) {
        Query q2 = em.createQuery("SELECT sum(s.score) FROM Studypoint s WHERE s.studentId = :id");
        q2.setParameter("id", s);
        return (Long) q2.getSingleResult();
    }

    public Long getAllTotals(EntityManager em) {
        Query q2 = em.createQuery("SELECT sum(s.score) FROM Studypoint s");
        return (Long) q2.getSingleResult();
    }

    public int getHighestTotal(EntityManager em) {
        Query q2 = em.createQuery("select sum(s.score) from Studypoint s group by s.studentId");
        List<Long> res = q2.getResultList();
        int highest = 0;
        for (Long l : res) {
            if (l.intValue() > highest) {
                highest = l.intValue();
            }
        }
        return highest;
    }

    public int getLowestTotal(EntityManager em) {
        Query q2 = em.createQuery("select sum(s.score) from Studypoint s group by s.studentId");
        List<Long> res = q2.getResultList();
        int lowest = 99999999;
        for (Long l : res) {
            if (l.intValue() < lowest) {
                lowest = l.intValue();
            }
        }
        
        return lowest;
    }

    public List<Student> getHighestScoringStudents(EntityManager em) {
        Query q = em.createNamedQuery("Student.findAll");
        List<Student> stu = q.getResultList();
        List<Student> res = new ArrayList<>();
        int highest = getHighestTotal(em);
        for(Student s: stu){
            int total = 0;
            for(Studypoint sp: s.getStudypointCollection()){
                total += sp.getScore();
            }
            if(total == highest) res.add(s);
        }
        return res;
        
    }

    public List<Student> getLowestScoringStudents(EntityManager em) {
        Query q = em.createNamedQuery("Student.findAll");
        List<Student> stu = q.getResultList();
        List<Student> res = new ArrayList<>();
        int lowest = getLowestTotal(em);
        for(Student s: stu){
            int total = 0;
            for(Studypoint sp: s.getStudypointCollection()){
                total += sp.getScore();
            }
            if(total == lowest) res.add(s);
        }
        return res;
    }

    public void createStudent(EntityManager em, Student s) {
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
    }

}
