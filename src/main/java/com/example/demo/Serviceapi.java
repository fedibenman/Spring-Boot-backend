package com.example.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.RequestBody;
;

@RestController
@CrossOrigin(origins = "*")
public class Serviceapi {
	
	private final AppartementRepo appartementReposotory ;
	private final imageRepo imageRepo ;

	Serviceapi(AppartementRepo appartementReposotory , imageRepo imageRepo){ 
		this.appartementReposotory = appartementReposotory ;
	this.imageRepo =imageRepo ;
	}
	
	// the post requests	

	@PostMapping(path="/AppartPost" ,consumes = {"*/*"}  )
	ResponseEntity<HttpStatus> addappartement( @RequestBody Appartement appartement)  {				
		appartementReposotory.save(appartement) ;
		return ResponseEntity.ok(HttpStatus.OK);
		}
	
	
	
	@PostMapping(path= "/imagePost"  ,consumes = {"*/*"} )
	ResponseEntity<HttpStatus> Image(@RequestParam("image") MultipartFile image) throws IOException {
		System.out.print(image) ;
		System.out.println("Original Image Byte Size - " + image.getBytes().length);
		image img = new image(image.getOriginalFilename(), image.getContentType(),image.getBytes());		
	imageRepo.save(img) ;
	return ResponseEntity.ok(HttpStatus.OK);
		}

	
	
	
	//the get requests 

	@GetMapping("/Allappartement")
	 List<Appartement> allappartement() {
	    return appartementReposotory.findAll();
	  }
	
	
	@GetMapping("/Allimage")
	 List<image> allimage() {
	    return imageRepo.findAll();
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
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);		return outputStream.toByteArray();
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

	public imageRepo getImageRepo() {
		return imageRepo;
	}

}
