/*package PhishingUniv.Phinocchio.Login.repository;

import PhishingUniv.Phinocchio.Login.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository{

    private static Map<Long, User> store = new HashMap<>();
    //동시성 문제 해결할 때는 concurrenthashmap으로 바꾸기

    //sequence는 key값 생성해주는 것
    //실무에서는 동시성 문제 고려하여 AtomicLong으로 사용하기
    private static long sequence = 0L;


    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;

    }

    @Override
    public Optional<User> findById(Long id) {
        //Optional로 감싸서 반환하면 이후 처리에 대해 client단에서 무언갈 할 수 있음

        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByName(String name) {
        return store.values().stream().filter(user ->user.getName().equals(name))
                .findAny();


    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore()
    {
        store.clear();
    }
}
*/