package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 디폴트 생성자의 접근지시자가 PROTECTED가 됨!
@ToString(of = {"id","username","age"}) // toString에서 연관관계 설정이 되어 있는 건 넣지 않는 게 좋음!(이곳에선 team이 연관관계!)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id") // entity의 ID를 db Table에서는 member_id로써 매핑하게 됨
    private Long Id;
    private String Username;
    private int age;

    //member(n) <-> team(1)
    @ManyToOne(fetch = FetchType.LAZY) // JPA에서 모든 연관관계는 기본적으로 LAZY 로 세팅해줘야 함!
    // 지연 로딩 : member 조회 시 member만 딱 조회함, 이 값을 볼 때, 이 team의 쿼리값을 가져옴 -> 대충 이게 지연 로딩
    @JoinColumn(name = "team_id") // "team_id"가 FK가 됨
    private Team team;


//    protected Member  () {
//    } // entity는 기본적으로 생성자가 하나 있어야 함.(private는 안됨.)

    // 생성자 만들기 단축키 : alt + insert
    public Member(String username) {
        Username = username;
    }

    public Member(String username, int age, Team team) {
        this.Username = username;
        this.age = age;
        if(team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) { // 특정 멤버가 팀을 변경할 수도 있음 (연관관계 세팅 필요)
        this.team = team;
        team.getMembers().add(this);
    }
}