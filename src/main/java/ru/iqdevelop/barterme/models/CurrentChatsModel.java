package ru.iqdevelop.barterme.models;

import java.util.List;

public class CurrentChatsModel {

    private Long id;
    private List<Long> collocutorIds;

    public CurrentChatsModel(Long id, List<Long> collocutorIds) {
        this.id = id;
        this.collocutorIds = collocutorIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getCollocutorIds() {
        return collocutorIds;
    }

    public void setCollocutorIds(List<Long> collocutorIds) {
        this.collocutorIds = collocutorIds;
    }
}
