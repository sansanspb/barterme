package ru.iqdevelop.barterme.repositories;

import org.springframework.stereotype.Repository;
import ru.iqdevelop.barterme.entities.ChatMessageEntity;

@Repository
public class ChatRepository extends AbstractRepository<Long, ChatMessageEntity>{
}
