package ru.srfholding.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.srfholding.project.model.ProjectEntity;

import java.util.UUID;

/**
 * Репозиторий проектов
 */
@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {

}
