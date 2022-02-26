package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService = appConfig.memberService();

        // 2. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        // 참고값이 다른 것을 확인
        System.out.println("memberService = " + memberService);
        System.out.println("memberService2 = " + memberService2);

        // memberService != memberService2
        Assertions.assertThat(memberService).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용하여 DIP를 만족하는 객체 사용")
    void singletonDipMememoryMemberRepositoryTest() {
        AppConfig appConfig = new AppConfig();

        MemberRepository memberRepository1 = appConfig.MemberRepository();
        MemberRepository memberRepository2 = appConfig.MemberRepository();

        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);

        Assertions.assertThat(memberRepository1).isSameAs(memberRepository2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        // isSameAs() 메서드는 객체의 참조 주소값이 같은지 비교를 하고
        // isEqualTo() 메서드는 객체가 가진 값이 같은지 비교를 한다
        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
    }
}
