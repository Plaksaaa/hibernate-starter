package com.plaxa.listener;

import com.plaxa.entity.Revision;
import org.hibernate.envers.RevisionListener;

public class PlaxaRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
//        SecurityContext.getUser().getId()
        ((Revision) revisionEntity).setUsername("plaxa");
    }
}
