package com.github.fernaspiazu.reactiveweb.repository;

import com.github.fernaspiazu.reactiveweb.domain.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface QuoteMongoReactiveRepository extends ReactiveSortingRepository<Quote, String>,
        ReactiveCrudRepository<Quote, String> {

    Flux<Quote> findAllByIdNotNullOrderByIdAsc(final Pageable page);
}
