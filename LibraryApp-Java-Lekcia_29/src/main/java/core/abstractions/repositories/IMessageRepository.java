package core.abstractions.repositories;

import core.entities.Message;

import java.util.ArrayList;

public interface IMessageRepository extends IRepository<Message> {

    ArrayList<Message> getMembersById(int userid);

}
