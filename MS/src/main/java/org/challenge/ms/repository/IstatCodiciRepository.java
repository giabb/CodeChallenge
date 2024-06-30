package org.challenge.ms.repository;

import org.challenge.ms.model.IstatCodiciEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IstatCodiciRepository extends JpaRepository<IstatCodiciEntity, String> {
}
