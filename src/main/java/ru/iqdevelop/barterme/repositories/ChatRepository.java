package ru.iqdevelop.barterme.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.iqdevelop.barterme.entities.ChatMessageEntity;
import ru.iqdevelop.barterme.utils.Helpers;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ChatRepository extends AbstractRepository<Long, ChatMessageEntity> {

    private static final Logger logger = LoggerFactory.getLogger(ChatRepository.class);

    public List<ChatMessageEntity> getMessagesHistoryPart(Long senderId, Long receiverId, int stage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatMessageEntity> q = cb.createQuery(ChatMessageEntity.class);
        Root<ChatMessageEntity> c = q.from(ChatMessageEntity.class);

        q.where(cb.and(cb.equal(
                c.get("fkSenderId"), senderId),
                cb.equal(c.get("fkReceiverId"), receiverId))
        );

        TypedQuery<ChatMessageEntity> query = entityManager.createQuery(q);
        List<ChatMessageEntity> rsQuery = query.getResultList();
        rsQuery.sort((msg1, msg2) -> msg2.getSendDate().compareTo(msg1.getSendDate()));

        return rsQuery.subList(stage * 20, (stage + 1) * 20 <= rsQuery.size() ? (stage + 1) * 20 : rsQuery.size());
    }

    public boolean put(ChatMessageEntity messageEntity) {

        messageEntity.setSendDate(Helpers.getCurrentTimestamp());

        try {
            entityManager.persist(messageEntity);
            logger.info("Message from %d to %d at %s put in database.", messageEntity.getSender().getCompanyId(),
                    messageEntity.getReceiver().getCompanyId(), messageEntity.getSendDate().toString());
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public List<Long> getChats(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatMessageEntity> q = cb.createQuery(ChatMessageEntity.class);
        Root<ChatMessageEntity> c = q.from(ChatMessageEntity.class);

        q.where(cb.or(
                cb.equal(c.get("fkSenderId"), id),
                cb.equal(c.get("fkReceiverId"), id)
                )
        );

        TypedQuery<ChatMessageEntity> query = entityManager.createQuery(q);
        List<ChatMessageEntity> rsQuery = query.getResultList();

        return rsQuery.stream()
                .map(i -> {
                    if (i.getFkReceiverId().equals(id)) {
                        return i.getFkSenderId();
                    } else {
                        return i.getFkReceiverId();
                    }
                })
                .distinct()
                .collect(Collectors.toList());
    }
}
