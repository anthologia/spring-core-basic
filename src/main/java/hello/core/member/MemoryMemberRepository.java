package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository {

    // 실무에서는 동시성 이슈를 해결하기 위한 concurrentHashMap를 사용한다
    // 그러나 우리는 공부용이기에 사용하지 않음.
    // 이에 대해 찾아보고 정리하기
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
