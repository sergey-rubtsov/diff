package com.waes.diff.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class Base64Data {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String uid;

    @Basic
    @Lob
    private String right;

    @Basic
    @Lob
    private String left;

    /**
     * @param left  field stores left data
     * @param right field stores right data
     * @param uid   the identifier, must be unique
     */
    public Base64Data(String left, String right, String uid) {
        this.uid = uid;
        this.left = left;
        this.right = right;
    }


    public Optional<String> getLeft() {
        return Optional.ofNullable(left);
    }

    public Optional<String> getRight() {
        return Optional.ofNullable(right);
    }
}
