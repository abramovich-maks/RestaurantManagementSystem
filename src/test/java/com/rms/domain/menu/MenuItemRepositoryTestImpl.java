package com.rms.domain.menu;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

class MenuItemRepositoryTestImpl implements MenuItemRepository {

    Map<Long, MenuItemEntity> database = new ConcurrentHashMap<>();
    AtomicInteger index = new AtomicInteger(1);

    @Override
    public boolean existsByName(final String name) {
        return database.values().stream()
                .anyMatch(item -> item.getName().equals(name));
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends MenuItemEntity> S saveAndFlush(final S entity) {
        return null;
    }

    @Override
    public <S extends MenuItemEntity> List<S> saveAllAndFlush(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(final Iterable<MenuItemEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(final Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MenuItemEntity getOne(final Long aLong) {
        return null;
    }

    @Override
    public MenuItemEntity getById(final Long aLong) {
        return null;
    }

    @Override
    public MenuItemEntity getReferenceById(final Long aLong) {
        return null;
    }

    @Override
    public <S extends MenuItemEntity> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends MenuItemEntity> List<S> findAll(final Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends MenuItemEntity> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }

    @Override
    public <S extends MenuItemEntity> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }

    @Override
    public <S extends MenuItemEntity> long count(final Example<S> example) {
        return 0;
    }

    @Override
    public <S extends MenuItemEntity> boolean exists(final Example<S> example) {
        return false;
    }

    @Override
    public <S extends MenuItemEntity, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends MenuItemEntity> S save(final S entity) {
        if (entity.getId() == null) {
            long id = index.getAndIncrement();
            entity.setId(id);
            database.put(id, entity);
        } else {
            database.put(entity.getId(), entity);
        }
        return entity;
    }


    @Override
    public <S extends MenuItemEntity> List<S> saveAll(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<MenuItemEntity> findById(final Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public boolean existsById(final Long aLong) {
        return database.containsKey(aLong);
    }

    @Override
    public List<MenuItemEntity> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public List<MenuItemEntity> findAllById(final Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(final Long aLong) {
        database.remove(database.remove(aLong));
    }

    @Override
    public void delete(final MenuItemEntity entity) {
    }

    @Override
    public void deleteAllById(final Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(final Iterable<? extends MenuItemEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<MenuItemEntity> findAll(final Sort sort) {
        return List.of();
    }

    @Override
    public Page<MenuItemEntity> findAll(final Pageable pageable) {
        return null;
    }
}
