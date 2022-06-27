package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{
    //단일 체계 원칙을 잘 지킨 것이다.
    //할인이 고쳐질 경우 여긴 안고쳐도 되기에

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //  밑에 두줄 주석은, DIP, OCP 위반 때문에 사용하지 않는다. 다른 방법으로 접근해야 한다.
    //    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // DIP를 지킨 코드는 밑에 한줄, 하지만 오류가 난다 왜? null이니깐
    private DiscountPolicy discountPolicy;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
