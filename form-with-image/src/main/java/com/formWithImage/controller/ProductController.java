package com.formWithImage.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formWithImage.dao.ProductRepository;
import com.formWithImage.model.Product;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	
	@PostMapping("/saveProduct")
	public ResponseEntity<?> addProduct(@Valid @RequestParam("product") String product,@RequestParam("image") MultipartFile file) throws JsonParseException,JsonMappingException,IOException{
		
			Product product1 = new ObjectMapper().readValue(product, Product.class);
			System.out.println(file.getBytes().length);
			product1.setFileByte(compressBytes(file.getBytes()));
			product1.setFileName(file.getOriginalFilename());
			product1.setFileType(file.getContentType());
			productRepository.save(product1);
			return ResponseEntity.ok("product added");
	}
	
	@GetMapping("/getImages")
	public List<Product> getImage() throws IOException {

		List<Product> myProducts = new ArrayList<>();
		final List<Product> retrievedImage = productRepository.findAll();
		for(Product product : retrievedImage) {
			Product img = new Product(product.getId(),product.getName(),product.getDescription(),product.getFileName(),
											product.getFileType(),decompressBytes(product.getFileByte()));
			myProducts.add(img);
		
		}
		return myProducts;
	}
	
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}
}
