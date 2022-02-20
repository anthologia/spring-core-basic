package hello.core.member;

// 구현체가 하나인 경우에는 보통 끝에 Impl 을 붙인다
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long meberId) {
        return memberRepository.findById(meberId);
    }
}
