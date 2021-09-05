package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",                      // ManyToMany 관계는 중간 테이블인 category_item을 join table로 지정
        joinColumns = @JoinColumn(name = "category_id"),    // 중간 테이블에 있는 칼럼 category_id 와 연결
        inverseJoinColumns = @JoinColumn(name = "item_id")) // 중간 테이블에 있는 칼럼 중 다대다 관계에 있는 다른 테이블로 들어갈 수 있도록 하는 칼럼과 연관된 칼럼
    private List<Item> items = new ArrayList<>();

    // 카테고리의 계층 구조 표현하기 위한 칼럼 : parent, child
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //== 연관관계 편의 메서드 ==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
