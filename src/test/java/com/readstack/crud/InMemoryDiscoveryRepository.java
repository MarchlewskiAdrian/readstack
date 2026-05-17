package com.readstack.crud;

import com.readstack.validation.exception.DiscoveryNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

class InMemoryDiscoveryRepository implements DiscoveryRepository {
    private Map<Long, Discovery> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public <S extends Discovery> S save(S entity) {
        long id = index.getAndIncrement();
        if (entity.getId() == null) {
            entity.setId(id);
        }
        db.put(id, entity);
        return entity;
    }

    @Override
    public Page<Discovery> findAllWitOptionalTitleField(String title, Pageable pageable) {
        List<Discovery> all = db
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
    public Page<Discovery> findAllByCategory_Id(Long categoryId, Pageable pageable) {
        List<Discovery> all = db.values()
                .stream()
                .filter(d -> d
                        .getCategory()
                        .getId()
                        .equals(categoryId))
                .toList();
        return new PageImpl<>(
                all,
                pageable,
                all.size()
        );
    }

    @Override
    public boolean existsByTitleIgnoreCase(String title) {
        for (Discovery discovery : db.values()) {
            if (title.equalsIgnoreCase(discovery.getTitle())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByUrlIgnoreCase(String url) {
        for (Discovery discovery : db.values()) {
            if (url.equalsIgnoreCase(discovery.getUrl())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByTitleIgnoreCaseAndIdNot(String title, Long id) {
        return false;
    }

    @Override
    public boolean existsByCategory_Id(Long categoryId) {
        return false;
    }

    @Override
    public boolean existsByUrlIgnoreCaseAndIdNot(String url, Long id) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Discovery> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Discovery> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Discovery> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Discovery getOne(Long aLong) {
        return null;
    }

    @Override
    public Discovery getById(Long aLong) {
        return null;
    }

    @Override
    public Discovery getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Discovery> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Discovery> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Discovery> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Discovery> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Discovery> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Discovery> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Discovery, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Discovery> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Discovery> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        return db.containsKey(id);
    }

    @Override
    public List<Discovery> findAll() {
        return List.of();
    }

    @Override
    public List<Discovery> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long id) {
        if (!db.containsKey(id)) {
            throw new DiscoveryNotFoundException(id);
        }
        db.remove(id);
    }

    @Override
    public void delete(Discovery entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Discovery> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Discovery> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Discovery> findAll(Pageable pageable) {
        return null;
    }
}
