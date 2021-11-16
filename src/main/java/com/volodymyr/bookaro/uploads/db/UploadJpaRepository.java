package com.volodymyr.bookaro.uploads.db;

import com.volodymyr.bookaro.uploads.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadJpaRepository extends JpaRepository<Upload, Long> {
}
