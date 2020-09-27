package com.sample.masking.repository;

import com.sample.masking.entity.PersonContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository
    extends JpaRepository<PersonContact, Long> {}