package com.readstack.crud.discovery;

import com.readstack.crud.PageResponse;
import com.readstack.crud.category.Category;
import com.readstack.crud.category.CategoryFacade;
import com.readstack.crud.category.InMemoryCategoryRepository;
import com.readstack.crud.discovery.search.DiscoveryFilter;
import com.readstack.crud.discovery.search.DiscoverySpecificationsBuilder;
import com.readstack.dto.DiscoveryAddDto;
import com.readstack.dto.DiscoveryGetDto;
import com.readstack.validation.exception.DiscoveryExistsException;
import com.readstack.validation.exception.DiscoveryNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DiscoveryFacadeTest {

    private final InMemoryCategoryRepository inMemoryCategoryRepository = new InMemoryCategoryRepository();
    private final InMemoryDiscoveryRepository inMemoryDiscoveryRepository = new InMemoryDiscoveryRepository();
    private final CategoryFacade categoryFacade = mock(CategoryFacade.class);
    private final DiscoverySpecificationsBuilder builder = new DiscoverySpecificationsBuilder();

    private final DiscoveryFacade discoveryFacade = createDiscoveryFacade(
            inMemoryDiscoveryRepository,
            categoryFacade
    );

    private DiscoveryFacade createDiscoveryFacade(
            DiscoveryRepository discoveryRepository,
            CategoryFacade categoryFacade
    ) {
        DiscoveryValidator discoveryValidator = new DiscoveryValidator(discoveryRepository);
        DiscoveryFetcher discoveryFetcher = new DiscoveryFetcher(discoveryRepository, builder);
        DiscoveryAdder discoveryAdder = new DiscoveryAdder(discoveryRepository, discoveryValidator, categoryFacade);
        DiscoveryUpdater discoveryUpdater = new DiscoveryUpdater(discoveryFetcher, discoveryRepository, discoveryValidator, categoryFacade);
        DiscoveryDeleter discoveryDeleter = new DiscoveryDeleter(discoveryRepository);

        return new DiscoveryFacade(
                discoveryFetcher,
                discoveryAdder,
                discoveryUpdater,
                discoveryDeleter
        );
    }

    @Test
    void should_return_all_discoveries_when_discoveries_exist() {
        //given
        Category category = addCategory();
        when(categoryFacade.getEntityById(category.getId())).thenReturn(category);
        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
        DiscoveryAddDto discoveryAddDto2 = new DiscoveryAddDto("Odkrycie #2", "https://example.com/discovery-2", "Opis odkrycia numer 2.", 0L);
        discoveryFacade.add(discoveryAddDto);
        discoveryFacade.add(discoveryAddDto2);
        DiscoveryFilter filter = new DiscoveryFilter(null, null, null, null, null, null);

        //when
        PageResponse<DiscoveryGetDto> all = discoveryFacade.search(filter, Pageable.unpaged());

        //then
        assertThat(all.items()).hasSize(2);
    }

    @Test
    void should_return_discoveries_by_category_id_when_category_exists() {
        //given
        Category category = addCategory();
        when(categoryFacade.getEntityById(category.getId())).thenReturn(category);

        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
        DiscoveryAddDto discoveryAddDto2 = new DiscoveryAddDto("Odkrycie #2", "https://example.com/discovery-2", "Opis odkrycia numer 2.", 0L);
        discoveryFacade.add(discoveryAddDto);
        discoveryFacade.add(discoveryAddDto2);

        //when
        PageResponse<DiscoveryGetDto> all = discoveryFacade.getAllByCategoryId(0L, Pageable.unpaged());

        //then
        assertThat(all.items()).hasSize(2);
    }

    @Test
    void should_return_discovery_by_id_when_discovery_exists() {
        //given
        Category category = addCategory();
        when(categoryFacade.getEntityById(category.getId())).thenReturn(category);

        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
        DiscoveryGetDto addedDiscovery = discoveryFacade.add(discoveryAddDto);

        //when
        DiscoveryGetDto discovery = discoveryFacade.getById(addedDiscovery.id());

        //then
        assertThat(discovery).isEqualTo(addedDiscovery);
    }

    @Test
    void should_update_discovery_by_id_when_discovery_exists() {
        //given
        Category category = addCategory();
        when(categoryFacade.getEntityById(category.getId())).thenReturn(category);

        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
        DiscoveryGetDto addedDiscovery = discoveryFacade.add(discoveryAddDto);

        //when
        DiscoveryAddDto body = new DiscoveryAddDto("Odkrycie #2", "https://example.com/discovery-2", "Opis odkrycia numer 2.", 0L);
        DiscoveryGetDto updatedDiscovery = discoveryFacade.updateById(addedDiscovery.id(), body);

        //then
        assertThat(updatedDiscovery)
                .extracting(
                        DiscoveryGetDto::id,
                        DiscoveryGetDto::title,
                        DiscoveryGetDto::url,
                        DiscoveryGetDto::description,
                        DiscoveryGetDto::category
                ).containsExactly(
                        addedDiscovery.id(),
                        body.title(),
                        body.url(),
                        body.description(),
                        addedDiscovery.category()
                );

    }

    @Test
    void should_delete_discovery_by_id() {
        //given
        Category category = addCategory();
        when(categoryFacade.getEntityById(category.getId())).thenReturn(category);

        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);
        DiscoveryGetDto addedDiscovery = discoveryFacade.add(discoveryAddDto);

        //when
        discoveryFacade.deleteById(addedDiscovery.id());
        DiscoveryFilter filter = new DiscoveryFilter(null, null, null, null, null, null);


        //then
        PageResponse<DiscoveryGetDto> all = discoveryFacade.search(filter, Pageable.unpaged());
        assertThat(all.items()).isEmpty();

    }

    @Test
    void should_add_discovery() {
        //given
        Category category = addCategory();
        when(categoryFacade.getEntityById(category.getId())).thenReturn(category);

        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto("Odkrycie #1", "https://example.com/discovery-1", "Opis odkrycia numer 1.", 0L);

        //when
        DiscoveryGetDto addedDiscovery = discoveryFacade.add(discoveryAddDto);

        //then
        DiscoveryGetDto discovery = discoveryFacade.getById(addedDiscovery.id());
        assertThat(discovery).isEqualTo(addedDiscovery);
    }

    @Test
    void should_throw_exception_when_discovery_not_found_by_id() {
        //given

        //when & then
        assertThatThrownBy(() -> discoveryFacade.getById(0L))
                .isInstanceOf(DiscoveryNotFoundException.class);
    }

    @Test
    void should_return_empty_list_when_category_does_not_exist() {
        //given

        //when & then
        PageResponse<DiscoveryGetDto> all = discoveryFacade.getAllByCategoryId(0L, Pageable.unpaged());
        assertThat(all.items()).isEmpty();
    }

    @Test
    void should_throw_exception_when_discovery_already_exists() {
        //given
        Category category = addCategory();
        when(categoryFacade.getEntityById(category.getId())).thenReturn(category);

        DiscoveryAddDto discoveryAddDto = new DiscoveryAddDto(
                "Odkrycie #1",
                "https://example.com/discovery-1",
                "Opis odkrycia numer 1.",
                category.getId());

        discoveryFacade.add(discoveryAddDto);

        //when & then
        assertThatThrownBy(() -> discoveryFacade.add(discoveryAddDto))
                .isInstanceOf(DiscoveryExistsException.class);
    }

    private Category addCategory() {
        Category category = new Category();
        category.setId(0L);
        category.setName("name1");
        category.setDescription("description1");

       return inMemoryCategoryRepository.save(category);
    }


}
