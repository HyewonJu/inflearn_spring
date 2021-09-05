package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository; // 주문에서 memberId 값을 꺼내오기 등 위해 필요
    private final ItemRepository itemRepository; // 주문에서 itemId 값을 꺼내오기 등 위해 필요

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 1. 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 2. 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 3. 주문 상품 생성
        // OrderItem에 만들어둔 생성 메서드 이용
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 4. 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 5. 주문 저장
        // Order 클래스의 cascade 옵션 때문에, order만 persis(save)해주면 delivery, orderItem 등은 저장(persist)하지 않아도 된다
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 1. 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        // 2. 주문 취소
        order.cancel();
    }

    /**
     * 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

}
