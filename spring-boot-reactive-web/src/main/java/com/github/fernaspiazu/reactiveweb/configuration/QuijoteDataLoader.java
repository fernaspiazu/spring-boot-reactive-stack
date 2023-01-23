package com.github.fernaspiazu.reactiveweb.configuration;

import com.github.fernaspiazu.reactiveweb.domain.Quote;
import com.github.fernaspiazu.reactiveweb.repository.QuoteMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Component
public class QuijoteDataLoader implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(QuijoteDataLoader.class);

    private final QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    public QuijoteDataLoader(final QuoteMongoReactiveRepository quoteMongoReactiveRepository) {
        this.quoteMongoReactiveRepository = quoteMongoReactiveRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (quoteMongoReactiveRepository.count().block() == 0L) {
            Supplier<String> idSupplier = getIdSequenceSupplier();
            BufferedReader bufferedReader = new BufferedReader(readDataFromFile());
            Stream<Mono<Quote>> monoStream = bufferedReader.lines()
                .filter(line -> !line.trim().isEmpty())
                .map(content -> {
                    Quote newQuote = new Quote(idSupplier.get(), "El Quijote", content);
                    return quoteMongoReactiveRepository.save(newQuote);
                });
            Flux.fromStream(monoStream).subscribe(m -> log.info("New Quote loaded: {}", m.block()));
            log.info("Repository contains now {} entries", quoteMongoReactiveRepository.count().block());
        }
    }

    private Supplier<String> getIdSequenceSupplier() {
        return new Supplier<>() {
            long l = 0L;

            @Override
            public String get() {
                // adds padding zeroes
                return String.format("%05d", l++);
            }
        };
    }

    private Reader readDataFromFile() {
        return new InputStreamReader(Objects.requireNonNull(
                getClass()
                        .getClassLoader()
                        .getResourceAsStream("pg2000.txt"))
        );
    }
}
