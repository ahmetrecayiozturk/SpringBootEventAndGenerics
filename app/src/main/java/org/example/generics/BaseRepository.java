package org.example.generics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

// Burada biz temel birtakım şeyleri sürekli sürekli yapmak yerine baserepository'den direkt alıp kullanacağız.
@NoRepositoryBean
public interface BaseRepository<T,ID> extends JpaRepository<T,ID> { }
