package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member")
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long Id;
    private String Username;

    protected Member  () {
    } // entity는 기본적으로 생성자가 하나 있어야 함.

    // 생성자 만들기 단축키 : alt + insert
    public Member(String username) {
        Username = username;
    }
}