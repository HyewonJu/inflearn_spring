package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // order 테이블에 있는 member 필드에 의해 매핑 되었다는 것을 의미, 관계에서 주인이 아님을 의미(읽기 전용)
    private List<Order> orders = new ArrayList<>();
}
