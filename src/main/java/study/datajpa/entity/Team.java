package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","name"})
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;


    //member(n) <-> team(1)
    @OneToMany(mappedBy = "team") // 기본편 강의 보고 오자(두 entity가 서로 매핑되어 있는 경우, mappedBy가 필요. 보통 FK가 없는 쪽에 걸어주는게 좋음)
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
