package com.main.model.jpa;

import com.main.model.entity.ArrivalEntity;
import com.main.model.entity.InventoryBaseEntity;

import java.util.List;
import java.util.Optional;

public class JpaArrivalDao extends JpaDataAccessObject<ArrivalEntity> {
    @Override
    public Optional<ArrivalEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(ArrivalEntity.class, id));
    }

    @Override
    public List<ArrivalEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM ArrivalEntity e").getResultList();
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
