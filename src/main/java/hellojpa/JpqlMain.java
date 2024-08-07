package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /* JPQL은 엔티티 객체를 대상으로 쿼리 -> 객체 지향 SQL */
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(0) // 페이징 (몇 번째부터)
                    .setMaxResults(10) // 페이징 (몇 개)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name : " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 사용 다 하면 EntityManager 꼭 닫아주자
        }

        emf.close();
    }
}
