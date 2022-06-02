package com.example.uploaddownload.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.example.uploaddownload.exception.FileStorageException;
import com.example.uploaddownload.exception.MyFileNotFoundException;
import com.example.uploaddownload.property.FileStorageProperties;


@Service
public class FileStorageService {
	
	//atributo para assumir o valor do path
	private final Path fileStorageLocation;
	
	// construtor - com injeção de dependencia
	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		
		// bloco try / catch
		try {
			Files.createDirectories(this.fileStorageLocation);
		}catch(Exception ex) {
			throw new FileStorageException("Não foi possivel criar o diretório no local indicado para o upload", ex);
			
	}
	
}
	
	// método que acessa o arquivo - apartir de sua identificação - 
	public String storeFile(MultipartFile file) {
		
	// tratamento para normalização de path para acessar o arquivo
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
	// bloco try/catch para verificar se o arquivo não contem caracteres invalidos
		
		try {
			if(fileName.contains("...")) {
				throw new FileStorageException("O arquivo contem uma sequencia inválida"+ fileName);
			}
			// copiar o arquivo para o local indicado (caso exista um arquivo com o mesmo nome, será substituido)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
			
		}catch(IOException ex){
			throw new FileStorageException("Não foi possivel armazenar o arquivo " + fileName + ". Tente novamente !", ex);
		}	
		
	}
	//tentativa de recuperar o arquivo "uppado"
	public Resource loadFileAsResource(String fileName) {
		// bloco try/catch
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			// verificar se o recurso existe
			if(resource.exists()) {
				return resource;
			}else{
				throw new MyFileNotFoundException("Arquivo não encontrado. " + fileName);
				
		}
		}catch(MalformedURLException ex){
			throw new MyFileNotFoundException("Arquivo não encontrado. " + fileName, ex);
		}
	}
}

