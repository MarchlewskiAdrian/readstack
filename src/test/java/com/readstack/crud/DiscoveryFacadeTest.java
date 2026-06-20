//package com.readstack.crud;
//
//import com.readstack.crud.category.Category;
//import com.readstack.crud.category.CategoryFetcher;
//import com.readstack.crud.discovery.*;
//import com.readstack.dto.DiscoveryAddDto;
//import com.readstack.dto.DiscoveryGetDto;
//import com.readstack.validation.exception.DiscoveryExistsException;
//import com.readstack.validation.exception.DiscoveryNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.springframework.data.domain.Pageable;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//class DiscoveryFacadeTest {
//    private final InMemoryDiscoveryRepository inMemoryDiscoveryRepository = new InMemoryDiscoveryRepository();
//    private final InMemoryCategoryRepository inMemoryCategoryRepository = new InMemoryCategoryRepository();
//
//    private final DiscoveryFacade discoveryFacade = createDiscoveryFacade(
//            inMemoryDiscoveryRepository,
//            new CategoryFetcher(inMemoryCategoryRepository)
//    );
//
//    private DiscoveryFacade createDiscoveryFacade(
//            DiscoveryRepository discoveryRepository,
//            CategoryFetcher categoryFetcher
//    ) {
//        DiscoveryValidator discoveryValidator = new DiscoveryValidator(discoveryRepository);
//        DiscoveryFetcher discoveryFetcher = new DiscoveryFetcher(discoveryRepository);
//        DiscoveryAdder discoveryAdder = new DiscoveryAdder(discoveryRepository, categoryFetcher, discoveryValidator);
//        DiscoveryUpdater discoveryUpdater = new DiscoveryUpdater(discoveryFetcher, discoveryRepository, categoryFetcher, discoveryValidator);
//        DiscoveryDeleter discoveryDeleter = new DiscoveryDeleter(discoveryRepository);
//
//        return new DiscoveryFacade(
//                discoveryFetcher,
//                discoveryAdder,
//                discoveryUpdater,
//                discoveryDeleter
//        );
//    }
//
//    @Test
//    void should_return_all_discoveries_when_discoveries_exist() {
//        //given
//        addCategory();
//        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
//        DiscoveryAddDto discoveryAddDto2 = new DiscoveryAddDto("Odkrycie #2", "https://example.com/discovery-2", "Opis odkrycia numer 2.", 0L);
//        discoveryFacade.add(discoveryAddDto);
//        discoveryFacade.add(discoveryAddDto2);
//
//        //when
//        PageResponse<DiscoveryGetDto> all = discoveryFacade.getAll(null, Pageable.unpaged());
//
//        //then
//        assertThat(all.items()).hasSize(2);
//    }
//
//    @Test
//    void should_return_discoveries_by_category_id_when_category_exists() {
//        //given
//        addCategory();
//        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
//        DiscoveryAddDto discoveryAddDto2 = new DiscoveryAddDto("Odkrycie #2", "https://example.com/discovery-2", "Opis odkrycia numer 2.", 0L);
//        discoveryFacade.add(discoveryAddDto);
//        discoveryFacade.add(discoveryAddDto2);
//
//        //when
//        PageResponse<DiscoveryGetDto> all = discoveryFacade.getAllByCategoryId(0L, Pageable.unpaged());
//
//        //then
//        assertThat(all.items()).hasSize(2);
//    }
//
//    @Test
//    void should_return_discovery_by_id_when_discovery_exists() {
//        //given
//        addCategory();
//        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
//        DiscoveryGetDto addedDiscovery = discoveryFacade.add(discoveryAddDto);
//
//        //when
//        DiscoveryGetDto discovery = discoveryFacade.getById(addedDiscovery.id());
//
//        //then
//        assertThat(discovery).isEqualTo(addedDiscovery);
//    }
//
//    @Test
//    void should_update_discovery_by_id_when_discovery_exists() {
//        //given
//        addCategory();
//        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
//        DiscoveryGetDto addedDiscovery = discoveryFacade.add(discoveryAddDto);
//
//        //when
//        DiscoveryAddDto body = new DiscoveryAddDto("Odkrycie #2", "https://example.com/discovery-2", "Opis odkrycia numer 2.", 0L);
//        DiscoveryGetDto updatedDiscovery = discoveryFacade.updateById(addedDiscovery.id(), body);
//
//        //then
//        assertThat(updatedDiscovery)
//                .extracting(
//                        DiscoveryGetDto::id,
//                        DiscoveryGetDto::title,
//                        DiscoveryGetDto::url,
//                        DiscoveryGetDto::description,
//                        DiscoveryGetDto::category
//                ).containsExactly(
//                        addedDiscovery.id(),
//                        body.title(),
//                        body.url(),
//                        body.description(),
//                        addedDiscovery.category()
//                );
//
//    }
//
//    @Test
//    void should_delete_discovery_by_id() {
//        //given
//        addCategory();
//        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
//        DiscoveryGetDto addedDiscovery = discoveryFacade.add(discoveryAddDto);
//
//        //when
//        discoveryFacade.deleteById(addedDiscovery.id());
//
//        //then
//        PageResponse<DiscoveryGetDto> all = discoveryFacade.getAll(null, Pageable.unpaged());
//        assertThat(all.items()).isEmpty();
//
//    }
//
//    @Test
//    void should_add_discovery() {
//        //given
//        addCategory();
//        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
//
//        //when
//        DiscoveryGetDto addedDiscovery = discoveryFacade.add(discoveryAddDto);
//
//        //then
//        DiscoveryGetDto discovery = discoveryFacade.getById(addedDiscovery.id());
//        assertThat(discovery).isEqualTo(addedDiscovery);
//    }
//
//    @Test
//    void should_throw_exception_when_discovery_not_found_by_id() {
//        //given
//
//        //when & then
//        assertThatThrownBy(() -> discoveryFacade.getById(0L))
//                .isInstanceOf(DiscoveryNotFoundException.class);
//    }
//
//    @Test
//    void should_return_empty_list_when_category_does_not_exist() {
//        //given
//
//        //when & then
//        PageResponse<DiscoveryGetDto> all = discoveryFacade.getAllByCategoryId(0L, Pageable.unpaged());
//        assertThat(all.items()).isEmpty();
//    }
//
//    @Test
//    void should_throw_exception_when_discovery_already_exists() {
//        //given
//        addCategory();
//        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
//        discoveryFacade.add(discoveryAddDto);
//
//        //when & then
//        assertThatThrownBy(() -> discoveryFacade.add(discoveryAddDto))
//                .isInstanceOf(DiscoveryExistsException.class);
//    }
//
//    private void addCategory() {
//        Category category = new Category();
//        category.setId(0L);
//        category.setName("name1");
//        category.setDescription("description1");
//
//        inMemoryCategoryRepository.save(category);
//    }
//
//
//}
