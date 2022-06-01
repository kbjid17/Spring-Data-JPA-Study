package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberJpaRepository {

    @PersistenceContext // 스프링 컨테이너가 JPA가 가지고 있는 Entity Manager를 가져다줌 -> 얘를 이용해서 JPA 매핑을 진행
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member); // persist : insert 쿼리를 이용해 해당 member를 저장
        return member;
    }

    public Member find(Long id) {
        return em.find(Member.class, id); // find : selece 쿼리를 이용해 해당 Id값을 가지고 있는 멤버를 가져옴
    }
}
