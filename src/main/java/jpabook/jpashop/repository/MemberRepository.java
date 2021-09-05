package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // 스프링 빈으로 등록
@RequiredArgsConstructor
public class MemberRepository {

    /* @RequiredArgsConstructor 쓰지 않는 경우
    @PersistenceContext // JPA가 EntityManager 주입
    private EntityManager em;
    */

    private final EntityManager em;

    /* JPA가 member를 저장 */
    public void save(Member member) {
        em.persist(member);
    }

    /* 해당 id(PK) 가진 회원 1개 조회 : id 값을 가지고 Member를 찾음 */
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    /* 회원 목록 조회 : 조회 쿼리(JPQL) 작성해줘야 한다 */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) // 파라미터로 (JPQL, 반환타입) 주기
                .getResultList();
    }

    /* 해당 name을 가진 회원 목록 조회 */
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name) // 위에 JPQL :name에 name값 바인딩 해줌
                .getResultList();
    }
}
