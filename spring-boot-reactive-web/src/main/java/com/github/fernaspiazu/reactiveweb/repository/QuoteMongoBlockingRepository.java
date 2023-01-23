package com.github.fernaspiazu.reactiveweb.repository;

import com.github.fernaspiazu.reactiveweb.domain.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QuoteMongoBlockingRepository extends PagingAndSortingRepository<Quote, String>,
        CrudRepository<Quote, String> {

    List<Quote> findAllByIdNotNullOrderByIdAsc(final Pageable page);
}
