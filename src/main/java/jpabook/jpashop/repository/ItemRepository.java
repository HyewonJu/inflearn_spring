package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) { // 상품 저장
        if (item.getId() == null) { // 새로운 상품인 경우, 새롭게 상품 추가
            em.persist(item);
        } else {
            // merge()의 리턴형이 영속성 컨텍스트 (merge의 파라미터로 준 item은 준영속이다)
            // 따라서, 만약 더 사용할 일 있으면 merge()의 리턴형을 변수에 담아서 사용해야 함!!
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class) // 파라미터로 (JPQL, 반환타입) 주기
                .getResultList();
    }
}
