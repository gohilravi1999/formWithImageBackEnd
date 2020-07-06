package com.formWithImage.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String name;
	
	@NotBlank
	@Size(max = 100)
	private String description;
	
	@NotBlank
	@Size(max = 20)
	private String fileName;
	
	
	@NotBlank
	@Size(max = 20)
	private String fileType;

	@Column(name = "fileByte", length = 1000)
	private byte[] fileByte;

	public Product() {
		super();
	}

	

	public Product(Long id, @NotBlank @Size(max = 20) String name, @NotBlank @Size(max = 100) String description,
			@NotBlank @Size(max = 20) String fileName, @NotBlank @Size(max = 20) String fileType, byte[] fileByte) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileByte = fileByte;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFileByte() {
		return fileByte;
	}

	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", fileName=" + fileName
				+ ", fileType=" + fileType + ", fileByte=" + Arrays.toString(fileByte) + "]";
	}
	
}
