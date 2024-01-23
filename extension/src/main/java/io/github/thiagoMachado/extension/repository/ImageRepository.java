package io.github.thiagoMachado.extension.repository;

import static org.springframework.data.jpa.domain.Specification.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import io.github.thiagoMachado.extension.model.Image;
import io.github.thiagoMachado.extension.model.unums.ImageExtension;
import io.github.thiagoMachado.extension.repository.specs.GenericSpecs;
import io.github.thiagoMachado.extension.repository.specs.ImageSpecs;

@Repository
public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

    Image getById(String id);

    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {
        Specification<Image> spec = where(GenericSpecs.conjunction());

        if (extension != null) {
            spec = spec.and(ImageSpecs.extensionEqual(extension));
        }

        if (StringUtils.hasText(query)) {
            spec = spec.and(anyOf(ImageSpecs.nameLike(query), ImageSpecs.tagsLike(query)));
        }

        return findAll(spec);
    }

    void deleteById(Optional<Image> obj);

}
