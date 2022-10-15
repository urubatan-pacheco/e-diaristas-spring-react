package br.com.urubatanpacheco.ediaristas.core.services.storage.adapters;

import org.springframework.web.multipart.MultipartFile;

import br.com.urubatanpacheco.ediaristas.core.models.Foto;
import br.com.urubatanpacheco.ediaristas.core.services.storage.exceptions.StorageServiceException;

public interface StorageService {
    Foto salvar(MultipartFile file) throws StorageServiceException;
}
