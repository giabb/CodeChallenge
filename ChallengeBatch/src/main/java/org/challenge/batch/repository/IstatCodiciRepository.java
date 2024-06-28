package org.challenge.batch.repository;

import org.challenge.batch.model.IstatCodiciEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IstatCodiciRepository extends JpaRepository<IstatCodiciEntity, String> {
}
