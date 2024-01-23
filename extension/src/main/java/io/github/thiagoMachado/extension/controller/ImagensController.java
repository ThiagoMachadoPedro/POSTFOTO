package io.github.thiagoMachado.extension.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.thiagoMachado.extension.model.Image;
import io.github.thiagoMachado.extension.model.DTOs.ImageDTO;
import io.github.thiagoMachado.extension.model.DTOs.ImageMapper;
import io.github.thiagoMachado.extension.model.unums.ImageExtension;
import io.github.thiagoMachado.extension.repository.ImageRepository;
import io.github.thiagoMachado.extension.service.ImageServices;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/imagem")
// traz mensageria com o log.info("")
@Slf4j
public class ImagensController {

  @Autowired
  private ImageServices service;

  @Autowired
  private ImageRepository repository;

  @PostMapping
  public ResponseEntity<?> save(
      @RequestParam("file") MultipartFile file,
      @RequestParam("name") String name,
      @RequestParam("tags") List<String> tags) throws IOException {

    log.info("Imagem recebida: name: {}, size: {}", file.getOriginalFilename(), file.getSize());

    ImageMapper mapper = new ImageMapper();

    Image image = mapper.mapToImage(file, name, tags);
    Image savedImage = service.save(image);
    URI imageUri = buildImageURL(savedImage);

    return ResponseEntity.created(imageUri).build();
  }

  @GetMapping("{id}")
  public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
    var possibleImage = service.imageId(id);
    if (possibleImage.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var image = possibleImage.get();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(image.getExtension().getMediaType());
    headers.setContentLength(image.getSize());
    // inline; filename="image.PNG"
    headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() + "\"", image.getFileName());

    return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
  }

  private URI buildImageURL(Image image) {
    String imagePath = "/" + image.getId();
    return ServletUriComponentsBuilder
        .fromCurrentRequestUri()
        .path(imagePath)
        .build().toUri();
  }

  @GetMapping
  public ResponseEntity<List<ImageDTO>> search(
      @RequestParam(value = "extension", required = false, defaultValue = "") String extension,
      @RequestParam(value = "query", required = false) String query) throws InterruptedException {

    Thread.sleep(3000L);

    var result = service.search(ImageExtension.ofName(extension), query);

    var images = result.stream().map(x -> {
      var url = buildImageURL(x);
      return new ImageMapper().imageToDTO(x, url.toString());
    }).collect(Collectors.toList());

    return ResponseEntity.ok(images);

  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<?> delete(@PathVariable String id

  ) throws InterruptedException {

    service.delete(id);
    return ResponseEntity.ok().build();
  }

}
