package com.vaes.json.service;

import com.vaes.json.domain.model.Base64Data;
import com.vaes.json.domain.model.Status;
import com.vaes.json.domain.model.Type;
import com.vaes.json.domain.repository.JsonRepository;
import com.vaes.json.exception.ResourceNotFoundException;
import com.vaes.json.exception.UnprocessableEntityException;
import com.vaes.json.message.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiffService {

    @Autowired
    private JsonRepository jsonRepository;

    /**
     * @param uid identifier
     *
     * @return result {@link StatusMessage} status message
     */
    public StatusMessage getDiff(String uid) {
        return jsonRepository.findJsonObjectByUid(uid).map(this::processData)
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Here the data is processed.
     *
     * @param data the entity
     *
     * @return
     */
    private StatusMessage processData(Base64Data data) {
        StatusMessage message = new StatusMessage();
        return data.getLeft().map(left -> data.getRight().map(right -> {
            if (left.equals(right)) {
                return message.status(Status.EQUAL_SIZE);
            } else if (left.length() != right.length()) {
                return message.status(Status.UNEQUAL_SIZE);
            } else {
                message.size(left.length());
                message.offset(calculateOffset(left, right));
                return message;
            }
        }).orElseThrow(() -> new UnprocessableEntityException("right data is not set")))
                .orElseThrow(() -> new UnprocessableEntityException("left data is not set"));
    }

    private Integer calculateOffset(String left, String right) {
        char[] leftArray = left.toCharArray();
        char[] rightArray = right.toCharArray();
        int n = leftArray.length;
        int i = 0;
        while (n-- != 0) {
            if (leftArray[i] != rightArray[i]) {
                return i;
            }
            i++;
        }
        return 0;
    }

    /**
     * Here the new posted data is stored.
     * First, there is an attempt to find the data by uid in the database.
     * If it is found, it's type is checked.
     * If the type is the same, data is updated in db only.
     * Else, the new entity is created and stored in db.
     *
     * @param data data from controller
     * @param type type of Data, LEFT or RIGHT
     * @param uid  identifier
     */
    public void storeData(String data, Type type, String uid) {
        Optional<Base64Data> found = jsonRepository.findJsonObjectByUid(uid);
        Base64Data storable = found.map(update -> updateStorable(data, type, update)
        ).orElse(updateStorable(data, type, new Base64Data()));
        storable.setUid(uid);
        jsonRepository.save(storable);
    }

    private Base64Data updateStorable(String data, Type type, Base64Data update) {
        if (type == Type.LEFT) {
            update.setLeft(data);
        } else {
            update.setRight(data);
        }
        return update;
    }

}
