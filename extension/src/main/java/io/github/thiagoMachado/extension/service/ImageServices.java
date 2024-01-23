package io.github.thiagoMachado.extension.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.thiagoMachado.extension.exception.UsersDuplication;
import io.github.thiagoMachado.extension.model.Image;
import io.github.thiagoMachado.extension.model.unums.ImageExtension;
import io.github.thiagoMachado.extension.repository.ImageRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImageServices {

  @Autowired
  private ImageRepository repository;

  public Image save(Image image) {

    return repository.save(image);
  }

  public Optional<Image> imageId(String id) {

    return repository.findById(id);
  }

  public List<Image> search(ImageExtension extension, String query) {
    return repository.findByExtensionAndNameOrTagsLike(extension, query);
  }

  public void delete(String id) {
    Optional<Image> obj = imageId(id);
   repository.deleteById(obj);

  }

}
