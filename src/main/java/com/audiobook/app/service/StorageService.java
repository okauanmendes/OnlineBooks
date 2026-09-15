package com.audiobook.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.time.Duration;

@Service
public class StorageService {

	private final S3Client s3Client;
	private final S3Presigner s3Presigner;
	private final String bucket;

	public StorageService(S3Client s3Client,
						  S3Presigner s3Presigner,
						  @Value("${aws.s3.bucket}") String bucket) {
		this.s3Client = s3Client;
		this.s3Presigner = s3Presigner;
		this.bucket = bucket;
	}

	public void upload(String key, MultipartFile file) throws IOException {
		PutObjectRequest request = PutObjectRequest.builder()
				.bucket(bucket)
				.key(key)
				.contentType(file.getContentType())
				.build();

		s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
	}

	public String generateDownloadUrl(String key, Duration duration) {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder()
				.bucket(bucket)
				.key(key)
				.build();

		GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
				.signatureDuration(duration)
				.getObjectRequest(getObjectRequest)
				.build();

		return s3Presigner.presignGetObject(presignRequest).url().toString();
	}

	public void delete(String key) {
		s3Client.deleteObject(DeleteObjectRequest.builder()
				.bucket(bucket)
				.key(key)
				.build());
	}
}
