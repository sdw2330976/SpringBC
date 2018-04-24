package com.sdw.soft.repository;

import com.sdw.soft.meta.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by shangyd on 2018/4/24.
 */
public interface PersonRepository {

    Mono<Person> getPerson(int id);

    Flux<Person> allPerson();

    Mono<Person> savePerson(Mono<Person> person);
}
