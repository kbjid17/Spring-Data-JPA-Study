package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 모든 JPA 작업은 하나의 트랜잭션 안에서 진행됨(꼭 기억!)
@Rollback(false) // JPA test :  한번 테스트를 진행하고 rollback을 시켜버림(테이블은 만들지만 그 안에서 이뤄지는 작업은 롤백됨) => 해당 롤백 작업을 안하게(false) 만듦
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("MemberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo((member.getUsername()));
        assertThat(findMember).isEqualTo(member);
    }

    // CRUD TEST
    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1"); //ctrl + D : 한줄 복사
        Member member2 = new Member("member2");
        memberJpaRepository.save((member1));
        memberJpaRepository.save((member2));

        // 단건 조회 검증
        System.out.println("------------------------test 실행");
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

//        findMember1.setUsername("memberID!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"); // 더티 체킹(== 변경 감지) 발생
//        JPA에서의 Update : 위와 같은 명령어를 사용한 변경 감지 기능을 통해 사용하면 됨

        // 리스트 조회
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);


        // 카운트 검증
       long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(all.size());

        // 멤버 삭제(다 지우고 다시 갯수 세보기)
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        long deletedCnt = memberJpaRepository.count();
        assertThat(deletedCnt).isEqualTo(0);

    }


}


// 한 트랜잭션 안에선 영속성 컨텍스트를 보장함.
/*
    한 트랜잭션 내에서 일어난 작업들에 대한 Entity들의 일관성을 보장함.
    Member member = new Member("MemberA");
        Member savedMember = memberJpaRepository.save(member);

        위 코드의 member가 같다는 것이 영속성을 보장해준다는 의미( 해당 기능을 "1차 캐시"라고 함)
 */