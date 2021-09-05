package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    /*
        protected OrderItem() {
            // 외부에서 new 키워드를 이용하여 객체를 함부로 생성하는 것을 막아줌
            // 외부에서 OrderItem orderItem = new OrderItem(); 햐여
            // setter()를 이용한 객체 생성하는 것을 막아줌
            // JPA는 protected까지 허용하는데 protected는 쓰지 말라는 의미를 암시함
            // 이렇게 생성자를 만드는 것 대신
            // class에 @NoArgsConstructor(access = AccessLevel.PROTECTED) 해주기
        }
    */

    //== 생성 메서드 ==/
    // orderPrice는 할인 등에 의해 값이 바뀔 수 있기 때문에 파라메터로 적어준 것
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 주문 수량만큼 아이템의 재고 수량 감소시켜주기
        return orderItem;
    }

    //== 비즈니스 로직 ==//

    /**
     * 주문 취소한 경우 : 해당 아이템을 주문한 개수(주문 수량)만큼 다시 아이템의 재고 수량을 늘려줘야 한다
     */
    public void cancel() {
        getItem().addStock(count);
    }

    //== 조회 로직 ==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

}
