package com.readstack.crud;

import com.readstack.validation.exception.CategoryNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

class InMemoryCategoryRepository implements CategoryRepository {
    Map<Long, Category> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        for (Category category : db.values()) {
            if (name.equalsIgnoreCase(category.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <S extends Category> S save(S entity) {
        long id = index.getAndIncrement();
        if (entity.getId() == null) {
            entity.setId(id);
        }
        db.put(id, entity);
        return entity;
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        List<Category> all = db
                .values()
                .stream()
                .toList();

        return new PageImpl<>(
                all,
                pageable,
                all.size()
        );
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public void deleteById(Long id) {
        if (!db.containsKey(id)){
            throw new CategoryNotFoundException(id);
        }
        db.remove(id);

    }

    @Override
    public boolean existsById(Long id) {
        return db.containsKey(id);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Category> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Category> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Category> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Category getOne(Long aLong) {
        return null;
    }

    @Override
    public Category getById(Long aLong) {
        return null;
    }

    @Override
    public Category getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Category> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Category> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Category> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Category> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Category> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Category> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Category, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Category> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public List<Category> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Category entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Category> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Category> findAll(Sort sort) {
        return List.of();
    }
}
