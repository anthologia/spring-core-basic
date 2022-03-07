# Spring Core (Basic)
[[스프링 핵심 원리 - 기본편](https://www.inflearn.com/course/스프링-핵심-원리-기본편/)]

---
## 1. 객체 지향 설계와 스프링
### 1) 이야기 - 자바 진영의 추운 겨울과 스프링의 탄생
- 옛날 옛적에 EJB 라는 지옥이 있었다. 이를 탈출하기 위해 Rod Johnson이라는 사람이 [J2EE Design and Development](http://index-of.es/Java/Wrox%20Press%20-%20Expert%20one-on-one%20J2EE%20Design%20and%20Development.pdf) 라는 책을 썼다.
  이 책의 예제코드가 지금의 Spring Framework가 되었다. EJB라는 Winter가 가고 Spring이 오게 된 것이다.
### 2) 스프링이란?
- 좋은 객체 지향 애플리케이션을 개발할 수 있게 도와주는 프레임워크
- 스프링 부트는 스프링 생태계를 편리하게 사용할 수 있도록 지원한다
### 3) 좋은 객체 지향 프로그래밍이란?
- 객체들의 모임, 객체들의 협력 등이 있지만 가장 중요한 것은 유연하고 변경이 용이하다는 것이다. 이를 위해서 역할과 구현을 분리하여 객체를 설계한다.
- 객체는 언제나 협력 관계를 가진다. 다형성은 클라이언트를 변경하지 않고, 서버의 구현 기능을 유연하게 변경해도 두 객체의 협력에 문제가 없도록 만든다.
- 인터페이스가 바뀌면 객체 간 협력에 문제가 생긴다. 그러므로 인터페이스를 안정적으로 바뀌지 않게 잘 설계하는 것이 중요하다.
### 4) 좋은 객체 지향 설계의 5가지 원칙 (SOLID)
- **SRP : 단일 책임 원칙 (Single Responsibility Principle)**
    - 문맥과 상황에 따라 다르지만, 한 클래스는 하나의 책임만 가져야 한다
    - 코드 변경이 있을 때, 파급 효과가 적다면 SRP를 잘 따른 것이다

- **OCP : 개방 폐쇄 원칙 (Open/Closed Principle) ⭐️**
    - 다형성을 활용하여 확장에는 열려 있어야 하지만, 변경에는 닫혀 있어야 한다
    - 역할과 구현의 분리를 생각해보자

- **LSP : 리스코프 치환 원칙 (Liskov Substitution Principle)**
    - 다형성에서 하위 클래스는 인터페이스 규약을 다 지켜야 한다는 것이다
    - 즉, 본래 기능의 보장을 말한다

- **ISP : 인터페이스 분리 원칙 (Interface Segregation Principle)**
    - 여러 개의 역할로 분리된 인터페이스가 하나의 범용 인터페이스보다 낫다. 그 이유는 대체 가능성이 높아지기 때문이다.

- **DIP : 의존관계 역전 원칙 (Dependency Inversion Principle) ⭐**️
    - 추상화(역할)에 의존해야지, 구체화(구현)에 의존하면 안된다
    - 클라이언트가 인터페이스에 의존해야 유연하게 구현체를 변경할 수 있다


- ❓ : 그러나 앞선 MemberService 클라이언트는 구현 클래스에 의존적이었다. 다형성을 사용했지만, OCP와 DIP를 지킬 순 없었다.  
  `MemberRepository m = new MemoryMemberRepository(); //기존 코드`  
  `MemberRepository m = new JdbcMemberRepository(); //변경 코드`  
  그렇기에 객체의 생성과 연관관계를 맺어줄 별도의 조립 / 설정자가 필요하다.

## 2. 스프링 핵심 원리 이해1 - 예제 만들기
### 1) 프로젝트 생성
 - 스프링을 사용하지 않고 순수한 자바로만 구현하는 프로젝트
### 2) 비즈니스 요구사항과 설계
 - 미확정된 부분이 있어도, 역할과 구분을 구분하여 개발하면 추후 구현체를 갈아끼울 수 있다
### 3) 회원 도메인 설계
 - 도메인 협력관계 : 기획자와의 커뮤니케이션에 사용
 - 클래스 다이어그램 : 도메인 협력관계를 구체화하여 개발자가 구현사항을 적은 것
 - 객체 다이어그램 : 구현체는 동적으로 결정되기 때문에 서버 런타임시 어떻게 돌아가는지 한 눈에 보기 위함
### 4) 회원 도메인 개발
 - 회원 Entity, Repository, Service에 대한 인터페이스와 구현체 작성
 - HaspMap을 사용하여 MemoryMemberRepository를 구현했다. 그러나 HashMap 클래스를 살펴보면, synchronized 키워드가 존재하지 않아 Multi-Thread 환경에서 사용할 수 없다.
   동시에 HaspMap에 접근하면, 값이 덮어씌어지는 동시성 문제가 발생하기 때문이다. 그래서 실무에서는 ConcurrentHashMap을 사용한다.
   - ❓ ConcurrentHashMap 이란 : ConcurrentHashMap은 get() 메소드에는 synchronized가 존재하지 않고, put()에만 존재한다.
                         이는 여러 쓰레드가 동시에 읽는 것은 가능하지만, 동시에 쓰는 것은 불가능하다는 뜻이다.
   - ❓ 그렇다면, 어떤 방식으로 Thread-safe하게 만드는걸까? : ConcurrentHashMap은 버킷 단위로 Lock을 사용한다. 살펴보면, 아래와 같은 코드가 있다.  
                                                     `private static final int DEFAULT_CAPACITY = 16;`  
                                                     `private static final int DEFAULT_CONCURRENCY_LEVEL = 16;`  
                                                     그렇기 때문에 버킷의 수와 동시 작업 가능한 쓰레드의 수가 같은 것이다.
                                                     그래서 여러 쓰레드에서 ConcurrentHashMap에 접근하더라도, 다른 버킷을 작업하는 것이라면 경쟁이 일어나지 않는다.  
                                                     - 참고 : [https://devlog-wjdrbs96.tistory.com/269](https://devlog-wjdrbs96.tistory.com/269)
### 5) 회원 도메인 실행과 테스트
 - Java 코드로 하는 테스트와 junit을 통한 단위 테스트의 차이 : 일일이 콘솔을 눈으로 확인하는 것보다는 코드를 변경할 때마다 자동적이고, 가시적으로 확인할 수 있는 junit 단위 테스트가 낫다고 느껴진다
 - ❗️코드는 여전히 OCP, DIP 위반 중 : MemberServiceImpl은 추상화와 구체화 둘에 모두 의존하고 있기 때문이다   
 `private final MemberRepository memberRepository = new MemoryMemberRepository();`
### 6) 주문과 할인 도메인 설계
 - 회원 저장소 구현체와 할인 정책 구현체가 바뀌더라도, 기존 역할들의 협력관계를 그대로 재사용할 수 있도록 역할과 구현을 분리하여 설계
### 7) 주문과 할인 도메인 개발
 - OrderService, DiscountPolicy 인터페이스와 구현체 작성 
 - OrderServiceImpl가 할인 정책에 의존하지 않기 때문에 단일 책임 원칙(SRP)이 잘 지켜진 것
### 8) 주문과 할인 도메인 테스트

## 3. 스프링 핵심 원리 이해2 - 객체 지향 원리 적용
### 1) 새로운 할인 정책 개발
 - @DisplayName을 사용한 테스트
### 2) 새로운 할인 정책 적용과 문제점
 - OrderServiceImpl 클라이언트는 DiscountPolicy의 추상과 구현체 모두 의존하고 있다. 이는 DIP와 OCP를 모두 위반한다. 
   이를 해결하기 위해 해당 클라이언트의 구현체 의존을 삭제하였다. 그러나 클라이언트와 구현체를 이어주는 코드가 없는데 접근을 하기 때문에 NPE가 발생한다. 
   이 문제를 해결하기 위해서는 누군가가 클라이언트에 구현체를 생성하고 주입해주어야 한다.
   - ❓ : 앞서 사용했던 `@AutoWired`를 통해 생성자 DI를 사용하면 되지 않을까? 
### 3) 관심사의 분리
 - AppConfig라는 애플리케이션의 전체 동작 프로세스 구성을 책임지는 클래스 생성
   - 이를 통해 구현체를 선택 및 생성하고, 클라이언트와 연결시켜 생성자 DI가 가능하게 만들 수 있다
 - 생성자 DI를 통해 클라이언트의 구현체 의존을 제거함으로써 OCP, DIP 위반 문제를 해결
### 4) AppConfig 리팩터링
 - 앞에서 구현한 AppConfig의 중복 제거와 역할에 따른 구현 분리
### 5) 새로운 구조와 할인 정책 적용
 - AppConfig로 인해 사용영역과 구성영역의 분리가 가능해짐
### 6) 전체 흐름 정리
### 7) 좋은 객체 지향 설계의 5가지 원칙의 적용
 - SRP : OrderServiceImpl 클라이언트에서 구현체 의존를 제거하여 SRP 적용
 - DIP : AppConfig에서 OrderServiceImpl로 구현체를 DI함으로써 DIP 적용
 - OCP : DiscountPolicy 구현체가 바뀌어도 OrderServiceImpl 코드 수정이 필요하지 않으므로 OCP 적용
### 8) IoC, DI, 그리고 컨테이너
 - **제어의 역전 IoC(Inversion of Control)**  
   : 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것
   - 프로그램에 대한 제어 흐름을 OrderServiceImpl 클라이언트가 아닌, AppConfig가 가진다
 - **의존관계 주입 DI(Dependency Injection)**  
   : 애플리케이션 런타임때, 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서 클라이언트와 서버의 실제 의존관계가 연결 되는 것
   - ❗[TIP 1] : 의존관계는 정적인 클래스 의존관계와, 실행 시점에서 결정되는 동적인 인스턴스 의존관계를 분리해서 생각해야 한다
     - 정적인 클래스 의존관계 : 클래스 다이어그램. 클래스가 사용하는 import code만 보고도 판단 가능
     - 동적인 인스턴스 의존관계 : 애플리케이션 실행 시점에 실제 생성된 객체 인스턴스의 참조가 연결된 의존관계
 - **IoC 컨테이너 / DI 컨테이너**  
   : AppConfig와 같이 객체를 생성하고 관리하면서 의존관계를 연결해주는 것
### 9) 스프링으로 전환하기
 - `@Configuration`을 사용하여 AppConfig를 스프링 컨테이너의 설정 정보로 등록
 - `@Bean`을 사용하여 각 메서드를 스프링 컨테이너에 스프링 Bean으로 등록
 - 스프링 컨테이너란, ApplicationContext 를 말한다
 - 스프링 Bean은 `applicationContext.getBean()` 메서드를 통해 찾을 수 있다

## 4. 스프링 컨테이너와 스프링 빈
### 1) 스프링 컨테이너 생성
 - 스프링 컨테이너인 ApplicationContext는 인터페이스로, 여러 구현체를 담을 수 있다. 여기서는 자바 설정 클래스를 기반으로 `AnnotationConfigApplicationContext` 구현체를 사용하였다.
 - 스프링 컨테이너를 생성할 때는 구성 정보(Ex, AppConfig) 지정이 필요하다
 - ❗[TIP 2] : 스프링 Bean은 항상 다른 이름을 부여해야 한다
### 2) 컨테이너에 등록된 모든 빈 조회
 - `ac.getBeanDefinitionNames()`을 통한 모든 Bean의 이름 조회
 - `ac.getBean()`을 사용하여 빈 이름으로 Bean 인스턴스 조회
   - `getBean(Name, Class)`로 호출하는 경우에는 정확하게 Class에 맞는 Bean 인스턴스가 반환이 되지만,
     `getBean(Name)`은 Name 이름을 가진 Bean 인스턴스를 반환하므로 Object에 담는 것이 좋다.
 - `getRole()`을 사용한 Bean 구분
   - ROLE_APPLICATION : 직접 등록한 애플리케이션 Bean 
   - Role ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 Bean
### 3) 스프링 빈 조회 - 기본
 - `ac.getBean(MemberService.class)`와 같이 타입만으로 Bean 조회
 - 실패하는 테스트 역시 필요하다. 이는 `assertThrows()`를 주로 사용한다.
### 4) 스프링 빈 조회 - 동일한 타입이 둘 이상
 - `ac.getBeansOfType(Class)`를 사용하면 해당 타입의 모든 Bean을 조회할 수 있다
### 5) 스프링 빈 조회 - 상속 관계
 - 부모 타입으로 조회하면, 자식 타입도 함께 조회한다
### 6) BeanFactory와 ApplicationContext
 - BeanFactory는 스프링 컨테이너의 최상의 인터페이스다
 - ApplicationContext는 BeanFactory의 모든 기능을 상속 받아서 제공한다.
   그 외에 MessageSource, EnviromentCapable, ApplicationPulisher, ResourceLoader의 기능도 상속 받아서 제공한다.
### 7) 다양한 성정 형식 지원 - 자바 코드, XML
 - XML을 통한 스프링 컨테이너 설정
   - `GenericXmlApplicationContext`를 사용해 resources/appConfig.xml 를 넘겨주면 된다 
### 8) 스프링 빈 설정 메타 정보 - BeanDefinition
 - 설정 형식을 유연하게 지원 가능한 이유는 BeanDeifinition이 추상이기 때문이다. 스프링은 XML 혹은 자바 코드를 읽어서 BeanDefinition을 만든다.  
   (이렇게 만들어진 BeanDeifinition은 factoryBean을 통해 만들었다고 한다.)  
 - 그래서 BeanDefinition을 Bean 설정 메타정보라고 한다. 스프링 컨테이너는 이 Bean 설정 메타정보를 기반으로 스프링 Bean을 생성한다.
 - factoryBean을 통해 만드는 것 외에도 직접 BeanDefinition을 정의하여 사용하는 것도 가능하지만, 이는 스프링 내부 코드나 스프링 관련 오픈소스에서나 사용된다.

## 5. 싱글톤 컨테이너
### 1) 웹 애플리케이션과 싱글톤
 - 스프링 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때마다 새로운 객체를 생성한다
 - 만약 TPS가 높다면, 수많은 객체가 생성되어야 하기 때문에 메모리 낭비가 심하다
### 2) 싱글톤 패턴
 - 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴
 - ❗[TIP 3] 잘 설계한 객체는 잘못 프로그래밍 했을 때, 컴파일 오류가 나도록 하는 것
 - ❓[싱글톤 패턴에 대해 더 알아보았다]()
 - ❓[싱글톤 패턴은 DIP를 위반한다?](https://www.inflearn.com/questions/458136)
### 3) 싱글톤 컨테이너
 - 스프링 컨테이너는 싱글톤 패턴을 직접 적용하지 않아도, 객체 인스턴스를 싱글톤으로 관리한다
   - 이런 기능을 싱글톤 레지스트리라고 부른다
### 4) 싱글톤 방식의 주의점 ⭐️
 - 싱글톤 방식은 인스턴스 하나를 여러 클라이언트가 공유하기 때문에 **stateless**(무상태)로 설계해야 한다
   - 클라이언트에 의존적인 필드 제거
   - 클라이언트가 값을 변경할 수 있는 필드 제거
   - 가급적 읽기만 허용
   - 필드 대신 공유되지 않는 지역변수, 파라미터, ThreadLocal 사용
### 5) @Configuration과 싱글톤
 - 스프링은 @Configuration에서 만들어진 스프링 Bean에게 어떻게든 싱글톤을 보장한다
### 6) @Configuration과 바이트코드 조작의 마법
 - @Configuration가 스프링 Bean에게 싱글톤을 보장하는 방법은 바이트코드 조작 라이브러리인 CGLIB를 사용하기 때문이다. CGLIB를 통해 AppConfig를 조작하여 새로운 AppConfig를 만들어 싱글톤을 보장한다.
 - 그래서 @Configuration없이 등록된 스프링 Bean은 추가적인 코드 없이는 싱글톤 보장이 되지 않는다

## 6. 컴포넌트 스캔
### 1) 컴포넌트 스캔과 의존관계 자동 주입 시작하기
 - `@ComponentScan`과 `@Component`를 사용한 컴포넌트 스캔
   - `excludeFilters`를 사용하여 특정 클래스를 컴포넌트 스캔에서 배제
 - `@Autowired`를 사용한 의존관계 주입
   - 스프링 컨테이너가 자동으로 타입이 같은 스프링 빈을 찾아서 주입한다
### 2) 탐색 위치와 기본 스캔 대상
 - `basePackages`를 통한 컴포넌트 스캔의 탐색 위치를 지정
   - ❗[TIP 4] `basePackagesClasses`는 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.
      그러나 이를 사용하지 않으면, `@ComponentScan`이 붙은 설정 정보 클래스의 패키지가 탐색 시작 위치로 지정된다.
      이러한 관례를 잘 이용하면, 패키지 위치를 지정하지 않고 깔끔하게 설정 정보 클래스를 구현할 수 있다.
 - 자바 문법 상으로 애노테이션에는 상속관계가 없음에도 @Repository, @Controller 등이 @Component를 가질 수 있는 이유는 스프링 자체 기능이기 때문이다
### 3) 필터
 - `includeFilters`, `excludeFilters`를 활용한 테스트 작성
### 4) 중복 등록과 충돌
 - 자동 빈 등록과 수동 빈 등록이 중복으로 이뤄질 경우에는 스프링 부트에서 오류를 발생시킨다
   - 그러나 overriding 옵션을 주면 수동 빈이 자동 빈을 오버라이딩하여 실행된다
 - ❗[TIP 5] 개발은 협업이기 때문에 명확하지 않은 코드는 나쁜 코드다

## 7. 의존관계 자동 주입
### 1) 다양한 의존관계 주입 방법
 - 생성자 주입
   - 생성자 호출시점에 딱 한 번만 호출되기 때문에 **불변**, **필수** 의존관계에 사용된다
   - 생성자가 하나만 있으면, `@Autowired`를 생략할 수 있다
   - ❗[TIP 6] 좋은 아키텍트는 제약, 한계가 있는 것이다
 - 수정자(setter) 주입
   - **선택**, **변경가능성**이 있는 의존관계에 사용된다
   - ❓[자바빈 프로퍼티에 대해 더 알아보았다]()
 - 필드 주입
   - 외부에서 변경이 불가능하여 테스트 하기가 힘들다는 단점이 있다. 그래서 DI 프레임워크가 없으면 아무것도 할 수 없다. 그러므로 사용을 지양하자.
   - 그러나 테스트 코드 혹은 @Configuration 같은 애플리케이션 코드가 아닌 곳에서는 사용할 수 있다
 - 일반 메서드 주입
   - 한 번에 여러 필드를 주입받을 수 있지만, 잘 사용되지 않는다. 생성자나 수정자 주입으로 대체할 수 있기 때문이다.
### 2) 옵션 처리
 - 주입할 스프링 빈이 없는 경우의 `@Autowired` 사용법
   - `@Autowired(required=false)`를 활용한 메소드 동적 호출
   - 파라메터에 `@Nullable`를 활용하여 null 입력
   - 파라메터에 `Optional<>`를 활용하여 Optional.empty 입력
### 3) 생성자 주입을 선택해라!
 - **불변**
   - 의존관계 주입은 애플리케이션 종료까지 불변해야 한다. 그래서 public인 수정자 주입은 좋은 설계가 아니다.
     필드 주입은 외부 변경이 불가하여 애플리케이션이 딱딱해지기 때문에 역시 좋은 설계가 아니다. 
 - 생성자 주입 사용시, 필드에 `final` 키워드를 붙여주면 생성자에서 값이 설정되지 않는 경우 컴파일 오류를 내준다
 - ❗[TIP 7] 컴파일 오류가 가장 좋은 오류다. 그러므로, 컴파일 오류가 나도록 설계하는 것이 좋다
### 4) 롬복과 최신 트랜드
 - Lombok 라이브러리 적용
 - `@RequiredArgsConstructor`를 사용한 final 필드 생성자 자동 생성
### 5) 조회 빈이 2개 이상 - 문제
 - `@Autowired`는 스프링 빈을 타입으로 조회한다. 그래서 등록된 같은 타입의 빈이 2개 이상이면, NoUniqueBeanDefinitionException이 발생한다. 
    하위 구현체를 지정하는 방법은 DIP를 위반하기 때문에 다른 방법을 찾아야 한다
### 6) @Autowired 필드 명, @Qualifier, @Primary
 - @Autowired 필드명 매칭
   - 타입 매칭의 결과가 2개 이상일 때, 필드 명, 파라미터 명으로 이름 매칭
 - @Qualifier 사용
   - @Qualifier 끼리 매칭
   - Bean 이름 매칭
 - @Primary 사용
 - ❗[TIP 8] 스프링은 자동보다는 수동이, 넓은 범위보다는 좁은 범위의 선택권이 우선 순위가 높다
### 7) 애노테이션 직접 만들기
 - `@Qualifier`를 상속받는 `@MainDiscountPolicy` 애노테이션 생성
 - 단, 애노테이션 상속은 자바의 기능은 아니고, 스프링의 기능이다
