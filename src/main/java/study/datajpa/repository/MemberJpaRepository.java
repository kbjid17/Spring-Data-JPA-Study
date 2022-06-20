package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {

    @PersistenceContext // 스프링 컨테이너가 JPA가 가지고 있는 Entity Manager를 가져다줌 -> 얘를 이용해서 JPA 매핑을 진행
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member); // persist : insert 쿼리를 이용해 해당 member를 저장
        return member;
    }



    public void delete(Member member) {
        em.remove(member); // delete 쿼리가 나오면서 삭제됨.
    }



    // 단건 조회
    public Member find(Long id) {
        return em.find(Member.class, id); // find : selece 쿼리를 이용해 해당 Id값을 가지고 있는 멤버를 가져옴
    }

    public long count() {
        return em.createQuery("select count(m) from Member m",Long.class)
                .getSingleResult(); // getSingleResult() : 결과를 하나만 반환
    }

    // 전체 조회
    public List<Member> findAll() {
        //JPQL을 사용해야 함!(JPQL은 일반 SQL과 약간의 문법 차이가 있음)
//        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
//        return result;

        return em.createQuery("select m from Member m", Member.class).getResultList();
        //qlString: "(이 안의 쿼리문이 SQL로 번역되어 실행됨)", (쿼리문에서 반환할 타입).class
        //getResultList() : 마지막으로 앞의 엔티티에서 쿼리문을 실행시킨 결괏값을 반환

    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member); // member이 null일 수도 있고 아닐 수도 있음(null이든 아니든 반환!)
    }
}


//(from "순수 JPA 기반 리포지토리 만들기")
/*
JPA는 UPDATE 기능을 만들 때 "변경 감지" 라는 기능으로 만듦.(Update라는 메서드가 필요가 없음!!!)
em으로 조회 후, entity 수정하고 commit 하면 자동으로 변경된 걸 인지해서 쿼리를 날려줌!
 */