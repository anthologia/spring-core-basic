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
