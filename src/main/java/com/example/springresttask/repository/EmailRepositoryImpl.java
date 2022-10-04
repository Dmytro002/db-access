package com.example.springresttask.repository;

import com.example.springresttask.domain.Email;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmailRepositoryImpl implements EmailRepository {

    private final EntityManager entityManager;

    @Override
    public List<Email> findAllByPendingEmail() {
        TypedQuery<Email> q = entityManager.createQuery("""
                        SELECT e
                        FROM email e
                        WHERE e.isSent = false
                        """,
                Email.class);
        return q.getResultList();
    }

    @Override
    public void deletePendingEmail(Long id) {
        Email email = entityManager.find(Email.class, id);

        if (email.isSent()) {
            throw new RuntimeException("you cannot delete this email");
        }
        entityManager.remove(email);
    }

    @Override
    public Email save(Email email) {
        entityManager.persist(email);
        return email;
    }

    public Email findById(Long id) {
        return entityManager.find(Email.class, id);
    }
}
